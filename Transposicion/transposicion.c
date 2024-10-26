#include "../Utils/utils.h"
#include <bits/time.h>
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int hill_transposition(FILE *in, FILE *out, int mode, char *p, int n, int m) {

  if (in == NULL || out == NULL)
    return ERR;

  int *matrix_t = (int *)calloc(n * n, sizeof(int));
  if (matrix_t == NULL)
    return ERR;

  int i = 0, j = 0;
  while (p[i] != '\0') {
    if (p[i] >= '0' && p[i] <= '9') {
      int col = (p[i] - '0') - 1;
      matrix_t[j * n + col] = 1;
      j++;
    }
    i++;
  }

  int *inv = NULL;
  if (mode == 1) {
    inv = (int *)calloc(n * n, sizeof(int *));
    if (inv == NULL) {
      free(matrix_t);
      return ERR;
    }
    if (mod_inverse(n, m, matrix_t, inv) == ERR) {
      free(matrix_t);
      free(inv);
      return ERR;
    }
  }

  int *matrix_text = (int *)malloc(sizeof(int) * n);
  if (matrix_text == NULL) {
    free(matrix_t);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int *matrix_out = (int *)malloc(sizeof(int) * n);
  if (matrix_text == NULL) {
    free(matrix_t);
    free(matrix_text);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int count = 0;
  char c;
  while ((c = fgetc(in)) != EOF) {
    matrix_text[count] = c - 'A';
    count++;

    if (count == n) {
      if (mode == 0) {
        matrix_multiplication(n, matrix_out, matrix_text, matrix_t);
      } else {
        matrix_multiplication(n, matrix_out, matrix_text, inv);
      }

      for (int i = 0; i < n; i++) {
        int e = (matrix_out[i] % m) + 'A';
        fputc(e, out);
      }
      count = 0;
    }
  }

  free(matrix_t);
  free(matrix_text);
  free(matrix_out);

  if (mode == 1)
    free(inv);

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  char *p = NULL;
  int mode = ERR, n = ERR, m = ERR;

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
    } else if (strcmp(argv[i], "-p") == 0) {
      p = argv[++i];
      if (p == NULL) {
        fprintf(stderr, "Error: Invalid value for -p\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-n") == 0) {
      n = atoi(argv[++i]);
      if (n == 0) {
        fprintf(stderr, "Error: Invalid value for -n\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-m") == 0) {
      m = atoi(argv[++i]);
      if (m == 0) {
        fprintf(stderr, "Error: Invalid value for -m\n");
        return ERR;
      }
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

  if (n == ERR) {
    fprintf(stderr, "Error: n must be specified\n");
    return ERR;
  }

  FILE *in, *out;

  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL) {
      return ERR;
    }
  } else {
    in = stdin;
  }

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fclose(in);
      return ERR;
    }
  } else {
    out = stdout;
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  hill_transposition(in, out, mode, p, n, m);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
