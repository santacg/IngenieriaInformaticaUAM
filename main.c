#include "utils.h"
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
  FILE *in_file = stdin;
  FILE *out_file = stdout;
  int m, a, b;

  if (argc < 8) {
    fprintf(stdout, "Invalid number of arguments\n");
    return ERR;
  }

  if (strncmp(argv[1], "-C", LINE) == 0) {
    fprintf(stdout, "El programa cifra\n");
  } else if (strncmp(argv[1], "-D", LINE) == 0) {
    fprintf(stdout, "El programa descifra\n");
  } else {
    fprintf(stdout, "Invalid method\n");
    return ERR;
  }

  if(strncmp(argv[2], "-m", LINE) == 0 && strncmp(argv[4], "-a", LINE) == 0 && strncmp(argv[6], "-b", LINE) == 0) {
    m = strtol(argv[3]);  /*-m(2) X(3)*/
    a = strtol(argv[5]);  /*-a(4) X(5)*/
    b = strtol(argv[7]);  /*-b(6) X(7)*/
  }
  else{
    fprintf(stdout, "Invalid arguments\n");
    return ERR;
  }

  /* a y b determinan una funcion afin inyectiva? */
  
  if(argc == 10){
    if (strncmp(argv[8], "-i", LINE) == 0) in_file = argv[9];         /*-i(8) X(9)*/
    else if (strncmp(argv[8], "-o", LINE) == 0) out_file = argv[9];   /*-o(8) X(9)*/
    else{
      fprintf(stdout, "Invalid arguments\n");
      return ERR;
    }
  }
  else if (argc == 12){
    if (strncmp(argv[8], "-i", LINE) == 0 && strncmp(argv[10], "-o", LINE) == 0){
      in_file = argv[9];    /*-i(8) X(9)*/
      out_file = argv[11];  /*-o(10) X(11)*/
    }
    else{
      fprintf(stdout, "Invalid arguments\n");
      return ERR;
    }
  }
  else{
    fprintf(stdout, "Invalid arguments\n");
    return ERR;
  }

  return 0;
}
