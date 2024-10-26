#include "../Utils/utils.h"
#include <glib.h>
#include <gmp.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l value -i filein\n", argv[0]);
}

int kasiski(FILE *in, int n_grama) {

  if (in == NULL || n_grama <= 0) {
    return ERR;
  }

  // Obtenemos el tamaño del archivo y lo dividimos entre el tamaño de n-grama
  // para saber cuantos n-gramas vamos a tener
  fseek(in, 0, SEEK_END);
  long file_size = ftell(in);
  fseek(in, 0, SEEK_SET);

  // Reservamos memoria para el array de strings
  long gramas_size = file_size / n_grama;
  printf("%ld\n", gramas_size);
  char **gramas = (char **)malloc(gramas_size * sizeof(char *));

  if (gramas == NULL)
    return ERR;

  for (int i = 0; i < gramas_size; i++) {
    gramas[i] = (char *)malloc((n_grama * sizeof(char)) + 1);
    if (gramas[i] == NULL) {
      for (int j = 0; j < i; j++) {
        free(gramas[j]);
      }
      free(gramas);
      return ERR;
    }
  }

  // Leer los n-gramas del archivo
  for (int i = 0; i < gramas_size; i++) {
    if (fread(gramas[i], sizeof(char), n_grama, in) != (size_t)n_grama) {
      for (int j = 0; j <= i; j++) {
        free(gramas[j]);
      }
      free(gramas);
      return ERR;
    }
    gramas[i][n_grama] = '\0'; // Añadir terminador nulo
  }

  return 0;
}

int main(int argc, char *argv[]) {
  char *file_in = NULL;
  int n_grama;

  int opt;
  while ((opt = getopt(argc, argv, "l:i:")) != -1) {
    switch (opt) {
    case 'l':
      n_grama = atoi(optarg);
      if (n_grama <= 0) {
        fprintf(stderr, "Error: Valor de n-grama incorrecto\n");
        return ERR;
      }
      break;
    case 'i':
      file_in = optarg;
      break;
    default:
      help(argv);
      return ERR;
    }
  }

  if (file_in == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in = fopen(file_in, "r");
  if (in == NULL) {
    fprintf(stderr, "Error: No se ha conseguido abrir el archivo de entrada\n");
    return ERR;
  }

  kasiski(in, n_grama);
  fclose(in);
}
