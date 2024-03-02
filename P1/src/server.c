#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <unistd.h>

#define PORT 8081
#define BUFFER_SIZE 2048

#define SA struct sockaddr

int main(int argc, char *argv[]) {
  int server_socket, connfd, read_size;
  struct sockaddr_in address;
  socklen_t addrlen = sizeof(address);
  char buffer[BUFFER_SIZE];

  server_socket = socket(AF_INET, SOCK_STREAM, 0);
  if (server_socket < 0) {
    perror("Error in socket");
    return EXIT_FAILURE;
  }

  address.sin_family = AF_INET;
  address.sin_port = htons(PORT);
  address.sin_addr.s_addr = INADDR_ANY;
  fprintf(stdout, "port: %d\n", PORT);

  if (bind(server_socket, (struct sockaddr *)&address, sizeof(address))) {
    perror("Binding socket");
    return EXIT_FAILURE;
  }

  fprintf(stdout, "Listening...\n");
  if (listen(server_socket, 50) < 0) {
    perror("Error in listen");
    return EXIT_FAILURE;
  }
 
  bzero(buffer, sizeof(buffer));
  for (;;) {
    struct sockaddr_in addr;
    socklen_t addr_len;
   
    connfd = accept(server_socket, (SA *)NULL, NULL);
    if (connfd < 0) {
      perror("Error in receiving connection");
      return EXIT_FAILURE;
    }

    do {
      read_size = read(connfd, buffer, BUFFER_SIZE - 1);
    } while(read_size != 0);
    
    close(connfd);  
  }
 
  close(server_socket);

  return EXIT_SUCCESS;
}
