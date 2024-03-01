#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

#define PORT 8081
#define SA struct sockaddr
#define BUFFER_SIZE 2048

int main(int argc, char *argv[]) {
  FILE *pf = NULL;
  int socket_fd, bytes_to_send;
  char buffer[BUFFER_SIZE];
  struct sockaddr_in servaddr;

  if (argc != 3) {
    fprintf(stdout, "usage: <hostIP> <FileToSend>");
    return EXIT_FAILURE;
  }

  socket_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (socket_fd < 0) {
    perror("Error opening socket");
    return EXIT_FAILURE;
  }

  servaddr.sin_family = AF_INET;
  servaddr.sin_port = htons(PORT);

  if (inet_pton(AF_INET, argv[1], &servaddr.sin_addr) <= 0) {
    perror("Error translating argument to AF_INET");
    return EXIT_FAILURE;
  }

  if (connect(socket_fd, (SA *)&servaddr, sizeof(servaddr)) < 0) {
    perror("Error connecting to sokcet");
    return EXIT_FAILURE;
  }

  pf = fopen(argv[2], "r");
  if (pf == NULL) {
    perror("Error opening file");
    return EXIT_FAILURE;
  }

  do {
    bytes_to_send = fread(buffer, BUFFER_SIZE, 1, pf);
    if (write(socket_fd, buffer, sizeof(buffer)) < 0) {
      perror("Error writing");
      return EXIT_FAILURE;
    }
  } while (bytes_to_send != 0);
  
  fclose(pf);
  close(socket_fd);

  return EXIT_SUCCESS;
}
