#include "utils.h"
#include <gmp.h>
#include <stdio.h>

int euclid_gcd(mpz_t a, mpz_t b, mpz_t *res) {
  if (a == NULL || b == NULL)
    return ERR;

  mpz_t zero;
  mpz_init(zero);

  if ((mpz_cmp(zero, a) == 0) || (mpz_cmp(zero, b) == 0)) {
    mpz_clear(zero);
    return ERR;
  }

  if (mpz_cmp(a, b) < 0) {
    mpz_t temp;
    mpz_init(temp);

    mpz_set(temp, a);
    mpz_set(a, b);
    mpz_set(b, temp);

    mpz_clear(temp);
  }

  mpz_t q, r, t;
  mpz_inits(q, r, t, NULL);

  while (mpz_cmp(zero, b) != 0) {
    mpz_set(t, b);
    mpz_fdiv_qr(q, r, a, b);
    mpz_set(b, r);
    mpz_set(a, t);
  }

  mpz_clear(zero);
  mpz_clears(q, r, t, NULL);

  mpz_set(*res, a);
  return 0;
}

int main(int argc, char **argv) {

  mpz_t a, b, res;
  mpz_inits(a, b, res, NULL);

  mpz_set_str(a, "10", 10);
  mpz_set_str(b, "5", 10);

  if (euclid_gcd(a, b, &res) != ERR) {
    gmp_printf("Resultado GCD: %Zd\n", a);
    mpz_clears(a, b, res, NULL);
    return 0;
  }

  mpz_clears(a, b, res, NULL);

  return -1;
}
