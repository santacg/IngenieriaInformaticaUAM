/**
 *
 * @author Carlos Garcia Santa
 */

#include "../Utils/utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr,
          "Uso: %s {-C|-D} -m tamaño alfabeto {-p permutacion | -n "
          "longitud} -i infile -o outfile",
          argv[0]);
}

int hill_transposicion(FILE *in, FILE *out, int mode, char *p, int n, int m) {

  if (in == NULL || out == NULL)
    return ERR;

  // Reservamos memoria para la matriz de transposición
  int *matriz_transposicion = (int *)calloc(n * n, sizeof(int));
  if (matriz_transposicion == NULL)
    return ERR;

  // Si p es NULL utilizamos permutation.dat
  int free_flag = 0;
  if (p == NULL) {
    free_flag = 1;
    p = malloc(sizeof(char) * (n + 1));

    if (p == NULL) {
      fprintf(stderr, "Error: Fallo al reservar memoria\n");
      return ERR;
    }

    FILE *perm_dat = fopen("permutacion.dat", "r");
    char c;
    int i = 0;
    while ((c = fgetc(perm_dat)) != EOF) {
      if (c >= '0' && c <= '9') {
        p[i] = c;
        i++;
      }
    }
    p[i] = '\0';
    fclose(perm_dat);
  }

  // Rellenamos la matriz de transposición según los valores de p
  int i = 0, j = 0;
  while (p[i] != '\0') {
    if (p[i] >= '0' && p[i] <= '9') {
      int col = (p[i] - '0') - 1;
      matriz_transposicion[j * n + col] = 1;
      j++;
    }
    i++;
  }

  // Si está en modo descifrado calcula la inversa de la matriz de transposición
  int *inv = NULL;
  if (mode == 1) {
    inv = (int *)calloc(n * n, sizeof(int));
    if (inv == NULL) {
      free(matriz_transposicion);
      return ERR;
    }
    if (mod_inversa(n, m, matriz_transposicion, inv) == ERR) {
      free(matriz_transposicion);
      free(inv);
      return ERR;
    }
  }

  int *matriz_texto = (int *)malloc(sizeof(int) * n);
  if (matriz_texto == NULL) {
    free(matriz_transposicion);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int *matriz_salida = (int *)malloc(sizeof(int) * n);
  if (matriz_salida == NULL) {
    free(matriz_transposicion);
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
        multiplicacion_matrices(n, matriz_salida, matriz_texto,
                                matriz_transposicion);
      } else {
        multiplicacion_matrices(n, matriz_salida, matriz_texto, inv);
      }

      for (int i = 0; i < n; i++) {
        int e = (matriz_salida[i] % m) + 'A';
        fputc(e, out);
      }
      count = 0;
    }
  }

  free(matriz_transposicion);
  free(matriz_texto);
  free(matriz_salida);

  if (free_flag == 1)
    free(p);

  if (mode == 1)
    free(inv);

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  char *p = NULL;
  int mode = ERR, n = 0, m = 0;

  int opt;
  if (argc < 6) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDm:p:n:i:o:")) != -1) {
    switch (opt) {
    case 'C':
      if (mode == MODE_DECRYPT) {
        fprintf(stderr,
                "Error: No puedes utilizar los dos modos al mismo tiempo\n");
        return ERR;
      }
      mode = MODE_ENCRYPT;
      break;
    case 'D':
      if (mode == MODE_ENCRYPT) {
        fprintf(stderr,
                "Error: No puedes utilizar los dos modos al mismo tiempo\n");
        return ERR;
      }
      mode = MODE_DECRYPT;
      break;
    case 'm':
      m = atoi(optarg);
      if (m <= 0) {
        fprintf(stderr, "Error: Valor incorrecto para -m\n");
        return ERR;
      }
      break;
    case 'n':
      if (p != NULL) {
        fprintf(stderr, "Error: No puedes utilizar n y p al mismo tiempo\n");
        return ERR;
      }
      n = atoi(optarg);
      if (n <= 0) {
        fprintf(stderr, "Error: Valor incorrecto para -n\n");
        return ERR;
      }
      break;
    case 'p':
      if (n != 0) {
        fprintf(stderr, "Error: No puedes utilizar n y p al mismo tiempo\n");
        return ERR;
      }
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

  if (mode == ERR || m == 0 || file_in == NULL || file_out == NULL) {
    fprintf(stderr, "Error: faltan argumentos\n");
    help(argv);
    return ERR;
  }

  if (n == 0 && p != NULL) {
    int i = 0, j = 0;
    while (p[i] != '\0') {
      if (p[i] >= '0' && p[i] <= '9') {
        j++;
      }
      i++;
    }
    n = j;
  } else if (n != 0 && p == NULL) {
    FILE *perm_file = fopen("permutacion.dat", "w");
    if (perm_file == NULL) {
      fprintf(stderr, "Error: Fallo al abrir el archivo dat\n");
      return ERR;
    }

    int flag = 0;
    int random[n];
    srand(time(NULL));
    for (int i = 0; i < n; i++) {
      random[i] = (rand() % n) + 1;
      for (int j = 0; j < i; j++) {
        if (random[i] == random[j]) {
          flag = 1;
          break;
        }
      }

      if (flag == 0) {
        fputc(random[i] + '0', perm_file);
      } else {
        i--;
      }
      flag = 0;
    }
    fclose(perm_file);

  } else {
    fprintf(stderr, "Error: faltan argumentos\n");
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
