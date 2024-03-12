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
  pthread_detach(pthread_self());
  process_http_request((long int)arg);
  close((long int)arg);
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
  int socket_fd;
  long int conn_fd;
  pthread_t tid;
  struct sockaddr_in address;
  /**
  if (argc != 2) {
    fprintf(stdout, "server usage: <port>");
    return EXIT_FAILURE;
  }
  */

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
