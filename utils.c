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

mpz_t *extended_euclidian(const mpz_t a, const mpz_t m) {
  mpz_t t, r, new_t, new_r, q, aux, *res = NULL;
  mpz_init_set_ui(t, 0);
  mpz_init_set_ui(new_t, 1);
  mpz_init_set(r, m);
  mpz_init_set(new_r, a);
  mpz_init(q);
  mpz_init(aux);

  while (mpz_sgn(new_r) != 0) {
    mpz_fdiv_q(q, r, new_r);

    mpz_set(t, new_t);
    mpz_sub(aux, t, q);
    mpz_mul(new_t, aux, new_t);

    mpz_set(r, new_r);
    mpz_sub(aux, r, q);
    mpz_mul(new_r, aux, new_r);
  }

  if (mpz_cmp_ui(r, 1) > 0) {
    mpz_clears(t, new_t, r, new_r, q, aux);
    return NULL;
  } else if (mpz_cmp_ui(t, 0) < 0) {
    mpz_add(t, t, m);
  }

  mpz_init_set(*res, t);
  mpz_clears(t, new_t, r, new_r, q, aux);
  return res;
}
