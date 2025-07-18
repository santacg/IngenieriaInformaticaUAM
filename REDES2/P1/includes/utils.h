#ifndef UTILS_H
#define UTILS_H

#define SA struct sockaddr
#define MAX_BUFFER_SIZE 4096
#define LINE_SIZE 64

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <signal.h>
#include <pthread.h>
#include <poll.h>
#include <sys/poll.h>
#include <fcntl.h>

typedef enum {
  OK = 0,
  ERROR = -1 
} status;

typedef enum {
  TRUE = 1,
  FALSE = 0
} boolean;

#endif
