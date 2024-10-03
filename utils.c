#include "utils.h"
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>

int euclidian_gcd(const mpz_t a, const mpz_t b) {
  if (a == NULL || b == NULL)
    return ERR;

  if (mpz_sgn(a) == 0 || mpz_sgn(b) == 0)
    return ERR;

  mpz_t a_cpy, b_cpy, r, t;
  mpz_inits(a_cpy, b_cpy, r, t, NULL);
  mpz_set(a_cpy, a);
  mpz_set(b_cpy, b);

  if (mpz_cmp(a_cpy, b_cpy) < 0) {
    mpz_swap(a_cpy, b_cpy);
  }

  while (mpz_sgn(b_cpy) != 0) {
    mpz_set(t, b_cpy);
    mpz_fdiv_r(r, a_cpy, b_cpy);
    mpz_set(b_cpy, r);
    mpz_set(a_cpy, t);
  }

  int res = mpz_get_ui(a_cpy);
  mpz_clears(a_cpy, b_cpy, r, t, NULL);

  return res;
}

mpz_t *extended_euclidian(const mpz_t a, const mpz_t b) {
  mpz_t prev_u, u, prev_v, v, tmp, quotient, remainder;
  mpz_t a_cpy, b_cpy;
  mpz_init_set_ui(prev_u, 1);
  mpz_init_set_ui(u, 0);
  mpz_init_set_ui(prev_v, 0);
  mpz_init_set_ui(v, 1);

  mpz_init_set(a_cpy, a);
  mpz_init_set(b_cpy, b);

  mpz_init(tmp);
  mpz_init(quotient);
  mpz_init(remainder);

  while (mpz_sgn(b_cpy) != 0) {
    mpz_fdiv_qr(quotient, remainder, a_cpy, b_cpy);

    mpz_mul(tmp, quotient, u);
    mpz_sub(tmp, prev_u, tmp);
    mpz_set(prev_u, u);
    mpz_set(u, tmp);

    mpz_mul(tmp, quotient, v);
    mpz_sub(tmp, prev_v, tmp);
    mpz_set(prev_v, v);
    mpz_set(v, tmp);

    mpz_set(a_cpy, b_cpy);
    mpz_set(b_cpy, remainder);
  }

  mpz_t *res = (mpz_t *)malloc(sizeof(mpz_t));

  mpz_init_set(*res, u);
  mpz_clears(a_cpy, b_cpy, prev_u, prev_v, u, v, tmp, quotient, remainder,
             NULL);

  return res;
}
