#include "../includes/server.h"
#include "../includes/sockets.h"
#include "../includes/svr_config.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/poll.h>

void sig_handler(int signal) { exit(EXIT_SUCCESS); }

void *conn_handler(void *conn_fd) {
  if (pthread_detach(pthread_self()) == ERROR) {
    perror("Error detaching thread");
    return NULL;
  }

  return NULL;
}

int main(int argc, char *argv[]) {
  int server_fd, conn_fd;
  server_t *server = NULL;
  pthread_t *tid = NULL;
  struct sigaction act;
  struct pollfd *fds;

  server = (server_t *)malloc(sizeof(server_t));
  if (!server) {
    perror("Error allocating memory for server");
    return EXIT_FAILURE;
  }

  if (server_set_config(server) == ERROR) {
    fprintf(stderr, "Error setting server configuration");
    free(server);
    return EXIT_FAILURE;
  }

  fds = (POLL *)malloc(sizeof(POLL) * server->max_clients);
  if (!fds) {
    perror("Error allocating memory for pollfd");
    free(server);
    return EXIT_FAILURE;
  }

  server_fd = init_socket(server);
  if (server_fd == ERROR) {
    free(server);
    return EXIT_FAILURE;
  }

  if (sigfillset(&act.sa_mask) == ERROR) {
    perror("Error filling signal set");
    free(server);
    return EXIT_FAILURE;
  }

  act.sa_flags = 0;
  act.sa_handler = &sig_handler;
  if (sigaction(SIGINT, &act, NULL) == ERROR) {
    perror("Error setting signal action");
    free(server);
    return EXIT_FAILURE;
  }

  if (listen(server_fd, server->backlog) == ERROR) {
    perror("Error setting socket to listen");
    free(server);
    return EXIT_FAILURE;
  }
  fprintf(stdout, "Server listening on port: %d\n", server->listen_port);

  while (1) {
    if (server->n_conns <= server->max_clients) {
      if ((fds[server->n_conns].fd = accept(server_fd, NULL, NULL)) == ERROR) {
        perror("Error accepting connection");
        free(server);
        close(server_fd);
        return EXIT_FAILURE;
      }

      if (pthread_create(tid, NULL, &conn_handler, &conn_fd) == ERROR) {
        perror("Error creating thread");
        free(server);
        close(server_fd);
        return EXIT_FAILURE;
      }
    }
  }
}
