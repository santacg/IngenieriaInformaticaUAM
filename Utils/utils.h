#ifndef UTILS_H
#define UTILS_H

#define MODE_ENCRYPT 0
#define MODE_DECRYPT 1
#define ERR -1
#define OK 0
#define LINE 1024

#include <gmp.h>
#include <stdio.h>

int euclidian_gcd(const mpz_t a, const mpz_t b);

mpz_t *extended_euclidian(const mpz_t m, const mpz_t a);

int determinant(int n, int *matrix);

int inverse(int n, int *matrix, int *inverse);

int mod_inverse(int n, int mod, int *matrix, int *inverse);

void matrix_multiplication(int n, int *matrix_out, int *matrix_a,
                           int *matrix_b);

void display_matrix(int n, int *matrix);

void procesado(FILE *in, FILE *out);

#endif
