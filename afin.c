#include "utils.h"
#include <bits/time.h>
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int afin_encrypt(FILE *in, FILE *out, int m, int a, int b) {
  if (in == NULL || out == NULL)
    return ERR;

  int c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'a' && c <= 'z') {
      int e = ((a * (c - 'a') + b) % m + m) % m +
              'a'; // Ajuste para que quede en el rango
      fputc(e, out);
    } else {
      fputc(c, out);
    }
  }

  return OK;
}

int afin_decrypt(FILE *in, FILE *out, int m, int a, int b) {
  if (in == NULL || out == NULL)
    return ERR;

  int e;
  while ((e = fgetc(in)) != EOF) {
    if (e >= 'a' && e <= 'z') {
      int d = (((e - 'a' - b + m) * 15) % m + m) % m +
              'a'; // Ajuste del rango y uso de inverse_a
      fputc(d, out);
    } else {
      fputc(e, out);
    }
  }

  return OK;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  int mode = ERR, m = 0, a = 0, b = 0;

  for (int i = 1; i < argc; i++) {
    if (strncmp("-C", argv[i], 2) == 0) {
      if (mode == 1) {
        fprintf(stdout, "Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 0;
    } else if (strncmp("-D", argv[i], 2) == 0) {
      if (mode == 0) {
        fprintf(stdout, "Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 1;
    } else if (strncmp(argv[i], "-m", 2) == 0) {
      m = atoi(argv[++i]);
    } else if (strncmp(argv[i], "-a", 2) == 0) {
      a = atoi(argv[++i]);
    } else if (strncmp(argv[i], "-b", 2) == 0) {
      b = atoi(argv[++i]);
    } else if (strncmp(argv[i], "-i", 2) == 0) {
      if (strstr(argv[++i], ".txt") != NULL) {
        file_in = argv[i];
      }
    } else if (strncmp(argv[i], "-o", 2) == 0) {
      if (strstr(argv[++i], ".txt") != NULL) {
        file_out = argv[i];
      }
    }
  }

  if (mode == ERR || m == 0 || a == 0 || b == 0) {
    fprintf(stdout, "Invalid format\n");
    return ERR;
  }

  FILE *in, *out;
  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL)
      return ERR;
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

  if (mode == 0) {
    mpz_t a_mpz, b_mpz, one;
    mpz_init_set_ui(a_mpz, a);
    mpz_init_set_ui(b_mpz, b);
    mpz_init_set_str(one, "1", 10);

    int res = euclidian_gcd(a_mpz, b_mpz);

    if (res != 1) {
      fprintf(stdout, "numbers %d and %d have a gcd = 1, cannot encrypt text\n",
              a, b);
      mpz_clears(a_mpz, b_mpz, one, NULL);

      fclose(in);
      fclose(out);
      return ERR;
    }

    mpz_clears(a_mpz, b_mpz, one, NULL);
    clock_gettime(CLOCK_MONOTONIC, &start_time);
    afin_encrypt(in, out, m, a, b);
    clock_gettime(CLOCK_MONOTONIC, &end_time);
  } else {
    mpz_t a_mpz, b_mpz, one;
    mpz_init_set_ui(a_mpz, a);
    mpz_init_set_ui(b_mpz, b);
    mpz_init_set_str(one, "1", 10);

    int res = euclidian_gcd(a_mpz, b_mpz);

    if (res != 1) {
      fprintf(stdout, "numbers %d and %d have a gcd = 1, cannot encrypt text\n",
              a, b);
      mpz_clears(a_mpz, b_mpz, one, NULL);

      fclose(in);
      fclose(out);
      return ERR;
    }

    mpz_clears(a_mpz, b_mpz, one, NULL);
    clock_gettime(CLOCK_MONOTONIC, &start_time);
    afin_decrypt(in, out, m, a, b);
    clock_gettime(CLOCK_MONOTONIC, &end_time);
  }

  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);
  fclose(in);
  fclose(out);
  return OK;
}
