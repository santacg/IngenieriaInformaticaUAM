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

mpz_t *extended_euclidian(const mpz_t m, const mpz_t a) {
  mpz_t prev_u, u, prev_v, v, tmp, quotient, remainder;
  mpz_t a_cpy, m_cpy;

  mpz_init_set_ui(prev_u, 1);
  mpz_init_set_ui(u, 0);
  mpz_init_set_ui(prev_v, 0);
  mpz_init_set_ui(v, 1);

  mpz_init_set(a_cpy, a);
  mpz_init_set(m_cpy, m);

  mpz_init(tmp);
  mpz_init(quotient);
  mpz_init(remainder);

  while (mpz_sgn(m_cpy) != 0) {
    mpz_fdiv_qr(quotient, remainder, a_cpy, m_cpy);

    mpz_mul(tmp, quotient, u);
    mpz_sub(tmp, prev_u, tmp);
    mpz_set(prev_u, u);
    mpz_set(u, tmp);

    mpz_mul(tmp, quotient, v);
    mpz_sub(tmp, prev_v, tmp);
    mpz_set(prev_v, v);
    mpz_set(v, tmp);

    mpz_set(a_cpy, m_cpy);
    mpz_set(m_cpy, remainder);
  }

  if (mpz_sgn(prev_u) < 0) {
    mpz_add(prev_u, prev_u, m);
  }

  mpz_t *res = (mpz_t *)malloc(sizeof(mpz_t));

  mpz_init_set(*res, prev_u);
  mpz_clears(a_cpy, m_cpy, prev_u, prev_v, u, v, tmp, quotient, remainder,
             NULL);

  return res;
}

void cofactorize(int p, int q, int n, int temp[n][n], int matrix[n][n]) {
  int i = 0, j = 0;

  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      if (row != p && col != q) {
        temp[i][j++] = matrix[row][col];

        if (j == n - 1) {
          j = 0;
          i++;
        }
      }
    }
  }
}

int determinant(int n, int matrix[n][n]) {
  int det = 0;

  if (n == 1) {
    return matrix[0][0];
  }

  int temp[n][n];
  int sign = 1;

  for (int f = 0; f < n; f++) {
    cofactorize(0, f, n, temp, matrix);
    det += sign * matrix[0][f] * determinant(n - 1, matrix);

    sign = -sign;
  }

  return det;
}

void adjoint(int n, int matrix[n][n], int adj[n][n]) {

  if (n == 1) {
    adj[0][0] = 1;
    return;
  }

  int sign = 1;
  int temp[n][n];

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      cofactorize(i, j, n, temp, matrix);

      sign = ((i + j) % 2 == 0) ? 1 : -1;
      adj[j][i] = (sign) * (determinant(n, temp));
    }
  }
}

int inverse(int n, int matrix[n][n], int inverse[n][n]) {
  int det = determinant(n, matrix);
  if (det == 0) {
    return ERR;
  }

  int adj[n][n];
  adjoint(n, matrix, adj);

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      inverse[i][j] = adj[i][j] / det;
    }
  }

  return 0;
}

void matrix_multiplication(int n, int *matrix_r, int matrix_a[n],
                           int matrix_b[n][n]) {
  int acc = 0;

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      acc += matrix_a[j] * matrix_b[i][j];
    }
    matrix_r[i] = acc;
  }

  return;
}
