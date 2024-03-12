#include "httpresponse.h"
#include "picohttpparser.h"
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
  METHOD_NOT_FOUND = 501,
} Http_status;

const char *get_status_message(Http_status status);

const char *get_mime_type(char *path);

char *get_pathension(char *path);

void http_response_error(int connfd, int response_code);

void methodGet(int connfd, char *path, int minor_version);

void methodPost(int connfd, char *request, char *path, int minor_version);

void methodOptions(int connfd, int minor_version, int response_code);

void exe_script(int connfd, char *path, char *exe_path);

char *decode_url(const char *url);

void process_http_request(int connfd) {
  int pret, minor_version, response_code = OK;
  char buffer[BUFFER_SIZE];
  char *method, *path, *request_body;
  size_t buff_len = 0, prev_buff_len = 0, num_headers, method_len, path_len;
  ssize_t nread = 0;
  struct phr_header headers[100];

  while (1) {
    nread = read(connfd, buffer + buff_len, BUFFER_SIZE - buff_len);
    if (nread <= 0) {
      perror("Error reading from scoket");
      response_code = SVR_ERROR;
      http_response_error(connfd, response_code);
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
      response_code = BAD_REQUEST;
      http_response_error(connfd, response_code);
      return;
    }

    assert(pret == -2);
    if (buff_len == sizeof(buffer)) {
      fprintf(stderr, "HTTP request too long");
      response_code = BAD_REQUEST;
      http_response_error(connfd, response_code);
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

void methodGet(int connfd, char *path, int minor_version) {
  int resource_fd, bytes_read = 0, exe_path_len = 0, response_code = OK;
  char response[BUFFER_SIZE], last_modified[LINE], date[LINE];
  char *exe_path = NULL;
  time_t t = time(NULL);
  struct tm tm;
  struct tm lm;
  struct stat file_stats;

  if (strchr(path, '?') != NULL) {
    exe_path_len = strchr(path, '?') - path;
    exe_path = (char *)malloc(sizeof(char) * exe_path_len);
    if (!exe_path) {
      response_code = SVR_ERROR;
      http_response_error(connfd, response_code);
      return;
    }

    strncpy(exe_path, path, exe_path_len);
    resource_fd = open(exe_path + 1, O_RDONLY);
    if (resource_fd == ERR) {
      response_code = NOT_FOUND;
      http_response_error(connfd, response_code);
      return;
    }
  } else {
    resource_fd = open(path + 1, O_RDONLY);
    if (resource_fd == ERR) {
      response_code = NOT_FOUND;
      http_response_error(connfd, response_code);
      return;
    }
  }

  if (fstat(resource_fd, &file_stats) == ERR) {
    response_code = SVR_ERROR;
    http_response_error(connfd, response_code);
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
          minor_version, response_code, get_status_message(response_code), date,
          last_modified, file_stats.st_size, get_mime_type(strchr(path, '.')));

  send(connfd, response, strlen(response), 0);

  if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
      strncmp(strchr(path, '.'), ".py", 3) == 0) {
    exe_script(connfd, path + 1, exe_path + 1);
    free(exe_path);
    return;
  }

  bzero(response, BUFFER_SIZE);
  while ((bytes_read = read(resource_fd, response, BUFFER_SIZE)) > 0) {
    send(connfd, response, bytes_read, 0);
  }

  free(exe_path);
  return;
}

void methodPost(int connfd, char *request_body, char *path, int minor_version) {
  int response_code = OK, exe_path_len = 0, resource_fd;
  char response[BUFFER_SIZE], date[LINE];
  char *exe_path;
  time_t t = time(NULL);
  struct tm tm;

  gmtime_r(&t, &tm);
  strftime(date, sizeof(date), "%a, %d %b %Y %H:%M:%S GMT", &tm);

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "\r\n",
          minor_version, response_code, get_status_message(response_code),
          date);

  if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
      strncmp(strchr(path, '.'), ".py", 3) == 0) {
    exe_script(connfd, request_body, path + 1);
    free(exe_path);
    return;
  }

  send(connfd, response, strlen(response), 0);
  return;
}

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
void exe_script(int connfd, char *path, char *exe_path) {
  FILE *fd = NULL;
  char buffer[BUFFER_SIZE] = "\0", command[LINE];
  char *tok = NULL, *save, *decoded;

  if (strncmp(strchr(path, '.'), ".php", 4) == 0) {
    strcpy(command, "php ");
  } else if (strncmp(strchr(path, '.'), ".py", 3) == 0) {
    strcpy(command, "python3 ");
  } else {
    return;
  }

  strcat(command, exe_path);
  path = strchr(path, '?');
  tok = strtok_r(path + 1, "=", &save);
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
  if (!decoded)
    return;

  fd = popen(decoded, "r");
  if (!fd)
    return;

  while (fgets(buffer, BUFFER_SIZE, fd) != NULL) {
    if (strcmp(buffer, "\r\n") == 0) {
      break;
    }
    send(connfd, buffer, strlen(buffer), 0);
    memset(buffer, 0, BUFFER_SIZE);
  }

  pclose(fd);
  free(decoded);
  return;
}

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
