#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define N_LETRAS 26
#define ERR -1

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l value -i filein\n", argv[0]);
}

long double indice_coincidencia(FILE *in, int key_len) {
  if (in == NULL || key_len <= 0) {
    return ERR;
  }

  // Inicializar contadores
  long long **frec_c = (long long **)malloc(key_len * sizeof(long long *));
  if (frec_c == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    return ERR;
  }

  for (int i = 0; i < key_len; i++) {
    frec_c[i] = (long long *)calloc(N_LETRAS, sizeof(long long));
    if (frec_c[i] == NULL) {
      fprintf(stderr, "Error de asignación de memoria.\n");
      for (int j = 0; j < i; j++) {
        free(frec_c[j]);
      }
      free(frec_c);
      return ERR;
    }
  }

  long long *N_i = (long long *)calloc(key_len, sizeof(long long));
  if (N_i == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    for (int i = 0; i < key_len; i++) {
      free(frec_c[i]);
    }
    free(frec_c);
    return ERR;
  }

  // Leer el archivo y actualizar frecuencias
  int c;
  long long pos = 0;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      int idx = pos % key_len;
      frec_c[idx][c - 'A']++;
      N_i[idx]++;
      pos++;
    }
  }

  // Calcular IC para cada subsecuencia
  long double ic_total = 0.0;
  for (int i = 0; i < key_len; i++) {
    long long acc = 0;
    for (int k = 0; k < N_LETRAS; k++) {
      acc += frec_c[i][k] * (frec_c[i][k] - 1);
    }
    if (N_i[i] > 1) {
      long double ic = (long double)acc / (N_i[i] * (N_i[i] - 1));
      ic_total += ic;
    }
  }

  // Obtener la media de los IC
  ic_total /= key_len;
  printf("Indice de coincidencia para longitud de clave %d: %Lf\n", key_len,
         ic_total);

  // Liberar memoria
  for (int i = 0; i < key_len; i++) {
    free(frec_c[i]);
  }
  free(frec_c);
  free(N_i);

  // Regresar el archivo al inicio para la siguiente iteración
  fseek(in, 0, SEEK_SET);

  return ic_total;
}

int main(int argc, char *argv[]) {
  char *file_in = NULL;
  int max_key_len = 0;

  int opt;
  while ((opt = getopt(argc, argv, "l:i:")) != -1) {
    switch (opt) {
    case 'l':
      max_key_len = atoi(optarg);
      if (max_key_len <= 0) {
        fprintf(stderr, "Error: Valor de longitud de clave incorrecto\n");
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

  if (file_in == NULL || max_key_len == 0) {
    fprintf(stderr, "Error: Faltan argumentos requeridos.\n");
    help(argv);
    return ERR;
  }

  FILE *in = fopen(file_in, "r");
  if (in == NULL) {
    fprintf(stderr, "Error: No se ha conseguido abrir el archivo de entrada\n");
    return ERR;
  }

  long double *indices =
      (long double *)malloc(max_key_len * sizeof(long double));
  if (indices == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    fclose(in);
    return ERR;
  }

  for (int i = 1, j = 0; i <= max_key_len; i++, j++) {
    indices[j] = indice_coincidencia(in, i);
    if (indices[j] == ERR) {
      fclose(in);
      free(indices);
      return ERR;
    }
  }

  fclose(in);

  long double eng_ic = 0.068;
  long double min_dist = -1;
  int min_idx = -1;

  for (int i = 0; i < max_key_len; i++) {
    long double val = indices[i];
    long double distancia = val - eng_ic;
    if (distancia < 0) {
      distancia = -distancia;
    }

    if (min_dist == -1 || distancia < min_dist) {
      min_dist = distancia;
      min_idx = i;
    }
  }

  if (min_idx >= 0) {
    printf("La clave más probable es %d con un IC %Lf\n", min_idx + 1,
           indices[min_idx]);
  } else {
    printf("Error: no se encontró un índice de coincidencia válido.\n");
  }

  free(indices);

  return 0;
}
