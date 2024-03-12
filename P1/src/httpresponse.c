/**
 * @file httpresponse.c
 * @brief Implementation of functions to handle HTTP responses for a web server.
 *        This includes generating responses for various HTTP methods such as
 * GET, POST, and OPTIONS, executing server-side scripts, decoding URL-encoded
 * strings, and determining MIME types based on file extensions.
 * @author Carlos García Santa Eduardo Junoy Ortega
 * @date 12/03/2024
 */

#include "../includes/httpresponse.h"
#include "../includes/picohttpparser.h"
#include <assert.h>
#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>

#define ERR -1
#define BUFFER_SIZE 16384
#define LINE 64

typedef enum {
  OK = 200,
  BAD_REQUEST = 400,
  NOT_FOUND = 404,
  SVR_ERROR = 500,
  METHOD_NOT_FOUND = 501
} Http_status;

/**
 * @brief Retrieves the message string associated with an HTTP status code.
 * @param status The HTTP status code.
 * @return The message string associated with the status code.
 */
const char *get_status_message(Http_status status);

/**
 * @brief Determines the MIME type based on the file extension.
 * @param path The path to the file.
 * @return The MIME type string if recognized, NULL otherwise.
 */
const char *get_mime_type(char *path);

/**
 * @brief Generates and sends an HTTP response indicating an error.
 * @param connfd The file descriptor for the connection.
 * @param response_code The HTTP status code to be sent.
 */
void http_response_error(int connfd, int response_code);

/**
 * @brief Handles the GET method request.
 * @param connfd The file descriptor for the connection.
 * @param path The path requested by the GET method.
 * @param minor_version The HTTP minor version.
 */
void methodGet(int connfd, char *path, int minor_version);

/**
 * @brief Handles the POST method request.
 * @param connfd The file descriptor for the connection.
 * @param request The body of the POST request.
 * @param path The path requested by the POST method.
 * @param minor_version The HTTP minor version.
 */
void methodPost(int connfd, char *request, char *path, int minor_version);

/**
 * @brief Handles the OPTIONS method request.
 * @param connfd The file descriptor for the connection.
 * @param minor_version The HTTP minor version.
 * @param response_code The HTTP status code for the OPTIONS response.
 */
void methodOptions(int connfd, int minor_version, int response_code);

/**
 * @brief Executes a script based on the request path.
 * @param connfd The file descriptor for the connection.
 * @param path The script path to be executed.
 * @param params The parameters to pass to the script.
 */
void exe_script(int connfd, char *path, char *params);

/**
 * @brief Decodes a URL-encoded string.
 * @param url The URL-encoded string.
 * @return The decoded string.
 */
char *decode_url(const char *url);

/**
 * @brief Processes an HTTP request.
 * @param connfd The file descriptor for the connection.
 */
void process_http_request(int connfd);

/**
 * @brief Processes an HTTP request.
 * @param connfd The file descriptor for the connection.
 * @note This function reads the HTTP request from the connection, parses it to
 * determine the request method, path, and HTTP version, and then dispatches it
 * to the appropriate handler function based on the method. It handles parsing
 * errors and unsupported methods by sending suitable HTTP error responses. This
 * is the entry point for all incoming HTTP requests.
 */
void process_http_request(int connfd) {
  int pret, minor_version; 
  char buffer[BUFFER_SIZE];
  char *method, *path, *request_body;
  size_t buff_len = 0, prev_buff_len = 0, num_headers, method_len, path_len;
  ssize_t nread = 0;
  struct phr_header headers[100];

  while (1) {
    nread = read(connfd, buffer + buff_len, BUFFER_SIZE - buff_len);
    if (nread <= 0) {
      perror("Error reading from scoket");
      http_response_error(connfd, SVR_ERROR);
      return;
    }
    prev_buff_len = buff_len;
    buff_len += nread;
    num_headers = sizeof(headers) / sizeof(headers[0]);
    pret = phr_parse_request(buffer, buff_len, &method, &method_len, &path,
                             &path_len, &minor_version, headers, &num_headers,
                             prev_buff_len);

    if (pret > 0)
      break;
    else if (pret == -1) {
      fprintf(stderr, "Error parsing http request");
      http_response_error(connfd, BAD_REQUEST);
      return;
    }

    assert(pret == -2);
    if (buff_len == sizeof(buffer)) {
      fprintf(stderr, "HTTP request too long");
      http_response_error(connfd, BAD_REQUEST);
      return;
    }
  }

  request_body = strstr(buffer, "\r\n\r\n");
  method[method_len] = '\0';
  path[path_len] = '\0';

  if (strcmp(method, "GET") == 0) {
    methodGet(connfd, path, minor_version);
  } else if (strcmp(method, "POST") == 0) {
    methodPost(connfd, request_body + 4, path, minor_version);
  } else if (strcmp(method, "OPTIONS") == 0) {
    methodOptions(connfd, minor_version, OK);
  } else {
    methodOptions(connfd, minor_version, METHOD_NOT_FOUND);
  }

  return;
}

/**
 * @brief Handles the GET method request.
 * @param connfd The file descriptor for the connection.
 * @param path The path requested by the GET method.
 * @param minor_version The HTTP minor version.
 * @note This function processes GET requests by determining the resource
 * requested, checking its availability, and sending it back to the client with
 * appropriate HTTP headers. If the resource is not found, it sends a 404 Not
 * Found response.
 */
void methodGet(int connfd, char *path, int minor_version) {
  int resource_fd, bytes_read = 0, params_len = 0;
  char response[BUFFER_SIZE], last_modified[LINE], date[LINE];
  char *params = NULL;
  time_t t = time(NULL);
  struct tm tm;
  struct tm lm;
  struct stat file_stats;

  /* Check if it is a script, if correct execute script function */
  if (strchr(path, '?') != NULL) {
    params_len = strchr(path, '?') - path;
    params = (char *)malloc(sizeof(char) * params_len + 1);
    if (!params) {
      http_response_error(connfd, SVR_ERROR);
      return;
    }
    params = strchr(path, '?');
    if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
        strncmp(strchr(path, '.'), ".py", 3) == 0) {
      exe_script(connfd, path + 1, params + 1);
      return;
    }
  }

  resource_fd = open(path + 1, O_RDONLY);
  if (resource_fd == ERR) {
    http_response_error(connfd, NOT_FOUND);
    return;
  }

  if (fstat(resource_fd, &file_stats) == ERR) {
    http_response_error(connfd, SVR_ERROR);
    return;
  }

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  gmtime_r(&file_stats.st_mtim.tv_sec, &lm);
  strftime(last_modified, sizeof(last_modified), "%a, %d %b %Y %H:%M:%S GMT",
           &lm);

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "Last-Modified: %s\r\n"
          "Content-Length: %ld\r\n"
          "Content-Type: %s\r\n"
          "\r\n",
          minor_version, OK, get_status_message(OK), date, last_modified,
          file_stats.st_size, get_mime_type(strchr(path, '.')));

  send(connfd, response, strlen(response), 0);
  bzero(response, BUFFER_SIZE);
  while ((bytes_read = read(resource_fd, response, BUFFER_SIZE)) > 0) {
    send(connfd, response, bytes_read, 0);
  }

  return;
}

/**
 * @brief Handles the POST method request.
 * @param connfd The file descriptor for the connection.
 * @param request The body of the POST request.
 * @param path The path requested by the POST method.
 * @param minor_version The HTTP minor version.
 * @note This function processes POST requests by extracting data from the
 * request body and handling it according to the application's logic. It can
 * respond with various HTTP status codes based on the outcome of processing the
 * request.
 */
void methodPost(int connfd, char *request_body, char *path, int minor_version) {
  char response[BUFFER_SIZE], date[LINE];
  time_t t = time(NULL);
  struct tm tm;

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
      strncmp(strchr(path, '.'), ".py", 3) == 0) {
    exe_script(connfd, path + 1, request_body);
    return;
  }

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "\r\n",
          minor_version, OK, get_status_message(OK), date);

  send(connfd, response, strlen(response), 0);
  return;
}

/**
 * @brief Handles the OPTIONS method request.
 * @param connfd The file descriptor for the connection.
 * @param minor_version The HTTP minor version.
 * @param response_code The HTTP status code for the OPTIONS response.
 * @note This function is used to communicate to the client which HTTP methods
 * are supported by the server for a given URL resource. It's useful for CORS
 *       preflight requests in web applications.
 */
void methodOptions(int connfd, int minor_version, int response_code) {
  char response[BUFFER_SIZE], date[LINE];
  time_t t = time(0);
  struct tm tm;

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "Allow: GET, POST, OPTIONS\r\n"
          "\r\n",
          minor_version, response_code, get_status_message(response_code),
          date);

  send(connfd, response, strlen(response), 0);
  return;
}

void http_response_error(int connfd, int response_code) {
  char response[BUFFER_SIZE], date[LINE];
  time_t t = time(0);
  struct tm tm;

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  sprintf(response,
          "HTTP/1.1 %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "\r\n",
          response_code, get_status_message(response_code), date);

  send(connfd, response, strlen(response), 0);
  return;
}

/**
 * @brief Executes a script based on the request path.
 * @param connfd The file descriptor for the connection.
 * @param path The script path to be executed.
 * @param params The parameters to pass to the script.
 * @note This function allows the server to execute server-side scripts and
 * return their output as a response to the client. It supports dynamic content
 * generation.
 */
void exe_script(int connfd, char *path, char *params) {
  int response_code = OK, resource_fd;
  FILE *fd = NULL;
  char buffer[BUFFER_SIZE], response[BUFFER_SIZE], command[LINE], date[LINE],
      last_modified[LINE];
  char *tok = NULL, *save, *decoded;
  time_t t = time(0);
  struct tm tm;
  struct stat file_stats;

  if (strncmp(strchr(path, '.'), ".php", 4) == 0) {
    strcpy(command, "php ");
  } else if (strncmp(strchr(path, '.'), ".py", 3) == 0) {
    strcpy(command, "python3 ");
  } else {
    http_response_error(connfd, BAD_REQUEST);
    return;
  }

  path = strtok(path, "?");
  strcat(command, path);
  tok = strtok_r(params, "=", &save);
  strcat(command, " ");
  strcat(command, tok);
  while (tok) {
    tok = strtok_r(NULL, "&", &save);
    if (!tok)
      break;
    strcat(command, " ");
    strcat(command, tok);
  }

  strcat(command, " < /dev/null");
  decoded = decode_url(command);
  if (!decoded) {
    http_response_error(connfd, BAD_REQUEST);
    return;
  }

  fd = popen(decoded, "r");
  if (!fd) {
    http_response_error(connfd, SVR_ERROR);
    return;
  }

  resource_fd = fileno(fd);
  while ((read(resource_fd, buffer, sizeof(buffer))) > 0) {
    strcat(buffer, "\r\n");
  }

  if (fstat(resource_fd, &file_stats) == ERR) {
    http_response_error(connfd, SVR_ERROR);
    return;
  }

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  sprintf(response,
          "HTTP/1.0 %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "Last-Modified: %s\r\n"
          "Content-Length: %ld\r\n"
          "Content-Type: text/html\r\n"
          "\r\n",
          response_code, get_status_message(response_code), date, last_modified,
          strlen(buffer));

  send(connfd, response, strlen(response), 0);
  send(connfd, buffer, strlen(buffer), 0);
  pclose(fd);
  free(decoded);
  return;
}

/**
 * @brief Determines the MIME type based on the file extension.
 * @param path The path to the file.
 * @return The MIME type string if recognized, NULL otherwise.
 * @note This function uses file extension to determine the MIME type for
 *       common file types (e.g., .html, .css, .js).
 */
const char *get_mime_type(char *path) {
  if (strcasecmp(path, ".html") == 0) {
    return "text/html";
  } else if (strcasecmp(path, ".txt") == 0) {
    return "text/plain";
  } else if (strcasecmp(path, ".jpg") == 0 || strcasecmp(path, ".jpeg") == 0) {
    return "image/jpeg";
  } else if (strcasecmp(path, ".gif") == 0) {
    return "image/gif";
  } else if (strcasecmp(path, ".mpeg") == 0 || strcasecmp(path, ".mpg") == 0) {
    return "video/mpeg";
  } else if (strcasecmp(path, ".doc") == 0 || strcasecmp(path, ".docx") == 0) {
    return "application/msword";
  } else if (strcasecmp(path, ".pdf") == 0) {
    return "application/pdf";
  } else {
    return NULL;
  }
}

/**
 * @brief Retrieves the message string associated with an HTTP status code.
 * @param status The HTTP status code.
 * @return The message string associated with the status code.
 * @note This function maps the enum Http_status values to their corresponding
 *       message strings.
 */
const char *get_status_message(Http_status status) {
  switch (status) {
  case OK:
    return "OK";
  case BAD_REQUEST:
    return "Bad request";
  case NOT_FOUND:
    return "Not found";
  case SVR_ERROR:
    return "Server error";
  case METHOD_NOT_FOUND:
    return "Method not found";
  default:
    return "Unknown status";
  }
}

/**
 * @brief Decodes a URL-encoded string.
 * @param url The URL-encoded string.
 * @return The decoded string.
 * @note This function decodes percent-encoded characters in the URL, converting
 *       them back to their original characters. It's essential for processing
 *       URLs containing special characters.
 */
char *decode_url(const char *url) {
  char *ret = malloc(strlen(url) + 2);

  int len = 0;
  for (; *url; len++) {
    if (*url == '%' && url[1] && url[2] && isxdigit(url[1]) &&
        isxdigit(url[2])) {
      char url1 = url[1];
      char url2 = url[2];
      url1 -= url1 <= '9' ? '0' : (url1 <= 'F' ? 'A' : 'a') - 10;
      url2 -= url2 <= '9' ? '0' : (url2 <= 'F' ? 'A' : 'a') - 10;
      ret[len] = 16 * url1 + url2;
      url += 3;
      continue;
    } else if (*url == '+') {
      url += 1;
      ret[len] = ' ';
    }
    ret[len] = *url++;
  }
  ret[len] = '\0';

  return ret;
}
