/**
 *
 * Descripcion: Implementation of time measurement functions
 *
 * Fichero: times.c
 * Autor: Carlos Aguirre Maeso
 * Version: 1.0
 * Fecha: 16-09-2019
 *
 */
#define MAX_TIME 1024
#include "stdio.h"
#include "stdlib.h"

#include "times.h"
#include "time.h"
#include "search.h"
#include "sorting.h"
#include "limits.h"
#include <unistd.h>
#include <sys/time.h>


/***************************************************/
/* Function: average_sorting_time Date:            */
/*                                                 */
/* Your documentation                              */
/***************************************************/
short average_sorting_time(pfunc_sort metodo, int n_perms, int N, PTIME_AA time)
{
  int i, minOB, maxOB, OB;
  clock_t start_t, end_t;
  double total_t = 0.0, averageOB = 0.0;
  int **perms = NULL, **aux = NULL;

  if (!metodo || !time || n_perms <= 0 || N < 0)
    return ERR;

  perms = generate_permutations(n_perms, N);
  if (!perms)
    return ERR;

  aux = perms;
  minOB = INT_MAX;
  maxOB = INT_MIN;
  for (i = 0; i < n_perms; i++)
  {
    start_t = clock();
    OB = metodo(perms[i], 0, N - 1);
    end_t = clock();
    if (OB == ERR || start_t == ERR || end_t == ERR)
    {
      for (i = 0; i < n_perms; i++)
      {
        free(aux[i]);
      }
      free(aux);

      return ERR;
    }

    averageOB += OB;
    if (OB < minOB)
    {
      minOB = OB;
    }
    if (OB > maxOB)
    {
      maxOB = OB;
    }

    total_t += (double)(end_t - start_t) / CLOCKS_PER_SEC;
  }

  total_t = total_t / n_perms;
  averageOB = averageOB / n_perms;

  time->N = N;
  time->n_elems = n_perms;
  time->min_ob = minOB;
  time->max_ob = maxOB;
  time->average_ob = averageOB;
  time->time = total_t;

  for (i = 0; i < n_perms; i++)
  {
    free(aux[i]);
  }
  free(aux);

  return OK;
}

/***************************************************/
/* Function: generate_sorting_times Date:          */
/*                                                 */
/* Your documentation                              */
/***************************************************/
short generate_sorting_times(pfunc_sort method, char *file, int num_min, int num_max, int incr, int n_perms)
{
  int i, n = 0;
  PTIME_AA ptime;

  if (!method || !file || num_min < 0 || num_min > num_max || incr <= 0 || n_perms <= 0)
    return ERR;
  if (num_max == num_min)
  {
    n = 1;
  }
  else
  {
    n = ((num_max - num_min) / incr) + ((num_max - num_min) % incr);
  }
  
  ptime = (TIME_AA *)malloc(n * sizeof(TIME_AA));
  if (!ptime)
    return ERR;

  for (i = num_min, n = 0; i <= num_max; i = i + incr)
  {
    if (average_sorting_time(method, n_perms, i, ptime + n) == ERR)
    {
      return ERR;
      free(ptime);
    }
    n++;
  }

  if (save_time_table(file, ptime, n) == ERR)
  {
    return ERR;
    free(ptime);
  }

  free(ptime);

  return OK;
}

/***************************************************/
/* Function: save_time_table Date:                 */
/*                                                 */
/* Your documentation                              */
/***************************************************/
short save_time_table(char *file, PTIME_AA ptime, int n_times)
{
  int i;
  FILE *pf = NULL;

  if (!file || n_times <= 0)
    return ERR;

  pf = fopen(file, "w");
  if (!pf)
    return ERR;

  for (i = 0; i < n_times; i++)
  {
    if (fprintf(pf, "%d %f %f %d %d\n", ptime->N, ptime->time, ptime->average_ob, ptime->min_ob, ptime->max_ob) == ERR)
    {
      fclose(pf);
      return ERR;
    }
    ptime++;
  }

  if (fclose(pf) == ERR)
    return ERR;

  return OK;
}

short average_search_time(pfunc_search method, pfunc_key_generator generator, char order, int N, int n_times, PTIME_AA ptime)
{
  int i, minOB, maxOB, OB, ret = 0;
  struct timeval start_t, end_t;
  long seconds, useconds;
  
  double total_t = 0.0, averageOB = 0.0;
  PDICT dict = NULL;
  int *perm = NULL, *aux = NULL, ppos = 0;
  

  if (!method || !generator || (order != SORTED && order != NOT_SORTED) || N < 0 || !ptime || n_times*N <= 0)
  {
    return ERR;
  }

  minOB = INT_MAX;
  maxOB = INT_MIN;

  dict = init_dictionary(N, order);
  if (!dict)
  {
    return ERR;
  }

  if (order == NOT_SORTED)
  {
    perm = generate_perm(N);
  }
  else
  {
    perm = generate_sorted_perm(N);
  }

  if (!perm)
  {
    free_dictionary(dict);
    return ERR;
  }
  
  if (massive_insertion_dictionary(dict,perm,N) == ERR)
  {
    free_dictionary(dict);
    free(perm);
    return ERR;
  }

  aux = (int*)malloc((N*n_times)*sizeof(int));
  if (!aux)
  {
    free_dictionary(dict);
    free(perm);
    return ERR;
  }

  generator(aux, N*n_times, N);

  for (i = 0; i < n_times*N; i++)
  {
    ret = gettimeofday(&start_t, NULL);
    if (ret == ERR)
    {
      free_dictionary(dict);
      free(perm);
      free(aux);
      return ERR;
    }
    
    OB = method(dict->table, 0, N - 1, aux[i], &ppos);
    if (OB == ERR)
    {
      free_dictionary(dict);
      free(perm);
      free(aux);
      return ERR;
    }
    
    ret = gettimeofday(&end_t, NULL);
    if (ret == ERR)
    {
      free_dictionary(dict);
      free(perm);
      free(aux);
      return ERR;
    }

    seconds  = end_t.tv_sec  - start_t.tv_sec;
    useconds = end_t.tv_usec - start_t.tv_usec;
    
    averageOB += OB;
    if (OB < minOB)
    {
      minOB = OB;
    }
    if (OB > maxOB)
    {
      maxOB = OB;
    }

    total_t += (double)((seconds) * 1000 + useconds/1000.0);
  }

  total_t = total_t / (n_times*N);
  averageOB = averageOB / (n_times*N);

  ptime->N = N;
  ptime->n_elems = n_times*N;
  ptime->min_ob = minOB;
  ptime->max_ob = maxOB;
  ptime->average_ob = averageOB;
  ptime->time = total_t;

  free_dictionary(dict);
  free(perm);
  free(aux);

  return OK;
  
}

/***************************************************/
/* Function: generate_sorting_times Date:          */
/*                                                 */
/* Your documentation                              */
/***************************************************/
short generate_search_times(pfunc_search method, pfunc_key_generator generator, int order, char* file, int num_min, int num_max, int incr, int n_times)
{
  int i, n = 0;
  PTIME_AA ptime;

  if (!method || !generator || (order != SORTED && order != NOT_SORTED) || !file || num_max < num_min)
  {
    return ERR;
  }
  if (num_max == num_min)
  {
    n = 1;
  }
  else
  {
    n = ((num_max - num_min) / incr) + ((num_max - num_min) % incr);
  }
  ptime = (TIME_AA *)malloc(n * sizeof(TIME_AA));
  if (!ptime)
    return ERR;

  for (i = num_min, n = 0; i < num_max; i = i + incr)
  {
    printf("Iteration %d/%d\n", i, num_max);
    if (average_search_time(method, generator, order, i, n_times, ptime + n) == ERR)
    {
      free(ptime);
      return ERR;
    }
    n++;
  }

  if (save_time_table(file, ptime, n) == ERR)
  {
    free(ptime);
    return ERR;
  }

  free(ptime);

  return OK;
  
}
