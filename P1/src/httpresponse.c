#include "httpresponse.h"
#include <assert.h>
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

const char *get_mime_type(char *path);

char *get_pathension(char *path);

void methodGet(int connfd, char *path, int minor_version);

void process_bad_request(int connfd);

void process_http_request(int connfd) {
  int pret, minor_version;
  char buffer[BUFFER_SIZE];
  char *method, *path;
  size_t buff_len = 0, prev_buff_len = 0, num_headers, method_len, path_len;
  ssize_t nread;
  struct phr_header headers[100];

  while (1) {
    nread = read(connfd, buffer + buff_len, BUFFER_SIZE - buff_len);
    if (nread <= 0)
      return;

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
      return;
    }

    assert(pret == -2);
    if (buff_len == sizeof(buffer)) {
      fprintf(stderr, "HTTP request too long");
      return;
    }
  }

  method[method_len] = '\0';
  path[path_len] = '\0';

  if (strcmp(method, "GET") == 0) {
    methodGet(connfd, path, minor_version);
  } else if (strcmp(method, "POST") == 0) {
  } else if (strcmp(method, "OPTIONS") == 0) {
  } else {
    return;
  }
}

void methodGet(int connfd, char *path, int minor_version) {
  int resource_fd;
  char response[BUFFER_SIZE];
  time_t tm = time(0);
  struct stat file_stats;

  resource_fd = open(path + 1, O_RDONLY);
  if (resource_fd == ERR) {
    process_bad_request(connfd);
  }

  if (fstat(resource_fd, &file_stats) == ERR) {
    process_bad_request(connfd);
  }

  if (sprintf(response,
              "HTTP/1.%d 200 OK\r\n"
              "Date: %s\r\n"
              "Server: Carlos server\r\n"
              "Last-Modified: %s\r\n"
              "Content-Length: %ld\r\n"
              "Content-Type: %s\r\n"
              "\r\n",
              minor_version, ctime(&tm), ctime(&file_stats.st_mtim.tv_sec),
              file_stats.st_size, get_mime_type(strchr(path, '.'))) < 0) {
    process_bad_request(connfd);
  }

  if (send(connfd, response, strlen(response), 0) == ERR) {
    process_bad_request(connfd);
  }
  
  bzero(response, BUFFER_SIZE);
  while (read(resource_fd, response, BUFFER_SIZE) != 0) {
    send(connfd, response, strlen(response), 0);
  }
}

void process_bad_request(int connfd) {return;}

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
