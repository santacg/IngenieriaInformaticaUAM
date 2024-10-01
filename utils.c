
#include "utils.h"
#include <gmp.h>
#include <stdio.h>

mpz_t *euclides(mpz_t *a, mpz_t *b) {
  if (a == NULL || b == NULL)
    return NULL;

  if ((mpz_cmp(0, *a) == 0) || (mpz_cmp(0, *b) == 0))
    return NULL;

  mpz_t t;
  mpz_init(t);
  while (mpz_cmp(0, *b) != 0) {
    b =
  }

  return NULL;
}
