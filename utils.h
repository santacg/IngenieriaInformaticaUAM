#ifndef UTILS_H
#define UTILS_H

#define ERR -1
#define OK 0
#define LINE 1024

#include <gmp.h>

int euclidian_gcd(const mpz_t a, const mpz_t b);

mpz_t *extended_euclidian(const mpz_t m, const mpz_t a);

int inverse(int n, int matrix[n][n], int inverse[n][n]);

void matrix_multiplication(int n, int *matrix_r, int matrix_a[n],
                           int matrix_b[n][n]);

#endif
