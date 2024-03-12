#include "httpresponse.h"
#include <arpa/inet.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>

#define PORT 8081
#define SA struct sockaddr

void *handle_conn(void *arg) {
  pthread_detach(pthread_self());
  process_http_request((long int)arg);
  close((long int)arg);
  return NULL;
}

int main(int argc, char *argv[]) {
  int socket_fd;
  long int conn_fd;
  pthread_t tid;
  struct sockaddr_in address;

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
