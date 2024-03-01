#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

#define PORT 8081

int main(int argc, char *argv[]) {
  int server_socket, bind_socket, new_socket, read_size;
  struct sockaddr_in address;
  socklen_t addrlen = sizeof(address);
  char buffer[2048] = "\0";
  char *message = "Hello Mamaguevo\n";
  int opt = 1;

  server_socket = socket(AF_INET, SOCK_STREAM, 0);
  if (server_socket < 0) {
    perror("Error in socket");
    return EXIT_FAILURE;
  }

  address.sin_family = AF_INET;
  address.sin_port = htons(PORT);
  address.sin_addr.s_addr = INADDR_ANY;
  fprintf(stdout, "port: %d\n", address.sin_port);
  fprintf(stdout, "addr: %d ", address.sin_addr.s_addr);

  bind_socket =
      bind(server_socket, (struct sockaddr *)&address, sizeof(address));
  if (bind_socket < 0) {
    perror("Binding socket");
    return EXIT_FAILURE;
  }

  fprintf(stdout, "Im going to start listening\n");
  if (listen(server_socket, 5) < 0) {
    perror("Error in listen");
    return EXIT_FAILURE;
  }

  new_socket = accept(server_socket, (struct sockaddr *)&address, &addrlen);
  if (new_socket < 0) {
    perror("Error in receiving connection");
    return EXIT_FAILURE;
  }

  do {
    read_size = read(new_socket, buffer, 2048 - 1);
    fprintf(stdout, "%s\n", buffer);
  } while(read_size != 0);

  close(new_socket);
  close(server_socket);

  return EXIT_SUCCESS;
}
