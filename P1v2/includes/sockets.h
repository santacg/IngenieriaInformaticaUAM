#ifndef SOCKET_H
#define SOCKET_H

#include "utils.h"
#include "server.h"

int init_socket(server_t *server);

int socket_recv(int socket_fd, char *buf, size_t buf_len, int flags); 

int socket_send(int socket_fd, const char *buf, size_t buf_len, int flags); 

#endif
