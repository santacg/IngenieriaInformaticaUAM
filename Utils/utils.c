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
  mpz_t prev_u, u, prev_v, v, tmp, quotient, remainder;
  mpz_t a_cpy, m_cpy;

  mpz_init_set_ui(prev_u, 1);
  mpz_init_set_ui(u, 0);
  mpz_init_set_ui(prev_v, 0);
  mpz_init_set_ui(v, 1);

  mpz_init_set(a_cpy, a);
  mpz_init_set(m_cpy, m);

  if (mpz_cmp(a_cpy, m_cpy) > 0) {
    mpz_swap(a_cpy, m_cpy);
  }

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

void cofactorize(int p, int q, int n, int *temp, int *matrix) {
  int i = 0, j = 0;

  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      if (row != p && col != q) {
        temp[i * (n - 1) + j++] = matrix[row * n + col];

        if (j == n - 1) {
          j = 0;
          i++;
        }
      }
    }
  }
}

int determinant(int n, int *matrix) {
  int det = 0;

  if (n == 1) {
    return *matrix;
  }

  int *temp = (int *)malloc(sizeof(int) * n * n);
  if (temp == NULL) {
    return ERR;
  }

  int sign = 1;

  for (int f = 0; f < n; f++) {
    cofactorize(0, f, n, temp, matrix);
    det += sign * matrix[f] * determinant(n - 1, temp);
    sign = -sign;
  }

  free(temp);
  return det;
}

int adjoint(int n, int *matrix, int *adj) {
  if (n == 1) {
    *(adj) = 1;
    return ERR;
  }

  int sign = 1;
  int *temp = (int *)malloc(sizeof(int *) * n * n);
  if (temp == NULL) {
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      cofactorize(i, j, n, temp, matrix);

      sign = ((i + j) % 2 == 0) ? 1 : -1;
      adj[j * n + i] = sign * determinant(n - 1, temp);
    }
  }

  free(temp);
  return 0;
}

int inverse(int n, int *matrix, int *inverse) {

  if (matrix == NULL || inverse == NULL) {
    return ERR;
  }

  int det = determinant(n, matrix);

  if (det == 0) {
    return ERR;
  }

  int *adj = (int *)malloc(sizeof(int) * n * n);
  if (adj == NULL) {
    return ERR;
  }

  if (adjoint(n, matrix, adj) == ERR) {
    free(adj);
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      inverse[i * n + j] = (adj[i * n + j] * det);
    }
  }

  free(adj);

  display_matrix(n, matrix);
  return 0;
}

int mod_inverse(int n, int mod, int *matrix, int *inverse) {

  if (matrix == NULL || inverse == NULL) {
    return ERR;
  }

  int det = determinant(n, matrix);

  if (det == 0) {
    return ERR;
  }

  mpz_t m, a;
  mpz_init_set_ui(m, mod);
  mpz_init_set_si(a, det);
  mpz_t *mp_det_inv = extended_euclidian(a, m);
  mpz_clear(m);
  mpz_clear(a);

  if (mpz_sgn(*mp_det_inv) == 0) {
    return ERR;
  }

  int det_inv = mpz_get_si(*mp_det_inv);
  mpz_clear(*mp_det_inv);
  free(mp_det_inv);

  int *adj = (int *)malloc(sizeof(int) * n * n);
  if (adj == NULL) {
    return ERR;
  }

  if (adjoint(n, matrix, adj) == ERR) {
    free(adj);
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      inverse[i * n + j] = (adj[i * n + j] * det_inv) % mod;
      if (inverse[i * n + j] < 0) {
        inverse[i * n + j] += mod;
      }
    }
  }

  free(adj);

  return 0;
}

void matrix_multiplication(int n, int *matrix_out, int *matrix_a,
                           int *matrix_b) {
  for (int i = 0; i < n; i++) {
    matrix_out[i] = 0;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      matrix_out[i] += matrix_b[i * n + j] * matrix_a[j];
    }
  }
}

void display_matrix(int n, int *matrix) {
  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      printf("%d\t", matrix[i * n + j]);
    }
    printf("\n");
  }
}

int permutate(int n, int *array, int *permutation) {
  if (array == NULL || permutation == NULL) {
    return ERR;
  }

  int *tmp = (int *)malloc(sizeof(int) * n);
  if (tmp == NULL) {
    return ERR;
  }

  int perm_idx;
  for (int i = 0; i < n; i++) {
    perm_idx = permutation[i];
    tmp[i] = array[perm_idx];
  }

  for (int i = 0; i < n; i++) {
    array[i] = tmp[i];
  }

  free(tmp);
  return 0;
}

void procesado(FILE *in, FILE *out) {
  char c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      fputc(c, out);
    } else if (c >= 'a' && c <= 'z') {
      c = c + ('A' - 'a');
      fputc(c, out);
    }
  }
}
