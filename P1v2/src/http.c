#include "../includes/http.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int send_file_content(int resource_fd, int socket_fd);

void format_http_date(time_t *time, char *buffer, size_t buffer_size);

const char *get_mime_type(const char *extension);

http_response_t *http_response_init();

int http_response_headers(http_response_t *http_response, char *method,
                          int method_len, int socket_fd);

int handle_get(http_response_t *http_response, char *path, int socket_fd);

int handle_post(http_response_t *http_response, char *path, int socket_fd,
                char *request_body);

int exe_script(http_response_t *http_response, char *path, char *params,
               int socket_fd, char *method);

int handle_http_request(thread_data_t *thread_data, char *buffer,
                        ssize_t bytes_received) {
  server_t *server = thread_data->server_data;
  int minor_version;
  const char *method, *path;
  char aux_path[MAX_URI_LENGTH] = "\0", aux_method[MAX_URI_LENGTH] = "\0";
  size_t method_len, path_len, num_headers;
  struct phr_header headers[100];
  http_response_t *http_response;

  if (buffer == NULL) {
    return ERROR;
  }

  num_headers = sizeof(headers) / sizeof(headers[0]);
  if (phr_parse_request(buffer, bytes_received, &method, &method_len, &path,
                        &path_len, &minor_version, headers, &num_headers,
                        0) == ERROR) {
    return ERROR;
  }

  http_response = http_response_init();
  if (http_response == NULL) {
    return ERROR;
  }

  http_response->server_name = server->server_signature;
  http_response->protocol_version = minor_version;

  if (path_len > MAX_URI_LENGTH) {
    http_response->status_code = HTTP_414_Request_URI_Too_Long;
    http_response->status_message = "Request-URI Too Long";
    http_response_headers(http_response, NULL, 0, thread_data->conn_socket);
    free(http_response);
    return OK;
  }

  strncpy(aux_path, path, path_len);
  aux_path[path_len] = '\0';
  strncpy(aux_method, method, method_len);
  aux_method[method_len] = '\0';

  if (strncmp("GET", method, method_len) == 0) {
    if (handle_get(http_response, aux_path, thread_data->conn_socket) ==
        ERROR) {
      if (http_response_headers(http_response, aux_method, method_len,
                                thread_data->conn_socket) == ERROR) {
        free(http_response);
        return ERROR;
      }
    }
  } else if (strncmp("POST", method, method_len) == 0) {
    char *request_body = strstr(buffer, "\r\n\r\n");

    if (handle_post(http_response, aux_path, thread_data->conn_socket,
                    request_body + 4) == ERROR) {
      if (http_response_headers(http_response, aux_method, method_len,
                                thread_data->conn_socket) == ERROR) {
        free(http_response);
        return ERROR;
      }
    }

  } else if (strncmp("OPTIONS", method, method_len) == 0) {
    http_response->status_code = HTTP_204_NO_CONTENT;
    http_response->status_message = "No Content";
    if (http_response_headers(http_response, aux_method, method_len,
                              thread_data->conn_socket) == ERROR) {
      free(http_response);
      return ERROR;
    }
  } else {
    http_response->status_code = HTTP_501_METHOD_NOT_IMPLEMENTED;
    http_response->status_message = "Method Not Implemented";
    if (http_response_headers(http_response, aux_method, method_len,
                              thread_data->conn_socket) == ERROR) {
      free(http_response);
      return ERROR;
    }
  }

  free(http_response);
  return OK;
}

int handle_get(http_response_t *http_response, char *path, int socket_fd) {
  int resource_fd;
  struct stat statbuf;

  if (path == NULL) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  if (strchr(path, '?') != NULL) {
    int params_len = 0;
    params_len = strchr(path, '?') - path;

    if (params_len <= 0) {
      http_response->status_code = HTTP_400_BAD_REQUEST;
      http_response->status_message = "Bad Request";
    }

    char *params;
    params = strchr(path, '?');
    if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
        strncmp(strchr(path, '.'), ".py", 3) == 0) {

      if (exe_script(http_response, path + 1, params + 1, socket_fd, "GET") ==
          ERROR) {
        return ERROR;
      }

      return OK;
    }
  }

  if (stat(path + 1, &statbuf) == ERROR) {
    http_response->status_code = HTTP_404_NOT_FOUND;
    http_response->status_message = "Not Found";
    return ERROR;
  }

  if (S_ISDIR(statbuf.st_mode)) {
    http_response->status_code = HTTP_400_BAD_REQUEST;
    http_response->status_message = "Bad Request";
    return ERROR;
  }

  http_response->content_size = statbuf.st_size;
  http_response->last_modified = statbuf.st_mtime;
  http_response->content_type = get_mime_type(path);

  if (http_response->content_type == NULL) {
    http_response->status_code = HTTP_400_BAD_REQUEST;
    http_response->status_message = "Bad Request";
    return ERROR;
  }

  resource_fd = open(path + 1, O_RDONLY);
  if (resource_fd == ERROR) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  http_response->status_code = HTTP_200_OK;
  http_response->status_message = "OK";
  if (http_response_headers(http_response, "GET", 3, socket_fd) == ERROR) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  if (send_file_content(resource_fd, socket_fd) == ERROR) {
    return ERROR;
  }

  return OK;
}

int handle_post(http_response_t *http_response, char *path, int socket_fd,
                char *request_body) {

  if (path == NULL) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  if (strncmp(strchr(path, '.'), ".php", 4) == 0 ||
      strncmp(strchr(path, '.'), ".py", 3) == 0) {
    
    if (exe_script(http_response, path + 1, request_body, socket_fd, "POST") ==
        ERROR) {
      return ERROR;
    }

    return OK;
  }

  http_response->status_code = HTTP_200_OK;
  http_response->status_message = "OK";
  if (http_response_headers(http_response, "POST", 4, socket_fd) == ERROR) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  return OK;
}

int http_response_headers(http_response_t *http_response, char *method,
                          int method_len, int socket_fd) {
  int bytes_written = 0, bytes_send = 0;
  char mod_time_str[MAX_TIME_STR_LENGTH] = "\0";
  char current_time_str[MAX_TIME_STR_LENGTH] = "\0";
  char headers[MAX_BUFFER_SIZE] = "\0";

  if (http_response == NULL || socket_fd < 0) {
    return ERROR;
  }

  time_t current_time = time(NULL);
  format_http_date(&current_time, current_time_str, sizeof(current_time_str));

  bytes_written =
      snprintf(headers, MAX_BUFFER_SIZE,
               "HTTP/1.1 %d %s\r\n"
               "Date: %s\r\n"
               "Server: %s\r\n",
               http_response->status_code, http_response->status_message,
               current_time_str, http_response->server_name);

  if (http_response->status_code == HTTP_200_OK ||
      http_response->status_code == HTTP_204_NO_CONTENT) {

    if (strncmp("GET", method, method_len) == 0 ||
        strncmp("POST", method, method_len) == 0) {
      format_http_date(&http_response->last_modified, mod_time_str,
                       sizeof(mod_time_str));

      bytes_written +=
          snprintf(headers + bytes_written, MAX_BUFFER_SIZE - bytes_written,
                   "Last-Modified: %s\r\n"
                   "Content-Length: %ld\r\n"
                   "Content-Type: %s\r\n"
                   "Keep-Alive: timeout=1, max=100\r\n"
                   "Connection: keep-alive\r\n",
                   mod_time_str, http_response->content_size,
                   http_response->content_type);
    } else {
      bytes_written +=
          snprintf(headers + bytes_written, MAX_BUFFER_SIZE - bytes_written,
                   "Allow: GET, POST, OPTIONS\r\n");
    }
  } else {
    bytes_written +=
        snprintf(headers + bytes_written, MAX_BUFFER_SIZE - bytes_written,
                 "Connection: close\r\n");
  }

  bytes_written += snprintf(headers + bytes_written,
                            MAX_BUFFER_SIZE - bytes_written, "\r\n");

  bytes_send = socket_send(socket_fd, headers, bytes_written, 0);
  if (bytes_send <= 0) {
    return ERROR;
  }

  return OK;
}

int exe_script(http_response_t *http_response, char *path, char *params,
               int socket_fd, char *method) {
  int resource_fd;
  FILE *fd = NULL;
  char *tok = NULL, *save;
  char command[MAX_URI_LENGTH];

  if (strncmp(strchr(path, '.'), ".php", 4) == 0) {
    strcpy(command, "php ");
  } else if (strncmp(strchr(path, '.'), ".py", 3) == 0) {
    strcpy(command, "python3 ");
  } else {
    http_response->status_code = HTTP_400_BAD_REQUEST;
    http_response->status_message = "Bad Request";
    return ERROR;
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
  fd = popen(command, "r");

  resource_fd = fileno(fd);

  struct stat statbuf;

  if (stat(path, &statbuf) == ERROR) {
    http_response->status_code = HTTP_404_NOT_FOUND;
    http_response->status_message = "Not Found";
    return ERROR;
  }

  http_response->content_size = statbuf.st_size;
  http_response->last_modified = statbuf.st_mtime;
  http_response->content_type = get_mime_type(path);

  if (http_response->content_type == NULL) {
    http_response->status_code = HTTP_400_BAD_REQUEST;
    http_response->status_message = "Bad Request";
    return ERROR;
  }

  if (http_response_headers(http_response, method, 3, socket_fd) == ERROR) {
    http_response->status_code = HTTP_500_INTERNAL_SERVER_ERROR;
    http_response->status_message = "Internal Server Error";
    return ERROR;
  }

  if (send_file_content(resource_fd, socket_fd) == ERROR) {
    return ERROR;
  }

  return OK;
}

int send_file_content(int resource_fd, int socket_fd) {
  int st = OK;
  char buffer[MAX_BUFFER_SIZE] = "\0";
  ssize_t bytes_read, bytes_send;

  if (resource_fd < 0 || socket_fd < 0) {
    close(resource_fd);
    return ERROR;
  }

  do {
    bytes_read = read(resource_fd, buffer, MAX_BUFFER_SIZE);
    if (bytes_read < 0) {
      st = ERROR;
      break;
    }

    if (bytes_read > 0) {
      bytes_send = socket_send(socket_fd, buffer, bytes_read, 0);
      if (bytes_send < 0) {
        st = ERROR;
        break;
      }
    }
  } while (bytes_read > 0 && st != ERROR);

  close(resource_fd);
  return st;
}

http_response_t *http_response_init() {
  http_response_t *http_response = NULL;

  http_response = (http_response_t *)malloc(sizeof(http_response_t));
  if (http_response == NULL) {
    return NULL;
  }

  http_response->status_code = 0;
  http_response->status_message = NULL;
  http_response->content_type = NULL;
  http_response->content_size = 0;
  http_response->server_name = NULL;
  http_response->protocol_version = 0;
  http_response->time = 0;
  http_response->last_modified = 0;

  return http_response;
}

const char *get_mime_type(const char *path) {
  size_t n_mime_types;
  const char *extension;

  n_mime_types = sizeof(mime_map) / sizeof(mime_map[0]);
  extension = strrchr(path, '.');

  if (!extension || *(extension + 1) == '\0') {
    return NULL;
  }

  extension++;

  for (size_t i = 0; i < n_mime_types; i++) {
    if (strcasecmp(extension, mime_map[i].extension) == 0) {
      return mime_map[i].mime_type;
    }
  }

  return "application/octet-stream";
}

void format_http_date(time_t *time, char *buffer, size_t buffer_size) {
  struct tm *gmt = gmtime(time);
  strftime(buffer, buffer_size, "%a, %d %b %Y %H:%M:%S GMT", gmt);
}
