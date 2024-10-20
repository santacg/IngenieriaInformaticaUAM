#include "../Utils/utils.h"
#include <bits/time.h>
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int transposition(FILE *in, FILE *out, int mode, char *p, int n) {

  if (in == NULL || out == NULL || p == NULL)
    return ERR;

  int *permutation = (int *)malloc(sizeof(int) * n);
  if (permutation == NULL)
    return ERR;

  for (int i = 0, j = 0; p[i] != '\0'; i++) {
    if (p[i] >= '0' && p[i] <= '9') {
      permutation[j] = (p[i] - '0') - 1;
      j++;
    }
  }

  char c;
  int *inv = NULL;
  if (mode == 1) {
    inv = (int *)malloc(sizeof(int *) * n);
    if (inv == NULL) {
      free(permutation);
      return ERR;
    }
    for (int i = n - 1, j = 0; i >= 0; i--, j++) {
      inv[j] = permutation[i];
    }
  }

  int *matrix_text = (int *)malloc(sizeof(int) * n);
  if (matrix_text == NULL) {
    free(permutation);
    if (mode == 1)
      free(inv);
    return ERR;
  }

  int count = 0;
  while ((c = fgetc(in)) != EOF) {
    if (count == n) {
      if (mode == 0) {
        permutate(n, matrix_text, permutation);
      } else {
        permutate(n, matrix_text, inv);
      }

      for (int i = 0; i < n; i++) {
        int e = matrix_text[i] + 'A';
        fputc(e, out);
      }
      count = 0;
    }

    matrix_text[count] = c - 'A';
    count++;
  }

  if (count != 0) {
    for (int i = count; i < n; i++) {
      matrix_text[i] = 'X' - 'A';
    }

    if (mode == 0) {
      permutate(n, matrix_text, permutation);
    } else {
      permutate(n, matrix_text, inv);
    }

    for (int i = 0; i < n; i++) {
      int e = matrix_text[i] + 'A';
      fputc(e, out);
    }
  }

  free(permutation);
  free(matrix_text);

  if (mode == 1)
    free(inv);

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL, *k_file = NULL;
  char *p = NULL;
  int mode = ERR, n = ERR;

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
  transposition(in, out, mode, p, n);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
