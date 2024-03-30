#include "../includes/sockets.h"

int init_socket(server_t *server) {
  int socket_fd;
  struct sockaddr_in addr;

  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd == ERROR) {
    perror("Error opening socket");
    return ERROR;
  }

  if (setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, &(int){1}, sizeof(int)) ==
      ERROR) {
    perror("Error setting socket options");
    return ERROR;
  }

  memset(&addr, 0, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_port = htonl(server->listen_port);
  addr.sin_addr.s_addr = INADDR_ANY;

  if (bind(socket_fd, (SA *)&addr, sizeof(addr)) == ERROR) {
    perror("Error binding socket");
    return ERROR;
  }
  
  return socket_fd;
}


