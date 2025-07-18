/**
 * @file server.h
 * 
 * @brief Server data structures
 * 
 * This header file contains the server data structures
 * declarations
 * 
 * @author Carlos Garcia Santa
 */

#ifndef SERVER_H
#define SERVER_H

#include "utils.h"
#include <sys/socket.h>

/**
 * @brief Server data structure
 * 
 * This data structure contains the server configuration
 * data
 */
typedef struct server {
  char server_root[LINE_SIZE];
  int max_open_sockets;
  int n_sockets;
  int listen_fd;
  int listen_port;
  char server_signature[LINE_SIZE];
  int backlog;
  int log_fd;
} server_t;

/**
 * @brief Thread data structure
 * 
 * This data structure contains the data to be passed
 * to the thread
 */
typedef struct thread_data {
  server_t *server_data;
  int conn_socket;
} thread_data_t;

#endif
