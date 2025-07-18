/**
 * @file sockets.h
 * 
 * @brief Socket functions header file
 * 
 * This header file contains the socket functions
 * declarations
 * 
 * @author Carlos Garcia Santa
 */

#ifndef SOCKET_H
#define SOCKET_H

#include "utils.h"
#include "server.h"

/**
 * @brief Initializes the server socket
 * 
 * @param server Server configuration
 * 
 * @return int File descriptor of the server socket
 */
int init_socket(server_t *server);

/**
 * @brief Sends data through a socket
 * 
 * @param socket_fd Socket file descriptor
 * @param buf Buffer to send
 * @param buf_len Buffer length
 * @param flags Flags
 * 
 * @return int Number of bytes sent, -1 if error
 */
int socket_recv(int socket_fd, char *buf, size_t buf_len, int flags); 

/**
 * @brief Receives data from a socket
 * 
 * @param socket_fd Socket file descriptor
 * @param buf Buffer to receive data
 * @param buf_len Buffer length
 * @param flags Flags
 * 
 * @return int Number of bytes received, -1 if error
 */
int socket_send(int socket_fd, const char *buf, size_t buf_len, int flags); 

#endif
