#ifndef SERVER_H
#define SERVER_H

#include "utils.h"

typedef struct server_s {
  int n_conns;
  char *server_root;
  int max_clients;
  int listen_port;
  char *server_signature;
  int backlog;
} server_t;

#endif
