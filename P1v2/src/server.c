#include "../includes/server.h"
#include "../includes/http.h"
#include "../includes/sockets.h"
#include "../includes/svr_config.h"
#include <bits/types/sigset_t.h>
#include <errno.h>
#include <pthread.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <unistd.h>

static volatile sig_atomic_t got_int_signal = 0;

void sig_handler(int sig) {
  if (sig == SIGINT) {
    got_int_signal = 1;
  }

  return;
}

static void *conn_handler(void *conn_fd);

int server_init(server_t *server);

static void *conn_handler(void *args) {
  int bytes_received, keep_alive = 1;
  char buffer[MAX_BUFFER_SIZE] = "\0";
  thread_data_t *thread_data = (thread_data_t *)args;
  struct timeval timeout;

  if (pthread_detach(pthread_self()) == ERROR) {
    dprintf(thread_data->server_data->log_fd, "Error detaching thread\n");
    thread_data->server_data->n_sockets--;
    close(thread_data->conn_socket);
    free(thread_data);
    return NULL;
  }

  timeout.tv_sec = 5;
  timeout.tv_usec = 0;

  if (setsockopt(thread_data->conn_socket, SOL_SOCKET, SO_RCVTIMEO, &timeout,
                 sizeof(timeout)) == ERROR) {
    dprintf(thread_data->server_data->log_fd, "Error setting socket timeout\n");
    thread_data->server_data->n_sockets--;
    close(thread_data->conn_socket);
    free(thread_data);
    return NULL;
  }

  if (setsockopt(thread_data->conn_socket, SOL_SOCKET, SO_KEEPALIVE,
                 &keep_alive, sizeof(keep_alive)) == ERROR) {
    dprintf(thread_data->server_data->log_fd, "Error setting socket timeout\n");
    thread_data->server_data->n_sockets--;
    close(thread_data->conn_socket);
    free(thread_data);
    return NULL;
  }

  while (keep_alive) {
    bytes_received =
        socket_recv(thread_data->conn_socket, buffer, MAX_BUFFER_SIZE, 0);

    if (bytes_received > 0) {
      if (handle_http_request(thread_data, buffer, bytes_received) == ERROR) {
        dprintf(thread_data->server_data->log_fd, "Error in http_request\n");
        break;
      }
    } else if (bytes_received == 0) {
      keep_alive = 0;
    } else {
      dprintf(thread_data->server_data->log_fd, "Error in socket_recv: %s\n",
              strerror(errno));
      break;
    }
  }

  thread_data->server_data->n_sockets--;
  close(thread_data->conn_socket);
  free(thread_data);
  return NULL;
}
int server_init(server_t *server) {
  if (server_set_config(server) == ERROR) {
    fprintf(stderr, "Error setting server configuration");
    return EXIT_FAILURE;
  }

  server->log_fd =
      open("log_file.txt", O_CREAT | O_WRONLY | O_TRUNC, S_IRUSR | S_IWUSR);
  if (server->log_fd == ERROR) {
    perror("Error opening file");
    return EXIT_FAILURE;
  }

  if (dprintf(server->log_fd, "server PID: %d\n", getpid()) == ERROR) {
    perror("Error saving PID to file");
    close(server->log_fd);
    return EXIT_FAILURE;
  }

  server->listen_fd = init_socket(server);
  if (server->listen_fd == ERROR) {
    dprintf(server->log_fd, "Error initializing socket\n");
    return EXIT_FAILURE;
  }

  if (listen(server->listen_fd, server->backlog) == ERROR) {
    dprintf(server->log_fd, "Error setting socket to listen\n");
    return EXIT_FAILURE;
  }
  dprintf(server->log_fd, "Server listening on port: %d\n",
          server->listen_port);

  return EXIT_SUCCESS;
}

int main() {
  int conn_fd, status = OK;
  pthread_t thread_id;
  server_t *server = NULL;
  thread_data_t *thread_data = NULL;
  sigset_t mask;
  struct sigaction act;

  if (sigfillset(&mask) == ERROR) {
    perror("Error filling signal set");
    return EXIT_FAILURE;
  }

  if (sigprocmask(SIG_BLOCK, &mask, NULL) == ERROR) {
    perror("Error setting mask");
    return EXIT_FAILURE;
  }

  if (sigfillset(&act.sa_mask) == ERROR) {
    perror("Error filling act mask");
    return EXIT_FAILURE;
  }

  act.sa_flags = 0;
  act.sa_handler = &sig_handler;
  if (sigaction(SIGINT, &act, NULL) == ERROR) {
    perror("Error setting signal action");
    return EXIT_FAILURE;
  }

  if (sigprocmask(SIG_UNBLOCK, &mask, NULL) == ERROR) {
    perror("Error setting mask");
    return EXIT_FAILURE;
  }

  server = (server_t *)malloc(sizeof(server_t));
  if (!server) {
    perror("Error allocating memory for server");
    return EXIT_FAILURE;
  }

  if (server_init(server) == EXIT_FAILURE) {
    close(server->log_fd);
    free(server);
    return EXIT_FAILURE;
  }

  while (got_int_signal == 0) {

    if (server->n_sockets >= server->max_open_sockets) {
      sleep(1);
      continue;
    }

    thread_data = (thread_data_t *)malloc(sizeof(thread_data_t));
    if (!thread_data) {
      dprintf(server->log_fd,
              "Error allocating memory for thread connection\n");
      status = ERROR;
      break;
    }

    if ((conn_fd = accept(server->listen_fd, NULL, NULL)) == ERROR) {
      if (errno == EINTR) {
        dprintf(server->log_fd, "Finishing by signal\n");
        break;
      } else {
        dprintf(server->log_fd, "Error accepting connection\n");
        status = ERROR;
        break;
      }
    }

    server->n_sockets++;
    thread_data->server_data = server;
    thread_data->conn_socket = conn_fd;

    if (pthread_create(&thread_id, NULL, &conn_handler, thread_data) == ERROR) {
      dprintf(server->log_fd, "Error creating thread\n");
      status = ERROR;
      break;
    }
  }

  free(thread_data);
  dprintf(server->log_fd, "Server exited with status: %d", status);
  close(server->log_fd);
  free(server);
  return status;
}
