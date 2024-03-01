#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>

#define PORT 8081
#define SA struct sockaddr
#define BUFFER_SIZE 2048

int main(int argc, char *argv[]) {
  FILE *fp = NULL;
  int i, j, fd, nchildren, nbytes;
  pid_t pid;
  char buffer[BUFFER_SIZE];
  char request[BUFFER_SIZE], reply[BUFFER_SIZE];
  struct sockaddr_in servaddr;

  if (argc != 4) {
    fprintf(stdout, "usage: <IPaddr> <nchildren> <FileToSend>");
    return EXIT_FAILURE;
  }

  nchildren = atoi(argv[2]);
  fp = fopen(argv[3], "r");
  if (fp == NULL) {
    perror("Error opening file");
    return EXIT_FAILURE;
  }

  fd = socket(AF_INET, SOCK_STREAM, 0);
  if (fd < 0) {
    perror("Error opening socket");
    return EXIT_FAILURE;
  }

  servaddr.sin_family = AF_INET;
  servaddr.sin_port = htons(PORT);

  if (inet_pton(AF_INET, argv[1], &servaddr.sin_addr) <= 0) {
    perror("Error translating argument to AF_INET");
    return EXIT_FAILURE;
  }

  for (i = 0; i < nchildren; i++) {
    pid = fork();
    if (pid == 0) { /* Child */
      if (connect(fd, (SA *)&servaddr, sizeof(servaddr)) < 0) {
        perror("Error connecting to sokcet");
        return EXIT_FAILURE;
      }
      while (!EOF) {
        if (fread(request, sizeof(request), 1, fp) == -1) {
          perror("Error reading from file");
          return EXIT_FAILURE;
        }
      }

      if (write(fd, request, strlen(request)) == -1) {
        perror("Error writing request");
        return EXIT_FAILURE;
      }

      if (read(fd, reply, sizeof(reply)) == -1) {
        perror("Error reading reply");
        return EXIT_FAILURE;
      }
      
      close(fd);
      return EXIT_SUCCESS;
    }
  }
 
  waitpid(-1, NULL, 0);
  fclose(fp);

  return EXIT_SUCCESS;
}
