#include "picohttpparser.h"
#include <arpa/inet.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>

#define PORT 8081
#define BUFFER_SIZE 4096
#define OK 200
#define NOT_FOUND 404
#define SERVER_ERR 500
#define SA struct sockaddr

typedef struct {
    int code;
    const char* description;
} HttpStatus;

HttpStatus httpStatusCodes[] = {
    {200, "OK"},
    {404, "Not Found"},
    {500, "Internal Server Error"},
    {0, NULL} // Marcador del final del array
};

const char* getDescriptionForStatusCode(int code) {
    for (int i = 0; httpStatusCodes[i].description != NULL; i++) {
        if (httpStatusCodes[i].code == code) {
            return httpStatusCodes[i].description;
        }
    }
    return "Unknown HTTP Status Code";
}


const char *get_mime_type(char *file_ext) {
  if (strcasecmp(file_ext, "html") == 0) {
    return "text/html";
  } else if (strcasecmp(file_ext, "txt") == 0) {
    return "text/plain";
  } else if (strcasecmp(file_ext, "jpg") == 0 ||
             strcasecmp(file_ext, "jpeg") == 0) {
    return "image/jpeg";
  } else if (strcasecmp(file_ext, "gif") == 0) {
    return "image/gif";
  } else if (strcasecmp(file_ext, "mpeg") == 0 ||
             strcasecmp(file_ext, "mpg") == 0) {
    return "video/mpeg";
  } else if (strcasecmp(file_ext, "doc") == 0 ||
             strcasecmp(file_ext, "docx") == 0) {
    return "application/msword";
  } else if (strcasecmp(file_ext, "pdf") == 0) {
    return "application/pdf";
  } else {
    return NULL;
  }
}

void server_protocol(int socket_fd) {
  int pret, minor_version, fd, http_code = 200;
  char buffer[BUFFER_SIZE], response_header[BUFFER_SIZE], response_body[BUFFER_SIZE], *method, *path,
      *file_path = NULL, *response = NULL, date_str[BUFFER_SIZE], response_line[BUFFER_SIZE],
      last_modified_str[BUFFER_SIZE];
  size_t buff_len = 0, prev_buff_len = 0, response_len, method_len, path_len,
         num_headers, bytes_received = 0;
  char *server_name = "Miau server";
  const char *mime_type = NULL;
  struct stat file_stat;
  struct phr_header headers[100];
  struct tm tm;
  struct tm last_modified;
  off_t file_size;
  time_t actual_time;

  while (1) {
    bytes_received = read(socket_fd, buffer + buff_len, sizeof(buffer));
    if (bytes_received <= 0) {
      perror("Error reading from file");
      return;
    }

    prev_buff_len = buff_len;
    buff_len += bytes_received;
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

    if (buff_len == sizeof(buffer)) {
      fprintf(stderr, "HTTP request too long");
      return;
    }
  }
  
  file_path = (char *)malloc(sizeof(char) * (path_len + 1));
  if (!file_path) {
    perror("Error allocating memory");
    http_code = SERVER_ERR;
  }

  strncat(file_path, path, path_len);
  fd = open(file_path + 1, O_RDONLY);
  if (fd == -1) {
    perror("Error opening resource");
    http_code = NOT_FOUND;
  }
  mime_type = get_mime_type(strchr(file_path, '.') + 1);
  free(file_path);  

  fstat(fd, &file_stat);
  file_size = file_stat.st_size;
  last_modified = *gmtime(&file_stat.st_mtim.tv_sec);
  strftime(last_modified_str, sizeof(last_modified_str),
           "%a, %d %b %Y %H:%M:%S %Z", &last_modified);
  actual_time = time(0);
  tm = *gmtime(&actual_time);
  strftime(date_str, sizeof(date_str), "%a, %d %b %Y %H:%M:%S %Z", &tm);
  
  sprintf(response_line, "HTTP/1.0 %d %s", http_code, getDescriptionForStatusCode(http_code));
  sprintf(response_header,
          "%s\r\n"
          "Date: %s\r\n"
          "Server: %s\r\n"
          "Last-Modified: %s\r\n"
          "Content-Length: %ld\r\n"
          "Content-Type: %s\r\n"
          "\r\n",
          response_line, date_str, server_name, last_modified_str, file_size, mime_type);
  
  if (read(fd, response_body, BUFFER_SIZE) == -1)
    return;

  response = (char *) malloc(sizeof(char) * (strlen(response_header) + strlen(response_body)));
  bzero(response, (strlen(response_header) + strlen(response_body)));
  strcat(response, response_header);
  strcat(response, response_body);
  write(socket_fd, response, strlen(response));
  free(response); 
  return;
}

void *handle_conn(void *arg) {
  pthread_detach(pthread_self());
  server_protocol((long int)arg);
  close((long int)arg);
  return NULL;
}

int main(int argc, char *argv[]) {
  int socket_fd;
  long int conn_fd;
  pthread_t tid;
  struct sockaddr_in address;
  char buffer[BUFFER_SIZE];

  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd < 0) {
    perror("Error in socket");
    return EXIT_FAILURE;
  }

  address.sin_family = AF_INET;
  address.sin_port = htons(PORT);
  address.sin_addr.s_addr = INADDR_ANY;
  fprintf(stdout, "port: %d\n", PORT);

  if (bind(socket_fd, (struct sockaddr *)&address, sizeof(address))) {
    perror("Binding socket");
    return EXIT_FAILURE;
  }

  fprintf(stdout, "Listening...\n");
  if (listen(socket_fd, 100) < 0) {
    perror("Error in listen");
    return EXIT_FAILURE;
  }

  bzero(buffer, sizeof(buffer));
  while (1) {
    conn_fd = accept(socket_fd, (SA *)NULL, NULL);
    if (conn_fd < 0) {
      perror("Error in receiving connection");
      return EXIT_FAILURE;
    }
    pthread_create(&tid, NULL, &handle_conn, (void *)conn_fd);
  }

  close(socket_fd);

  return EXIT_SUCCESS;
}
