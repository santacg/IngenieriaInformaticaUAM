#include "../Utils/utils.h"
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#define MAX_LINE 2048

void help(char **argv) {
  fprintf(stderr,
          "Usage: %s {-C|-D} -m value -a value -b value -i infile -o outfile\n",
          argv[0]);
}

int encrypt(int e, const mpz_t m, const mpz_t a, const mpz_t b, mpz_t acc) {
  e = e - 'A';

  mpz_set_ui(acc, e);
  mpz_mul(acc, a, acc);
  mpz_add(acc, b, acc);
  mpz_mod(acc, acc, m);

  e = mpz_get_ui(acc);
  e = e + 'A';

  return e;
}

int decrypt(int d, const mpz_t m, const mpz_t b, mpz_t acc, mpz_t inverse) {
  d = d - 'A';

  mpz_set_ui(acc, d);
  mpz_sub(acc, acc, b);
  mpz_mod(acc, acc, m);
  mpz_mul(acc, acc, inverse);
  mpz_mod(acc, acc, m);

  d = mpz_get_ui(acc);
  d = d + 'A';

  return d;
}

int affine(FILE *in, FILE *out, int mode, const mpz_t m, const mpz_t a,
           const mpz_t b) {
  if (in == NULL || out == NULL)
    return ERR;

  mpz_t *inverse = NULL;
  if (mode != 0)
    inverse = extended_euclidian(a, m);

  mpz_t acc;
  mpz_init(acc);

  FILE *pf_in = NULL, *pf_out = NULL;
  if (in == stdin) {
    char buffer[MAX_LINE];
    fgets(buffer, MAX_LINE, in);
    pf_in = fopen("in.txt", "w+");
    pf_out = fopen("out.txt", "w+");
    fputs(buffer, pf_in);
    fseek(pf_in, 0, SEEK_SET);
    procesado(pf_in, pf_out); // Aqui se convierten en mayusculas
    fseek(pf_out, 0, SEEK_SET);
    fclose(pf_in);
    remove("in.txt");
    in = pf_out;
  }

  int c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      if (mode == MODE_ENCRYPT) {
        c = encrypt(c, m, a, b, acc);
      } else {
        c = decrypt(c, m, b, acc, *inverse);
      }

      fputc(c, out);
      mpz_set_ui(acc, 0);
    }
  }

  if (out == stdout)
    printf("\n");

  mpz_clear(acc);
  if (mode != 0) {
    mpz_clear(*inverse);
    free(inverse);
  }

  if (pf_out != NULL)
    fclose(pf_out);

  return OK;
}

int affine_nt(FILE *in, FILE *out, int mode, const mpz_t m, const mpz_t a,
              const mpz_t b, const mpz_t c) {
  if (in == NULL || out == NULL)
    return ERR;

  mpz_t *inverse = NULL;
  if (mode != 0)
    inverse = extended_euclidian(a, m);

  mpz_t acc, b_c;
  mpz_init(acc);
  mpz_init(b_c);
  mpz_set_ui(b_c, 0);
  mpz_add(b_c, c, b);

  FILE *pf_in = NULL, *pf_out = NULL;
  if (in == stdin) {
    char buffer[MAX_LINE];
    fgets(buffer, MAX_LINE, in);
    pf_in = fopen("in.txt", "w+");
    pf_out = fopen("out.txt", "w+");
    fputs(buffer, pf_in);
    fseek(pf_in, 0, SEEK_SET);
    procesado(pf_in, pf_out);
    fseek(pf_out, 0, SEEK_SET);
    fclose(pf_in);
    remove("in.txt");
    in = pf_out;
  }

  int d;
  while ((d = fgetc(in)) != EOF) {
    if (d >= 'A' && d <= 'Z') {
      if (mode == MODE_ENCRYPT) {
        d = encrypt(d, m, a, b_c, acc);
      } else {
        d = decrypt(d, m, b_c, acc, *inverse);
      }

      fputc(d, out);
      mpz_set_ui(acc, 0);
    }
  }

  if (out == stdout)
    printf("\n");

  mpz_clear(acc);
  mpz_clear(b_c);
  if (mode != 0) {
    mpz_clear(*inverse);
    free(inverse);
  }

  if (pf_out != NULL)
    fclose(pf_out);

  return OK;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  int mode = ERR;

  if (argc < 7) {
    help(argv);
    return ERR;
  }

  mpz_t a_mpz, b_mpz, m_mpz;
  mpz_inits(a_mpz, b_mpz, m_mpz, NULL);

  int opt;
  if (argc < 7) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDm:a:b:i:o:")) != -1) {
    switch (opt) {
    case 'C':
      if (mode == MODE_DECRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
        return ERR;
      }
      mode = MODE_ENCRYPT;
      break;
    case 'D':
      if (mode == MODE_ENCRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
        return ERR;
      }
      mode = MODE_DECRYPT;
      break;
    case 'm':
      mpz_set_ui(m_mpz, atoi(optarg));
      if (mpz_sgn(m_mpz) == ERR || mpz_sgn(m_mpz) == 0) {
        fprintf(stderr, "Error: Invalid value for -m\n");
        mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
        return ERR;
      }
      break;
    case 'a':
      mpz_set_ui(a_mpz, atoi(optarg));
      if (mpz_sgn(a_mpz) == ERR || mpz_sgn(a_mpz) == 0) {
        fprintf(stderr, "Error: Invalid value for -a\n");
        mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
        return ERR;
      }
      break;
    case 'b':
      mpz_set_ui(b_mpz, atoi(optarg));
      if (mpz_sgn(b_mpz) == ERR || mpz_sgn(b_mpz) == 0) {
        fprintf(stderr, "Error: Invalid value for -a\n");
        mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
        return ERR;
      }
      break;
    case 'i':
      file_in = optarg;
      break;
    case 'o':
      file_out = optarg;
      break;
    default:
      help(argv);
      mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
      return ERR;
    }
  }

  if (mode == ERR) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
    return ERR;
  }

  FILE *in = NULL, *out = NULL;
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
  if (euclidian_gcd(a_mpz, m_mpz) != 1) {
    fprintf(stdout,
            "Introduced numbers dont make up an injetive affine function"
            ", cannot encrypt nor decrypt text\n ");
    mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
    if (in != stdin)
      fclose(in);
    if (out != stdout)
      fclose(out);
    return ERR;
  }

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  affine(in, out, mode, m_mpz, a_mpz, b_mpz);
  clock_gettime(CLOCK_MONOTONIC, &end_time);

  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  if (in != stdin && out != stdout) {
    printf("time: %lf \n", elapsed_time);
  }

  mpz_clears(a_mpz, b_mpz, m_mpz, NULL);
  fclose(in);
  if (out != stdout) {
    remove("out.txt");
    fclose(out);
  }
  return OK;
}
