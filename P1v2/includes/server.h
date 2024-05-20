#ifndef SERVER_H
#define SERVER_H

#include "utils.h"
#include <sys/socket.h>

typedef struct server {
  char server_root[LINE_SIZE];
  int max_open_sockets;
  int n_sockets;
  int listen_fd;
  int listen_port;
  char server_signature[LINE_SIZE];
  int backlog;
  int log_fd;
} server_t;

typedef struct thread_data {
  server_t *server_data;
  int conn_socket;
} thread_data_t;

#endif
