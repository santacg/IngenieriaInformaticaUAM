#ifndef UTILS_H
#define UTILS_H

#define SA struct sockaddr
#define POLL struct pollfd
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

typedef enum {
  OK = 0,
  ERROR = -1 
} status;

#endif
