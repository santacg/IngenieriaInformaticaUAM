#include <arpa/inet.h>
#include <bits/pthreadtypes.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define PORT 8081
#define BUFFER_SIZE 2048

#define SA struct sockaddr

void server_protocol(int socket_fd) {
  int nwrite, nread;
  char buffer[BUFFER_SIZE];
  char *reply = "Hola";

  for (nread = 0, nwrite = 0; ; ) {
    nread = read(socket_fd, buffer, sizeof(buffer));
    if (nread <= 0)
      return;

    nwrite = atoi(buffer);
    if (nwrite <= 0 || nwrite > BUFFER_SIZE) {
      perror("Error in client request");
      return;
    }

    bzero(buffer, sizeof(buffer));
    snprintf(buffer, nwrite, "%s", reply);
    write(socket_fd, buffer, sizeof(buffer));
  }

  return;
}

void *handle_conn(void *arg) {
  pthread_detach(pthread_self());
  server_protocol((long int)arg);
  close((long int)arg);
  return NULL;
}

int main(int argc, char *argv[]) {
  int socket_fd, read_size;
  long int conn_fd;
  pthread_t tid;
  struct sockaddr_in address;
  socklen_t addrlen = sizeof(address);
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
  if (listen(socket_fd, 50) < 0) {
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
