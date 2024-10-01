#include "utils.h"
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {

  if (argc != 8) {
    fprintf(stdout, "Invalid number of arguments\n");
    return ERR;
  }

  if (strncmp(argv[1], "afin", LINE) == 0) {
    fprintf(stdout, "afin\n");
  } else {
    fprintf(stdout, "Invalid method\n");
    return ERR;
  }

  return 0;
}
