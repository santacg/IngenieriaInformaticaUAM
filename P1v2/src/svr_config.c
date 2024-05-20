#include "../includes/svr_config.h"

int server_set_config(server_t *server) {
  FILE *file = fopen("server_config.txt", "r");
  char line[LINE_SIZE];

  if (file == NULL) {
    perror("Error opening configuration file");
    return ERROR;
  }

  while (fgets(line, sizeof(line), file) != NULL) {
    char *key = strtok(line, " ");
    char *value = strtok(NULL, "\n");

    if (key == NULL || value == NULL) {
      continue;
    }

    if (strcmp(key, "server_root") == 0) {
      strncpy(server->server_root, value, LINE_SIZE);
    } else if (strcmp(key, "max_open_sockets") == 0) {
      server->max_open_sockets = atoi(value);
    } else if (strcmp(key, "listen_port") == 0) {
      server->listen_port = atoi(value);
    } else if (strcmp(key, "server_signature") == 0) {
      strncpy(server->server_signature, value, LINE_SIZE);
    } else if (strcmp(key, "backlog") == 0) {
      server->backlog = atoi(value);
    }
  }

  server->n_sockets = 0;
  fclose(file);
  return 0;
}
