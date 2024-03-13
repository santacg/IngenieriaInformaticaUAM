#ifndef server_h
#define server_h

#define MAX_LINE 255
struct server_connection {
  long int connfd;
  char server_root[MAX_LINE];
  char server_signature[MAX_LINE];
};


#endif
