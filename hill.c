#include "utils.h"
#include <bits/time.h>
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int hill(FILE *in, FILE *out, FILE *k, int mode, int m, int n) {

  if (in == NULL || out == NULL || k == NULL)
    return ERR;

  int matrix_k[n][n];
  int c, i = 0, j = 0;
  while ((c = fgetc(k)) != EOF) {
    if (c >= '0' && c <= '9') {
      matrix_k[i][j] = c - '0';
      j++;
    }

    if (j == n) {
      j = 0;
      i++;
    }
  }

  int count = 0;
  int matrix_text[n], matrix_e[n], matrix_d[n];
  if (mode == 0) {
    while ((c = fgetc(in)) != EOF) {
      if (count == n) {
        count = 0;
        matrix_multiplication(n, matrix_e, matrix_text, matrix_k);
        for (int i = 0; i < n; i++) {
          fputc((matrix_e[i] % m) + 'a', out);
        }
      }

      if (c >= 'a' && c <= 'z') {
        matrix_text[count] = c - 'a';
        count++;
      } else {
        fputc(c, out);
      }
    }
  } else {

    int matrix_inverse[n][n];
    inverse(n, matrix_k, matrix_inverse);

    while ((c = fgetc(in)) != EOF) {
      if (count == n) {
        count = 0;
        matrix_multiplication(n, matrix_d, matrix_text, matrix_inverse);
        for (int i = 0; i < n; i++) {
          fputc((matrix_d[i] % m) + 'a', out);
        }
      }

      if (c >= 'a' && c <= 'z') {
        matrix_text[count] = c - 'a';
        count++;
      } else {
        fputc(c, out);
      }
    }
  }

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL, *k_file = NULL;
  int mode = ERR, n, m;

  for (int i = 1; i < argc; i++) {
    if (strcmp("-C", argv[i]) == 0) {
      if (mode == 1) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 0; // Cifrar
    } else if (strcmp("-D", argv[i]) == 0) {
      if (mode == 0) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 1; // Descifrar
    } else if (strcmp(argv[i], "-m") == 0) {
      m = atoi(argv[++i]);
      if (m == 0) {
        fprintf(stderr, "Error: Invalid value for -m\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-n") == 0) {
      n = atoi(argv[++i]);
      if (n == 0) {
        fprintf(stderr, "Error: Invalid value for -n\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-k") == 0) {
      if (i + 1 >= argc || strstr(argv[++i], ".txt") == NULL) {
        fprintf(stderr, "Error: Invalid input matrix file\n");
        return ERR;
      }
      k_file = argv[i];
    } else if (strcmp(argv[i], "-i") == 0) {
      if (i + 1 >= argc || strstr(argv[++i], ".txt") == NULL) {
        fprintf(stderr, "Error: Invalid input file\n");
        return ERR;
      }
      file_in = argv[i];
    } else if (strcmp(argv[i], "-o") == 0) {
      if (i + 1 >= argc || strstr(argv[++i], ".txt") == NULL) {
        fprintf(stderr, "Error: Invalid output file\n");
        return ERR;
      }
      file_out = argv[i];
    } else {
      fprintf(stderr, "Error: Unknown parameter %s\n", argv[i]);
      return ERR;
    }
  }

  if (mode == ERR) {
    fprintf(stderr, "Error: -C or -D must be specified\n");
    return ERR;
  }

  FILE *in, *out, *k_in;

  if (k_file != NULL) {
    k_in = fopen(k_file, "r");
    if (k_in == NULL) {
      return ERR;
    }
  }

  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL) {
      fclose(k_in);
      return ERR;
    }
  } else {
    in = stdin;
  }

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fclose(k_in);
      fclose(in);
      return ERR;
    }
  } else {
    out = stdout;
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  hill(in, out, k_in, mode, m, n);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(k_in);
  fclose(in);
  fclose(out);
  return OK;
}
