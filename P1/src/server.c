/**
 * @file server.c
 * @brief Implementation of a basic HTTP server.
 *
 * This file contains the main function to initialize a socket, bind it to a
 * specific port, and listen for incoming connections. Upon accepting a
 * connection, it dispatches the handling of the connection to a separate
 * thread. This allows the server to handle multiple requests simultaneously.
 *
 * @author Carlos García Santa Eduardo Junoy Ortega
 * @date 12/03/2024
 */

#include "../includes/httpresponse.h"
#include "../includes/server.h"
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
#include <signal.h>
#include <unistd.h>

#define LINE 32
#define SA struct sockaddr

int socket_fd;

void termination_handler (int signum){
  close(socket_fd);
  exit(EXIT_SUCCESS);
}

/**
 * @brief Handles a new connection in a separate thread.
 *
 * This function is the thread routine to handle HTTP requests from clients. It
 * detaches the thread to ensure that resources are freed upon completion,
 * processes the HTTP request using the provided connection file descriptor, and
 * then closes the connection.
 *
 * @param arg A pointer to the argument passed to the thread, which is the
 *            connection file descriptor cast to a void pointer.
 * @return Always returns NULL.
 */
void *handle_conn(void *arg) {
  struct server_connection svr_conn;
  svr_conn = *(struct server_connection *) arg;
  pthread_detach(pthread_self());
  process_http_request(svr_conn.connfd, svr_conn.server_root, svr_conn.server_signature);
  close(svr_conn.connfd);
  return NULL;
}

/**
 * @brief The main function to start the HTTP server.
 *
 * Initializes a socket, sets up the address structure, binds the socket to the
 * specified port, listens for incoming connections, and dispatches each
 * connection to a new thread for handling.
 *
 * @param argc The argument count.
 * @param argv The argument vector.
 * @return EXIT_SUCCESS on successful execution, EXIT_FAILURE on error.
 */
int main(int argc, char *argv[]) {
  FILE *config_file = NULL;
  char line[LINE];
  char *tok = NULL;
  long int conn_fd, port, max_clients;
  pthread_t tid;
  struct sockaddr_in address;
  struct sigaction new_action, old_action;
  struct server_connection svr_conn;
  
  new_action.sa_handler = termination_handler;
  sigemptyset(&new_action.sa_mask);
  new_action.sa_flags = 0;

  sigaction(SIGINT, NULL, &old_action);
  if (old_action.sa_handler != SIG_IGN)
    sigaction(SIGINT, &new_action, NULL);

  config_file = fopen("server.conf", "r");
  while (fgets(line, sizeof(line), config_file)) {
    if (strncmp(line, "server_root", 11) == 0) {
      strtok(line, "=");
      tok = strtok(NULL, "\0");
      strncpy(svr_conn.server_root, tok, strlen(tok));
    }
    else if (strncmp(line, "max_clients", 11) == 0) {
      strtok(line, "=");
      tok = strtok(NULL, "\0");
      max_clients = atol(tok);
    }
    else if (strncmp(line, "listen_port", 11) == 0) {
      strtok(line, "=");
      tok = strtok(NULL, "\0");
      port = atol(tok);
    }
    else if (strncmp(line, "server_signature", 16) == 0) {
      strtok(line, "=");
      tok = strtok(NULL, "\0");
      strncpy(svr_conn.server_signature, tok, strlen(tok));
    }
    else {

    }
  }
  
  /*
  if (daemon(1, 0) == -1) {
    perror("Error setting daemon");
    return EXIT_FAILURE;
  }
  */

  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd < 0) {
    perror("Error in socket");
    return EXIT_FAILURE;
  }

  address.sin_family = AF_INET;
  address.sin_port = htons(port);
  address.sin_addr.s_addr = INADDR_ANY;
  fprintf(stdout, "port: %ld\n", port);

  if (bind(socket_fd, (struct sockaddr *)&address, sizeof(address))) {
    perror("Binding socket");
    return EXIT_FAILURE;
  }

  fprintf(stdout, "Listening...\n");
  if (listen(socket_fd, max_clients) < 0) {
    perror("Error in listen");
    return EXIT_FAILURE;
  }

  while (1) {
    conn_fd = accept(socket_fd, (SA *)NULL, NULL);
    if (conn_fd < 0) {
      perror("Error in receiving connection");
      return EXIT_FAILURE;
    }
    svr_conn.connfd = conn_fd;
    pthread_create(&tid, NULL, &handle_conn, (void *)&svr_conn);
  }
}
