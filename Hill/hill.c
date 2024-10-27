#include "../Utils/utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr,
          "Usage: %s {-C|-D} -m tamaño alfabeto -n tamaño matrix -k keyfile -i "
          "infile -o outfile\n",
          argv[0]);
}

int hill(FILE *in, FILE *out, FILE *k, int mode, int m, int n) {

  if (in == NULL || out == NULL || k == NULL)
    return ERR;

  // Se reserva memoria para la matriz de claves
  int *matriz_k = (int *)malloc(sizeof(int) * n * n);
  if (matriz_k == NULL)
    return ERR;

  int i = 0, j = 0;
  char c;
  // Leemos la matriz de claves
  while ((c = fgetc(k)) != EOF) {
    if (c >= '0' && c <= '9') {
      matriz_k[i * n + j] = c - '0';
      j++;
    }

    if (j == n) {
      j = 0;
      i++;
    }
  }

  // Verificamos que la matriz de claves define una funcion inyectiva para ello
  // se calcula el determinante
  if (determinant(n, matriz_k) == 0) {
    printf("Error: Determinant of matrix K is zero\n");
    free(matriz_k);
    return ERR;
  }

  int *inv = NULL;
  // Si vamos a desencriptar se calcula el inverso modular de la matriz de
  // claves
  if (mode == MODE_DECRYPT) {
    inv = (int *)malloc(sizeof(int *) * n * n);
    if (inv == NULL) {
      free(matriz_k);
      return ERR;
    }
    if (mod_inverse(n, m, matriz_k, inv) == ERR) {
      free(matriz_k);
      free(inv);
      return ERR;
    }
  }

  // Se reserva memoria para la matriz en la que se va a almacenar el texto que
  // se va leyendo
  int *matriz_texto = (int *)malloc(sizeof(int) * n);
  if (matriz_texto == NULL) {
    free(matriz_k);
    if (mode == MODE_DECRYPT)
      free(inv);
    return ERR;
  }

  // Se reserva memoria para la matriz en la que se va a almacenar el texto
  // cifrado o discifrado para su posterior impresion
  int *matriz_salida = (int *)malloc(sizeof(int) * n);
  if (matriz_texto == NULL) {
    free(matriz_k);
    free(matriz_texto);
    if (mode == MODE_DECRYPT)
      free(inv);
    return ERR;
  }

  int count = 0;
  // Se procesa el archivo de entrada proporcionado caracater a caracter
  while ((c = fgetc(in)) != EOF) {
    matriz_texto[count] = c - 'A';
    count++;

    // Cuando hayan suficientes caracteres en la matriz de texto estos se
    // encriptan o desencriptan
    if (count == n) {
      // Si estamos encriptando se multiplica la matriz de texto por la matriz
      // de claves
      if (mode == MODE_ENCRYPT) {
        matrix_multiplication(n, matriz_salida, matriz_texto, matriz_k);
      } else {
        // Si estamos desencriptando se multiplica la matriz de texto por el
        // inverso modular de la matriz de claves
        matrix_multiplication(n, matriz_salida, matriz_texto, inv);
      }

      // Se imprimen los caracteres en el archivo de salida
      for (int i = 0; i < n; i++) {
        int e = (matriz_salida[i] % m) + 'A';
        fputc(e, out);
      }
      // Reseteamos la cuenta
      count = 0;
    }
  }

  free(matriz_k);
  free(matriz_texto);
  free(matriz_salida);

  if (mode == 1)
    free(inv);

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL, *k_file = NULL;
  int mode = ERR, n = 0, m = 0;

  int opt;
  if (argc < 7) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDm:n:k:i:o:")) != -1) {
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
    case 'k':
      k_file = optarg;
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

  if (mode == ERR || m <= 0 || n <= 0 || k_file == NULL || file_in == NULL ||
      file_out == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in = NULL, *out = NULL, *k = NULL;

  if (k_file != NULL) {
    k = fopen(k_file, "r");
    if (k == NULL) {
      fprintf(stderr, "Error: Could not open key file %s\n", k_file);
      return ERR;
    }
  }

  if (file_in != NULL) {
    in = fopen(file_in, "r+");
    if (in == NULL) {
      fprintf(stderr, "Error: Could not open input file %s\n", file_in);
      fclose(k);
      return ERR;
    }
  }

  // Obtenemos el tamañó del fichero de entrada
  fseek(in, 0, SEEK_END);
  long in_size = ftell(in);

  // Verificamos que el tamaño del fichero de entrada sea mutliplo de n
  // de lo contrario aplicamos padding
  long size_resto = in_size % n;
  if (size_resto != 0) {
    fseek(in, -1, SEEK_END);
    for (int i = 0; i < size_resto; i++) {
      fputc('X', in);
    }
  }

  fseek(in, 0, SEEK_SET);

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fprintf(stderr, "Error: Could not open output file %s\n", file_out);
      fclose(k);
      fclose(in);
      return ERR;
    }
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  hill(in, out, k, mode, m, n);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(k);
  if (in != stdin)
    fclose(in);
  if (in != stdout)
    fclose(out);
  return OK;
}
