#include "utils.h"
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>

int euclidian_gcd(mpz_t a, mpz_t b) {
  if (a == NULL || b == NULL)
    return ERR;

  if (mpz_sgn(a) == 0 || mpz_sgn(b) == 0)
    return ERR;

  mpz_t r, t;
  mpz_inits(r, t, NULL);

  if (mpz_cmp(a, b) < 0) {
    mpz_swap(a, b);
  }

  while (mpz_sgn(b) != 0) {
    mpz_set(t, b);
    mpz_fdiv_r(r, a, b);
    mpz_set(b, r);
    mpz_set(a, t);
  }

  mpz_clears(r, t, NULL);
  return mpz_get_ui(a);
}
