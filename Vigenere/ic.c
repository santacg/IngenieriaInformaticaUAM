#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define N_LETRAS 26
#define ERR -1

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l maxima longtitud de clave -i filein\n",
          argv[0]);
}

long double indice_coincidencia(FILE *in, int key_len) {
  if (in == NULL || key_len <= 0) {
    return ERR;
  }

  // Asignamos memoria para contadores de frecuencia por subsecuencia
  long long **frec_c = malloc(key_len * sizeof(long long *));
  if (frec_c == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    return ERR;
  }

  for (int i = 0; i < key_len; i++) {
    frec_c[i] = calloc(N_LETRAS, sizeof(long long));
    if (frec_c[i] == NULL) {
      fprintf(stderr, "Error de asignación de memoria.\n");
      // Liberamos memoria previamente asignada en caso de error
      for (int j = 0; j < i; j++) {
        free(frec_c[j]);
      }
      free(frec_c);
      return ERR;
    }
  }

  // Contador de letras por subsecuencia
  long long *N_i = calloc(key_len, sizeof(long long));
  if (N_i == NULL) {
    fprintf(stderr, "Error de asignación de memoria.\n");
    for (int i = 0; i < key_len; i++) {
      free(frec_c[i]);
    }
    free(frec_c);
    return ERR;
  }

  // Leemos el archivo y actualizamos las frecuencias
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

  // Calcular el indice de coincidencia para cada subsecuencia
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

  // Calculamos la media del IC y mostramos el resultado por pantalla
  ic_total /= key_len;
  printf("Indice de coincidencia para longitud de clave %d: %Lf\n", key_len,
         ic_total);

  // Liberamos la memoria dinamica asignada
  for (int i = 0; i < key_len; i++) {
    free(frec_c[i]);
  }
  free(frec_c);
  free(N_i);

  // Reiniciamos el puntero del archivo para las proximas lecturas
  fseek(in, 0, SEEK_SET);

  return ic_total;
}

int main(int argc, char *argv[]) {
  char *file_in = NULL;
  int max_key_len = 0;

  int opt;
  if (argc < 3) {
    help(argv);
    return ERR;
  }

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

  long double indices[max_key_len];
  long double max_ic = -1;

  // Para la longitud de clave maxima dada calculamos el IC promedio de cada
  // longitud hasta llegar al máximo
  for (int i = 1; i <= max_key_len; i++) {
    indices[i - 1] = indice_coincidencia(in, i);
    if (indices[i - 1] == ERR) {
      fclose(in);
      return ERR;
    }
    if (indices[i - 1] > max_ic) {
      max_ic = indices[i - 1];
    }
  }
  fclose(in);

  // Establecemos un umbral para que solo se seleccionen los ICs más altos
  long double lim_ic = max_ic * 0.9;
  int key_len = -1;
  long double ic = -1;

  for (int i = 1; i <= max_key_len; i++) {
    long double val = indices[i - 1];
    int current_len = i;
    int multiplo = 0;

    // Consideramos solo las longitudes con IC por encima del umbral
    if (val >= lim_ic) {
      // Verificamos si la longitud actual es múltiplo de una longitud menor ya
      // seleccionada
      for (int j = 1; j < current_len; j++) {
        if (indices[j - 1] >= lim_ic && current_len % j == 0) {
          multiplo = 1;
          break;
        }
      }

      if (!multiplo) {
        // Seleccionar esta longitud de clave
        key_len = current_len;
        ic = val;
        // Rompemos el bucle para obtener la longitud más pequeña posible
        break;
      }
    }
  }

  printf("La longitud de clave más probable es %d con un IC %Lf\n", key_len,
         ic);

  return 0;
}
