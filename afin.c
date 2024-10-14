#include "utils.h"
#include <bits/time.h>
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int affine(FILE *in, FILE *out, int mode, const mpz_t m, const mpz_t a,
           const mpz_t b) {
  if (in == NULL || out == NULL)
    return ERR;

  mpz_t *inverse = NULL;
  if (mode != 0)
    inverse = extended_euclidian(m, a);

  mpz_t acc;
  mpz_init(acc);

  int c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'a' && c <= 'z') {
      if (mode == 0) {
        c = c - 'a';

        mpz_set_ui(acc, c);
        mpz_mul(acc, a, acc);
        mpz_add(acc, b, acc);
        mpz_mod(acc, acc, m);

        c = mpz_get_ui(acc);
        c = c + 'a';
      } else {
        c = c - 'a';

        mpz_set_ui(acc, c);
        mpz_sub(acc, acc, b);
        mpz_mod(acc, acc, m);
        mpz_mul(acc, acc, *inverse);
        mpz_mod(acc, acc, m);

        c = mpz_get_ui(acc);
        c = c + 'a';
      }
    }
    fputc(c, out);
    mpz_set_ui(acc, 0);
  }

  mpz_clear(acc);
  if (mode != 0) {
    mpz_clear(*inverse);
    free(inverse);
  }
  return OK;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  int mode = ERR;
  mpz_t a_mpz, b_mpz, m_mpz;
  mpz_inits(a_mpz, b_mpz, m_mpz, NULL);

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
      if (i + 1 >= argc || mpz_set_str(m_mpz, argv[++i], 10) != 0) {
        fprintf(stderr, "Error: Invalid value for -m\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-a") == 0) {
      if (i + 1 >= argc || mpz_set_str(a_mpz, argv[++i], 10) != 0) {
        fprintf(stderr, "Error: Invalid value for -a\n");
        return ERR;
      }
    } else if (strcmp(argv[i], "-b") == 0) {
      if (i + 1 >= argc || mpz_set_str(b_mpz, argv[++i], 10) != 0) {
        fprintf(stderr, "Error: Invalid value for -b\n");
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
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
    return ERR;
  }

  if (mpz_cmp_ui(m_mpz, 0) == 0 || mpz_cmp_ui(a_mpz, 0) == 0 ||
      mpz_cmp_ui(b_mpz, 0) == 0) {
    fprintf(stderr, "Error: Parameters -m, -a, and -b must be non-zero\n");
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
    return ERR;
  }

  FILE *in, *out;
  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL) {
      mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
      return ERR;
    }
  } else {
    in = stdin;
  }

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
      fclose(in);
      return ERR;
    }
  } else {
    out = stdout;
  }

  struct timespec start_time, end_time;

/*
  int res = euclidian_gcd(a_mpz, b_mpz);

  if (res != 1) {
    fprintf(stdout, "Introduced numbers dont have a gcd = 1, cannot encrypt "
                    "nor decrypt text\n");
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);

    fclose(in);
    fclose(out);
    return ERR;
  }
  */
  int res = euclidian_gcd(a_mpz, m_mpz);

  if (res != 1) {
    fprintf(stdout, "Introduced numbers dont make up an injetive affine function"
                    ", cannot encrypt nor decrypt text\n ");
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);

    fclose(in);
    fclose(out);
    return ERR;
  }
           
  clock_gettime(CLOCK_MONOTONIC, &start_time);
  affine(in, out, mode, m_mpz, a_mpz, b_mpz);
  clock_gettime(CLOCK_MONOTONIC, &end_time);

  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
  fclose(in);
  fclose(out);
  return OK;
}
