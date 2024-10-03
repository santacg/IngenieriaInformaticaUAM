#ifndef UTILS_H
#define UTILS_H

#define ERR -1
#define OK 0
#define LINE 1024

#include <gmp.h>

int euclidian_gcd(const mpz_t a, const mpz_t b);

mpz_t *extended_euclidian(const mpz_t a, const mpz_t m);

#endif
