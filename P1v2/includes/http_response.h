#ifndef HTTP_RESPONSE_H
#define HTTP_RESPONSE_H

#include <time.h>
#include <sys/types.h>

typedef struct http_response_s {
  char *protocol_version;
  int response_code;
  char *response_status;
  time_t time; 
  char *server_name;
  time_t last_modified;
  off_t content_size;
  char *content_type;
} http_response_t;

#endif 
