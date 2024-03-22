/**
 *
 * Descripcion: Implementation of function that generate permutations
 *
 * File: permutations.c
 * Autor: Carlos Aguirre
 * Version: 1.1
 * Fecha: 21-09-2019
 *
 */

#include "permutations.h"
#include <stdio.h>
#include <stdlib.h>

int *generate_sorted_perm(int N);


int swap(int *n1, int *n2)
{
  int aux;

  if (!n1 || !n2)
    return ERR;

  aux = *n1;
  *n1 = *n2;
  *n2 = aux;

  return OK;
}
/***************************************************/
/* Function: random_num Date:                      */
/* Authors:                                        */
/*                                                 */
/* Rutine that generates a random number           */
/* between two given numbers                       */
/*                                                 */
/* Input:                                          */
/* int inf: lower limit                            */
/* int sup: upper limit                            */
/* Output:                                         */
/* int: random number                              */
/***************************************************/
int random_num(int inf, int sup)
{
  int num;

  if (inf < 0 || sup < 0 || inf > sup)
    return ERR;

  num = inf + (double)(sup - inf + 1) * rand() / (RAND_MAX + 1.0);
  return num;
}

/***************************************************/
/* Function: generate_perm Date:                   */
/* Authors:                                        */
/*                                                 */
/* Rutine that generates a random permutation      */
/*                                                 */
/* Input:                                          */
/* int n: number of elements in the permutation    */
/* Output:                                         */
/* int *: pointer to integer array                 */
/* that contains the permitation                   */
/* or NULL in case of error                        */
/***************************************************/
int *generate_perm(int N)
{
  int i, pos;
  int *perm = NULL;

  if (N < 0)
    return NULL;

  perm = (int *)malloc(sizeof(int) * N);

  if (!perm)
    return NULL;

  for (i = 0; i < N; i++)
  {
    perm[i] = i + 1;
  }

  for (i = 0; i < N; i++)
  {
    pos = random_num(0, N - 1);
    if (pos == ERR)
      return NULL;

    if (swap(&perm[i], &perm[pos]) == ERR)
      return NULL;
  }

  return perm;
}

/***************************************************/
/* Function: generate_permutations Date:           */
/* Authors:                                        */
/*                                                 */
/* Function that generates n_perms random          */
/* permutations with N elements                    */
/*                                                 */
/* Input:                                          */
/* int n_perms: Number of permutations             */
/* int N: Number of elements in each permutation   */
/* Output:                                         */
/* int**: Array of pointers to integer that point  */
/* to each of the permutations                     */
/* NULL en case of error                           */
/***************************************************/
int **generate_permutations(int n_perms, int N)
{
  int i, j;
  int **l_perms = NULL;

  if (n_perms < 0 || N < 0)
    return NULL;

  l_perms = (int **)malloc(sizeof(int *) * n_perms);

  if (!l_perms)
    return NULL;

  for (i = 0; i < n_perms; i++)
  {
    l_perms[i] = generate_perm(N);
    if (!l_perms[i])
    {
      for (j = 0; j < i; j++)
      {
        free(l_perms[j]);
      }
      free(l_perms);
      return NULL;
    }
  }

  return l_perms;
}

int *generate_sorted_perm(int N)
{
  int i;
  int *perm = NULL;

  if (N < 0)
    return NULL;

  perm = (int *)malloc(sizeof(int) * N);

  if (!perm)
    return NULL;

  for (i = 0; i < N; i++)
  {
    perm[i] = i + 1;
  }

  return perm;
}

/***************************************************/
/* Function: generate_permutations Date:           */
/* Authors:                                        */
/*                                                 */
/* Function that generates n_perms random          */
/* permutations with N elements                    */
/*                                                 */
/* Input:                                          */
/* int n_perms: Number of permutations             */
/* int N: Number of elements in each permutation   */
/* Output:                                         */
/* int**: Array of pointers to integer that point  */
/* to each of the permutations                     */
/* NULL en case of error                           */
/***************************************************/
int **generate_sorted_permutations(int n_perms, int N)
{
  int i, j;
  int **l_perms = NULL;

  if (n_perms < 0 || N < 0)
    return NULL;

  l_perms = (int **)malloc(sizeof(int *) * n_perms);

  if (!l_perms)
    return NULL;

  for (i = 0; i < n_perms; i++)
  {
    l_perms[i] = generate_sorted_perm(N);
    if (!l_perms[i])
    {
      for (j = 0; j < i; j++)
      {
        free(l_perms[j]);
      }
      free(l_perms);
      return NULL;
    }
  }

  return l_perms;
}