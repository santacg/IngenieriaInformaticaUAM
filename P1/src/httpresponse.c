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
  NOT_FOUND = 404,
  SVR_ERROR = 500,
  METHOD_NOT_FOUND = 501,
} Http_status;

const char *get_status_message(Http_status status);

const char *get_mime_type(char *path);

char *get_pathension(char *path);

void methodGet(int connfd, char *path, int minor_version, int response_code);

void methodPost(int connfd, char *request, char *path, int minor_version,
                int response_code);

void methodOptions(int connfd, int minor_version, int response_code);

void process_http_request(int connfd) {
  int pret, minor_version, response_code = OK;
  char buffer[BUFFER_SIZE];
  char *method, *path, *request_body;
  size_t buff_len = 0, prev_buff_len = 0, num_headers, method_len, path_len;
  ssize_t nread;
  struct phr_header headers[100];

  while (1) {
    nread = read(connfd, buffer + buff_len, BUFFER_SIZE - buff_len);
    if (nread <= 0) {
      response_code = SVR_ERROR;
      break;
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
      response_code = SVR_ERROR;
      break;
    }

    assert(pret == -2);
    if (buff_len == sizeof(buffer)) {
      fprintf(stderr, "HTTP request too long");
      response_code = SVR_ERROR;
      break;
    }
  }

  if (response_code == OK) {
    request_body = strstr(buffer, "\r\n\r\n");
    method[method_len] = '\0';
    path[path_len] = '\0';
  }

  if (strcmp(method, "GET") == 0) {
    methodGet(connfd, path, minor_version, response_code);
  } else if (strcmp(method, "POST") == 0) {
    methodPost(connfd, request_body + 4, path, minor_version, response_code);
  } else if (strcmp(method, "OPTIONS") == 0) {
    methodOptions(connfd, minor_version, response_code);
  } else {
    methodOptions(connfd, minor_version, METHOD_NOT_FOUND);
  }

  return;
}

void methodGet(int connfd, char *path, int minor_version, int response_code) {
  int resource_fd;
  char response[BUFFER_SIZE];
  time_t tm = time(0);
  struct stat file_stats;

  resource_fd = open(path + 1, O_RDONLY);
  if (resource_fd == ERR) {
    response_code = NOT_FOUND;
  }

  if (response_code != NOT_FOUND) {
    if (fstat(resource_fd, &file_stats) == ERR) {
      response_code = SVR_ERROR;
    }
  }

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "Last-Modified: %s\r\n"
          "Content-Length: %ld\r\n"
          "Content-Type: %s\r\n"
          "\r\n",
          minor_version, response_code, get_status_message(response_code),
          ctime(&tm), ctime(&file_stats.st_mtim.tv_sec), file_stats.st_size,
          get_mime_type(strchr(path, '.')));

  send(connfd, response, strlen(response), 0);

  bzero(response, BUFFER_SIZE);
  while (read(resource_fd, response, BUFFER_SIZE) != 0) {
    send(connfd, response, strlen(response), 0);
  }

  return;
}

void methodPost(int connfd, char *request_body, char *path, int minor_version, int response_code) {
  char response[BUFFER_SIZE];
  time_t tm = time(0);

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "\r\n",
          minor_version, response_code, get_status_message(response_code),
          ctime(&tm));

  send(connfd, response, strlen(response), 0);
}

void methodOptions(int connfd, int minor_version, int response_code) {
  char response[BUFFER_SIZE];
  time_t tm = time(0);

  sprintf(response,
          "HTTP/1.%d %d %s\r\n"
          "Date: %s\r\n"
          "Server: Carlos server\r\n"
          "Allow: GET, POST, OPTIONS\r\n"
          "\r\n",
          minor_version, response_code, get_status_message(response_code),
          ctime(&tm));

  send(connfd, response, strlen(response), 0);
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

void process_bad_request(int connfd) { return; }

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
  case NOT_FOUND:
    return "Not found";
  case SVR_ERROR:
    return "Server error";
  default:
    return "Unknown status";
  }
}
