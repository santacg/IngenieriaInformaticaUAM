#include "../Utils/utils.h"
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define N_LETRAS 26

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l value -i filein\n", argv[0]);
}

char **subsecuencias(FILE *in, int n_grama, int size) {
  // Asignar memoria para el array de subsecuencias
  char **subsecuencias = (char **)malloc(n_grama * sizeof(char *));
  if (subsecuencias == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    return NULL;
  }

  // Asignar memoria para cada subsecuencia y inicializarla
  for (int i = 0; i < n_grama; i++) {
    subsecuencias[i] = (char *)malloc((size + 1) * sizeof(char));
    if (subsecuencias[i] == NULL) {
      fprintf(stderr, "Error de asignación de memoria.\n");
      // Liberar memoria previamente asignada en caso de error
      for (int j = 0; j < i; j++) {
        free(subsecuencias[j]);
      }
      free(subsecuencias);
      return NULL;
    }
  }

  // Dividir el texto en subsecuencias
  for (int i = 0; i < n_grama; i++) {
    fseek(in, i,
          SEEK_SET); // Colocar la posición inicial para cada subsecuencia
    int j = 0;
    char c;

    while ((c = fgetc(in)) != EOF) {
      subsecuencias[i][j] = (c - 'A') + 1; // + 1 para evitar el barra 0
      j++;
      // Saltar n_grama caracteres para la siguiente letra de esta subsecuencia
      fseek(in, n_grama - 1, SEEK_CUR);
    }
    // Finalizar la subsecuencia con '\0' para que sea una cadena válida
    subsecuencias[i][size] = '\0';
  }

  return subsecuencias;
}

int indice_coincidencia(FILE *in, int n_grama) {
  if (in == NULL || n_grama <= 0) {
    return ERR;
  }

  // Obtenemos el tamaño del archivo
  fseek(in, 0, SEEK_END);
  int file_size = ftell(in);
  fseek(in, 0, SEEK_SET);
  // Declaramos un array de frecuencias
  int frec_c[N_LETRAS];

  // Inicializamos todas las frecuencias a 0
  for (int k = 0; k < N_LETRAS; k++) {
    frec_c[k] = 0;
  }
  // Calculamos el indice de coincidencia del texto completo
  char c;
  long double ic_total = 0;
  while ((c = fgetc(in)) != EOF) {
    c = c - 'A';
    frec_c[(int)c]++;
  }
  long double acc = 0;
  for (int k = 0; k < N_LETRAS; k++) {
    acc += frec_c[k] * (frec_c[k] - 1);
  }
  ic_total = acc / (file_size * (file_size - 1));
  printf("Indice de coincidencia del texto: %Lf\n", ic_total);

  // Obtenemos el tamaño de las subsecuencias
  int subs_size = file_size / n_grama;
  // Subdividimos el texto en segmentos
  char **subs = subsecuencias(in, n_grama, subs_size);

  // Obtenemos las frecuencias de los caracteres para cada subsecuencia
  ic_total = 0;
  for (int i = 0; i < n_grama; i++) {

    // Inicializamos todas las frecuencias a 0 por cada iteracion
    for (int k = 0; k < N_LETRAS; k++) {
      frec_c[k] = 0;
    }

    int N_i = strlen(subs[i]);
    for (int j = 0; j < N_i; j++) {
      c = subs[i][j];
      frec_c[(int)(c - 1)]++;
    }
    printf("\n");

    printf("Tabla de frecuencias: \n");
    for (int k = 0; k < N_LETRAS; k++) {
      printf("%c:%d ", k + 'A', frec_c[k]);
    }
    printf("\n");

    // Con las frecuencias calculamos el IC
    long double ic = 0, acc = 0;
    for (int k = 0; k < N_LETRAS; k++) {
      acc += frec_c[k] * (frec_c[k] - 1);
    }
    ic = acc / (N_i * (N_i - 1));
    ic_total += ic;
    printf("Indice de coincidencia: %Lf\n", ic);
  }

  printf("\n");
  ic_total /= n_grama;
  printf("Indice de coincidencia para longitud de clave %d: %Lf\n", n_grama,
         ic_total);
  for (int i = 0; i < n_grama; i++) {
    free(subs[i]);
  }
  free(subs);

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

  indice_coincidencia(in, n_grama);
  fclose(in);
}
