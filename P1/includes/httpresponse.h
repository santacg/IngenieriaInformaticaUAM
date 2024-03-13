#include <stdlib.h>
#include "picohttpparser.h"

#ifndef httpresponse_h
#define httpresponse_h 

void process_http_request(int connfd, char *server_name, char *server_root);

#endif 
