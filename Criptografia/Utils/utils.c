/**
 *
 * @author Carlos Garcia Santa
 */

#include "utils.h"
#include <gmp.h>
#include <stdio.h>
#include <stdlib.h>

int euclides_mcd(const mpz_t a, const mpz_t b) {
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

mpz_t *euclides_extendido(const mpz_t a, const mpz_t m) {
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

void cofactorizar(int p, int q, int n, int *temp, int *matriz) {
  int i = 0, j = 0;

  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      if (row != p && col != q) {
        temp[i * (n - 1) + j++] = matriz[row * n + col];

        if (j == n - 1) {
          j = 0;
          i++;
        }
      }
    }
  }
}

int determinante(int n, int *matriz) {
  int det = 0;

  if (n == 1) {
    return *matriz;
  }

  int *tmp = (int *)malloc(sizeof(int) * n * n);
  if (tmp == NULL) {
    return ERR;
  }

  int signo = 1;

  for (int f = 0; f < n; f++) {
    cofactorizar(0, f, n, tmp, matriz);
    det += signo * matriz[f] * determinante(n - 1, tmp);
    signo = -signo;
  }

  free(tmp);
  return det;
}

int adjunta(int n, int *matriz, int *adj) {
  if (n == 1) {
    *(adj) = 1;
    return ERR;
  }

  int signo = 1;
  int *tmp = (int *)malloc(sizeof(int *) * n * n);
  if (tmp == NULL) {
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      cofactorizar(i, j, n, tmp, matriz);

      signo = ((i + j) % 2 == 0) ? 1 : -1;
      adj[j * n + i] = signo * determinante(n - 1, tmp);
    }
  }

  free(tmp);
  return 0;
}

int inversa(int n, int *matriz, int *inversa) {

  if (matriz == NULL || inversa == NULL) {
    return ERR;
  }

  int det = determinante(n, matriz);

  if (det == 0) {
    return ERR;
  }

  int *adj = (int *)malloc(sizeof(int) * n * n);
  if (adj == NULL) {
    return ERR;
  }

  if (adjunta(n, matriz, adj) == ERR) {
    free(adj);
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      inversa[i * n + j] = (adj[i * n + j] * det);
    }
  }

  free(adj);

  return 0;
}

int mod_inversa(int n, int mod, int *matriz, int *inversa) {

  if (matriz == NULL || inversa == NULL) {
    return ERR;
  }

  int det = determinante(n, matriz);

  if (det == 0) {
    return ERR;
  }

  mpz_t m, a;
  mpz_init_set_ui(m, mod);
  mpz_init_set_si(a, det);
  mpz_t *mp_det_inv = euclides_extendido(a, m);
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

  if (adjunta(n, matriz, adj) == ERR) {
    free(adj);
    return ERR;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      inversa[i * n + j] = (adj[i * n + j] * det_inv) % mod;
      if (inversa[i * n + j] < 0) {
        inversa[i * n + j] += mod;
      }
    }
  }

  free(adj);

  return 0;
}

void multiplicacion_matrices(int n, int *matriz_out, int *matriz_a,
                             int *matriz_b) {
  for (int i = 0; i < n; i++) {
    matriz_out[i] = 0;
  }

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      matriz_out[i] += matriz_b[i * n + j] * matriz_a[j];
    }
  }
}

void imprimir_matriz(int n, int *matriz) {
  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      printf("%d ", matriz[i * n + j]);
    }
    printf("\n");
  }
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
