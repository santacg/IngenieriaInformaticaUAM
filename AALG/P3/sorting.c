/**
 *
 * Descripcion: Implementation of sorting functions
 *
 * Fichero: sorting.c
 * Autor: Carlos Aguirre
 * Version: 1.0
 * Fecha: 16-09-2019
 *
 */

#include "sorting.h"
#include <stdio.h>
#include <stdlib.h>

int swap_sort(int *tabla, int i1, int i2);
int min(int *array, int ip, int iu, int *OB);
int merge(int *tabla, int ip, int iu, int imedio);
int table_cpy(int *to, int *td, int ip, int iu);
int partition(int *tabla, int ip, int iu, int *pos);
int median(int *tabla, int ip, int iu, int *pos);
int median_avg(int *tabla, int ip, int iu, int *pos);
int median_stat(int *tabla, int ip, int iu, int *pos);

/***************************************************/
/* Function: swap_sort Date: 19/10/23              */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that swaps two elements in an array.    */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int i1: Index of the first element to swap      */
/* int i2: Index of the second element to swap     */
/* Output:                                         */
/* int: OK if successful, ERR in case of error     */
/***************************************************/

int swap_sort(int *tabla, int i1, int i2)
{
  int aux;

  if (!tabla)
    return ERR;

  aux = tabla[i1];
  tabla[i1] = tabla[i2];
  tabla[i2] = aux;

  return OK;
}
/***************************************************/
/* Function: SelectSort Date: 19/10/23             */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that sorts an array using the           */
/* selection sort algorithm. It also counts        */
/* and returns the number of basic operations      */
/* performed during the sort.                      */
/*                                                 */
/* Input:                                          */
/* int *array: Pointer to the array to be sorted   */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* Output:                                         */
/* int: Number of basic operations                 */
/* performed during the sort or ERR                */
/* in case of error                                */
/***************************************************/
int SelectSort(int *array, int ip, int iu)
{
  int min_pos, i, OB = 0;

  if (!array || ip < 0 || iu < ip)
    return ERR;

  for (i = ip; i <= iu - 1; i++)
  {
    min_pos = min(array, i, iu, &OB);
    if (min_pos == ERR)
    {
      return ERR;
    }
    if (swap_sort(array, i, min_pos) == ERR)
      return ERR;
  }

  return OB;
}

/***************************************************/
/* Function: SelectSortInv Date: 19/10/23          */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that sorts an array in descending       */
/* order using the selection sort algorithm.       */
/* It also counts and returns the number of        */
/* basic operations performed during the sort.     */
/*                                                 */
/* Input:                                          */
/* int *array: Pointer to the array to be sorted   */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* Output:                                         */
/* int: Number of basic operations                 */
/* performed during the sort or ERR                */
/* in case of error                                */
/***************************************************/
int SelectSortInv(int *array, int ip, int iu)
{
  int min_pos, i, OB = 0;

  if (!array || ip < 0 || iu < ip)
    return ERR;

  for (i = iu; i >= ip + 1; i--)
  {
    min_pos = min(array, ip, i, &OB);
    if (min_pos == ERR)
    {
      return ERR;
    }
    if (swap_sort(array, i, min_pos) == ERR)
      return ERR;
  }

  return OB;
}

/***************************************************/
/* Function: min Date: 19/10/23                    */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that finds the index of the             */
/* smallest value in a subarray between two        */
/* given indices. It also updates the count        */
/* of basic operations performed during the search.*/
/*                                                 */
/* Input:                                          */
/* int *array: Pointer to the array                */
/* int ip: Index of the first element of subarray  */
/* int iu: Index of the last element of subarray   */
/* int *OB: Pointer to count of basic operations   */
/* Output:                                         */
/* int: Index of the smallest value in the         */
/* subarray or ERR in case of error                */
/***************************************************/
int min(int *array, int ip, int iu, int *OB)
{
  int i, min;

  if (!array || ip < 0 || iu < ip || !OB)
    return ERR;

  min = ip;
  for (i = ip + 1; i <= iu; i++)
  {
    (*OB)++;
    if (array[i] < array[min])
    {
      min = i;
    }
  }

  return min;
}

/***************************************************/
/* Function: mergesort Date: 08/11/23              */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that sorts an array using the           */
/* merge sort algorithm. It also counts and        */
/* returns the number of basic operations          */
/* performed during the sort.                      */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array to be sorted   */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* Output:                                         */
/* int: Number of basic operations performed       */
/* during the sort or ERR in case of error         */
/***************************************************/
int mergesort(int *tabla, int ip, int iu)
{
  int med = 0, OB = 0, auxOB = 0;
  if (!tabla || iu < ip || ip < 0)
  {
    return ERR;
  }

  if (ip == iu)
  {
    return OK;
  }

  med = (iu + ip) / 2;
  OB += mergesort(tabla, ip, med);
  if (OB == ERR)
    return ERR;
  auxOB += mergesort(tabla, med + 1, iu);
  if (OB == ERR)
    return ERR;
  OB +=auxOB;
  auxOB = merge(tabla, ip, iu, med);
  if (auxOB == ERR)
  {
    return ERR;
  }
  OB += auxOB;
  return OB;
}

/***************************************************/
/* Function: merge Date: 08/11/23                  */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that merges two sorted halves of an     */
/* array. It also counts and returns the number    */
/* of basic operations performed during the merge. */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int ip: Index of the first element of the array */
/* int iu: Index of the last element of the array  */
/* int imedio: Middle index of the array           */
/* Output:                                         */
/* int: Number of basic operations performed       */
/* during the merge or ERR in case of error        */
/***************************************************/
int merge(int *tabla, int ip, int iu, int imedio)
{
  int i, j, k, OB = 0;
  int *tabla_aux = NULL;
  if (!tabla || ip > iu || ip < 0 || imedio < 0)
  {
    return ERR;
  }

  tabla_aux = (int *)malloc((iu - ip + 1) * sizeof(int));
  if (!tabla_aux)
  {
    return ERR;
  }

  OB++;
  for (i = ip, j = imedio + 1, k = 0; i <= imedio && j <= iu; k++)
  {
    OB++;
    if (tabla[i] < tabla[j])
    {
      tabla_aux[k] = tabla[i++];
    }
    else
    {
      tabla_aux[k] = tabla[j++];
    }
  }
  if (i > imedio)
  {
    while (j <= iu)
    {
      tabla_aux[k++] = tabla[j++];
    }
  }
  else if (j > iu)
  {
    while (i <= imedio)
    {
      tabla_aux[k++] = tabla[i++];
    }
  }
  if (table_cpy(tabla_aux, tabla, ip, iu) == ERR)
  {
    free(tabla_aux);
    return ERR;
  }

  free(tabla_aux);

  return OB;
}

/***************************************************/
/* Function: table_cpy Date: 08/11/23              */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that copies elements from one array     */
/* to another.                                     */
/*                                                 */
/* Input:                                          */
/* int *to: Pointer to the source array            */
/* int *td: Pointer to the destination array       */
/* int ip: Index of the first element to copy      */
/* int iu: Index of the last element to copy       */
/* Output:                                         */
/* int: OK if successful, ERR in case of error     */
/***************************************************/
int table_cpy(int *to, int *td, int ip, int iu)
{
  int i, j;
  if (!to || !td)
  {
    return ERR;
  }

  for (i = ip, j = 0; i <= iu; i++, j++)
  {
    td[i] = to[j];
  }

  return OK;
}

/***************************************************/
/* Function: quicksort Date: 08/11/23              */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that sorts an array using the           */
/* quicksort algorithm. It also counts and         */
/* returns the number of basic operations          */
/* performed during the sort.                      */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array to be sorted   */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* Output:                                         */
/* int: Number of basic operations performed       */
/* during the sort or ERR in case of error         */
/***************************************************/
int quicksort(int *tabla, int ip, int iu)
{
  int OB = 0, auxOB = 0, m;
  int pos;

  if (!tabla || ip > iu || ip <= 0 || iu <= 0)
    return ERR;

  /*Caso base*/
  if (ip == iu)
    return 0;

  auxOB = partition(tabla, ip, iu, &pos);
  if (auxOB != ERR)
    OB += auxOB;
  else
    return ERR;

  m = pos;
  /*Caso general*/
  if (ip < (m - 1))
  {
    auxOB = quicksort(tabla, ip, m - 1);
    if (auxOB != ERR)
      OB += auxOB;
    else
      return ERR;
  }
  if ((m + 1) < iu)
  {
    auxOB = quicksort(tabla, m + 1, iu);
    if (auxOB != ERR)
      OB += auxOB;
    else
      return ERR;
  }

  return OB;
}

/***************************************************/
/* Function: partition Date: 08/11/23              */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that partitions an array for quicksort. */
/* It also counts and returns the number of basic  */
/* operations performed during the partition.      */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* int *pos: Pointer to store the partition index  */
/* Output:                                         */
/* int: Number of basic operations performed       */
/* during the partition or ERR in case of error    */
/***************************************************/
int partition(int *tabla, int ip, int iu, int *pos)
{
  int i, m, k, auxOB, OB = 0;

  if (!tabla || !pos || ip > iu)
    return ERR;

  auxOB = median(tabla, ip, iu, pos);

  if (auxOB != ERR)
    OB += auxOB;
  else
    return ERR;

  m = *pos;
  k = tabla[m];
  if (swap_sort(tabla, ip, m) == ERR)
    return ERR;
  m = ip;

  for (i = ip + 1; i <= iu; i++)
  {
    OB++;
    if (tabla[i] < k)
    {
      m++;
      if (swap_sort(tabla, i, m) == ERR)
        return ERR;
    }
  }

  if (swap_sort(tabla, ip, m) == ERR)
    return ERR;

  *pos = m;
  return OB;
}

/***************************************************/
/* Function: median Date: Date: 08/11/23           */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that finds the median value in an array.*/
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* int *pos: Pointer to store the median index     */
/* Output:                                         */
/* int: 0 if successful, ERR in case of error     */
/***************************************************/
int median(int *tabla, int ip, int iu, int *pos)
{
  if (!tabla || !pos)
    return ERR;

  *pos = ip;
  return 0;
}

/***************************************************/
/* Function: median_avg Date: 08/11/23             */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that calculates the average index       */
/* between two indices and uses it as a median.    */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* int *pos: Pointer to store the median index     */
/* Output:                                         */
/* int: OK if successful, ERR in case of error     */
/***************************************************/
int median_avg(int *tabla, int ip, int iu, int *pos)
{

  if (!tabla || !pos)
    return ERR;

  *pos = (ip + iu) / 2;

  return 0;
}

/***************************************************/
/* Function: median_stat Date: 08/11/23            */
/* Authors: Carlos García Joaquín Abad             */
/*                                                 */
/* Routine that selects a median value based on    */
/* statistical properties of the array. It also    */
/* counts basic operations involved.               */
/*                                                 */
/* Input:                                          */
/* int *tabla: Pointer to the array                */
/* int ip: Index of the first element              */
/* int iu: Index of the last element               */
/* int *pos: Pointer to store the median index     */
/* Output:                                         */
/* int: Number of basic operations performed or    */
/* ERR in case of error                            */
/***************************************************/
int median_stat(int *tabla, int ip, int iu, int *pos)
{
  int OB = 0;

  if (tabla == NULL)
    return ERR;

  median(tabla, ip, iu, pos);

  OB++;

  if (tabla[ip] < tabla[*pos])
  {

    OB++;

    if (tabla[*pos] < tabla[iu])
    {
      return OB;
    }
    else
    {
      *pos = iu;
      return OB;
    }
  }

  else
  {

    OB++;

    if (tabla[ip] < tabla[iu])
    {
      *pos = ip;
      return OB;
    }
    else
    {
      *pos = iu;
      return OB;
    }
  }
}

