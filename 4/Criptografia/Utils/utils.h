#ifndef UTILS_H
#define UTILS_H

#define MODE_ENCRYPT 0
#define MODE_DECRYPT 1
#define ERR -1
#define OK 0
#define LINE 1024

#include <gmp.h>
#include <stdio.h>

int euclides_mcd(const mpz_t a, const mpz_t b);

mpz_t *euclides_extendido(const mpz_t m, const mpz_t a);

int determinante(int n, int *matrix);

int inversa(int n, int *matrix, int *inversa);

int mod_inversa(int n, int mod, int *matrix, int *inversa);

void multiplicacion_matrices(int n, int *matriz_out, int *matriz_a,
                             int *matriz_b);

void imprimir_matriz(int n, int *matriz);

void procesado(FILE *in, FILE *out);

#endif
