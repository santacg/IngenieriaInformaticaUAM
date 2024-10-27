#include "../Utils/utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr,
          "Usage: %s {-C|-D} -m value -p permutation -n permutation_len -i "
          "infile -o "
          "outfile\n",
          argv[0]);
}

int hill_transposicion(FILE *in, FILE *out, int mode, char *p, int n, int m) {

  if (in == NULL || out == NULL)
    return ERR;

  // Reservamos memoria para la matriz de transposición
  int *matriz_transpuesta = (int *)calloc(n * n, sizeof(int));
  if (matriz_transpuesta == NULL)
    return ERR;

  // Rellenamos la matriz de transposición según los valores de p
  int i = 0, j = 0;
  while (p[i] != '\0') {
    if (p[i] >= '0' && p[i] <= '9') {
      int col = (p[i] - '0') - 1;
      matriz_transpuesta[j * n + col] = 1;
      j++;
    }
    i++;
  }

  // Si está en modo descifrado calcula la inversa de la matriz de transposición
  int *inv = NULL;
  if (mode == 1) {
    inv = (int *)calloc(n * n, sizeof(int *));
    if (inv == NULL) {
      free(matriz_transpuesta);
      return ERR;
    }
    if (mod_inverse(n, m, matriz_transpuesta, inv) == ERR) {
      free(matriz_transpuesta);
      free(inv);
      return ERR;
    }
  }

  int *matriz_texto = (int *)malloc(sizeof(int) * n);
  if (matriz_texto == NULL) {
    free(matriz_transpuesta);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int *matriz_out = (int *)malloc(sizeof(int) * n);
  if (matriz_out == NULL) {
    free(matriz_transpuesta);
    free(matriz_texto);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int count = 0;
  char c;
  while ((c = fgetc(in)) != EOF) {
    matriz_texto[count] = c - 'A';
    count++;

    if (count == n) {
      if (mode == 0) {
        matrix_multiplication(n, matriz_out, matriz_texto, matriz_transpuesta);
      } else {
        matrix_multiplication(n, matriz_out, matriz_texto, inv);
      }

      for (int i = 0; i < n; i++) {
        int e = (matriz_out[i] % m) + 'A';
        fputc(e, out);
      }
      count = 0;
    }
  }

  free(matriz_transpuesta);
  free(matriz_texto);
  free(matriz_out);

  if (mode == 1)
    free(inv);

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  char *p = NULL;
  int mode = ERR, n = 0, m = 0;

  int opt;
  if (argc < 7) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDm:k:n:i:o:")) != -1) {
    switch (opt) {
    case 'C':
      if (mode == MODE_DECRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = MODE_ENCRYPT;
      break;
    case 'D':
      if (mode == MODE_ENCRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = MODE_DECRYPT;
      break;
    case 'm':
      m = atoi(optarg);
      if (m <= 0) {
        fprintf(stderr, "Error: Invalid value for -m\n");
        return ERR;
      }
      break;
    case 'n':
      n = atoi(optarg);
      if (n <= 0) {
        fprintf(stderr, "Error: Invalid value for -n\n");
        return ERR;
      }
      break;
    case 'p':
      p = optarg;
      break;
    case 'i':
      file_in = optarg;
      break;
    case 'o':
      file_out = optarg;
      break;
    default:
      help(argv);
      return ERR;
    }
  }

  if (mode == ERR || m == 0 || n == 0 || p == NULL || file_in == NULL ||
      file_out == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in, *out;

  if (file_in != NULL) {
    in = fopen(file_in, "r+");
    if (in == NULL)
      return ERR;
  }

  // Obtenemos el tamañó del fichero de entrada
  fseek(in, 0, SEEK_END);
  long in_size = ftell(in);

  // Verificamos que el tamaño del fichero de entrada sea mutliplo de n
  // de lo contrario aplicamos padding
  long size_remainder = in_size % n;
  if (size_remainder != 0) {
    fseek(in, -1, SEEK_END);
    for (int i = 0; i < size_remainder; i++) {
      fputc('X', in);
    }
  }

  fseek(in, 0, SEEK_SET);

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fclose(in);
      return ERR;
    }
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  hill_transposicion(in, out, mode, p, n, m);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
