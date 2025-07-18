/**
 * @file sockets.c 
 * 
 * @brief Socket functions implementation
 * @author Carlos Garcia Santa
 */

#include "../includes/sockets.h"

int init_socket(server_t *server) {
  int socket_fd;
  struct sockaddr_in addr;

  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd == ERROR) {
    perror("Error opening socket");
    return ERROR;
  }

  memset(&addr, 0, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_port = htons(server->listen_port);
  addr.sin_addr.s_addr = INADDR_ANY;

  if (setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, &(int){1}, sizeof(int)) ==
      ERROR) {
    perror("Error setting socket options");
    return ERROR;
  }

  if (bind(socket_fd, (SA *)&addr, sizeof(addr)) == ERROR) {
    perror("Error binding socket");
    return ERROR;
  }

  return socket_fd;
}

int socket_send(int socket_fd, const char *buf, size_t buf_len, int flags) {
  int ret;

  if (!buf || socket_fd < 0) {
    return ERROR;
  }

  ret = send(socket_fd, buf, buf_len, flags);
  if (ret < 0) {
    return ERROR;
  }

  return ret;
}

int socket_recv(int socket_fd, char *buf, size_t buf_len, int flags) {
  int ret;

  if (!buf || socket_fd < 0) {
    return ERROR;
  }

  ret = recv(socket_fd, buf, buf_len, flags);
  if (ret < 0) {
    return ERROR;
  }

  return ret;
}
