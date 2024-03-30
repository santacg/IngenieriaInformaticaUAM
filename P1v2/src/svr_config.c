#include "../includes/svr_config.h"
#include <string.h>

status server_set_config(server_t *server) {
  FILE *pf = NULL;
  char line[LINE_SIZE] = "\0";
  status st = OK;

  pf = fopen("server_config.txt", "r");
  if (!pf)
    return ERROR;

  while (fgets(line, LINE_SIZE, pf)) {
    if (strncmp(line, "server_root", 11) == 0) {
      server->server_root = strstr(line, " ") + 1;
    } else if (strncmp(line, "max_clients", 11) == 0) {
      server->max_clients = atoi(strstr(line, " "));
    } else if (strncmp(line, "listen_port", 11) == 0) {
      server->listen_port = atoi(strstr(line, " "));
    } else if (strncmp(line, "server_signature", 16) == 0) {
      server->server_signature = strstr(line, " ") + 1;
    } else if (strncmp(line, "backlog", 7) == 0) {
      server->backlog = atoi(strstr(line, " "));
    } else {
      st = ERROR;
    }
  }
  
  fclose(pf);
  return st; 
}
