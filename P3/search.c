/**
 *
 * Description: Implementation of functions for search
 *
 * File: search.c
 * Author: Carlos Aguirre and Javier Sanz-Cruzado
 * Version: 1.0
 * Date: 14-11-2016
 *
 */

#include "search.h"

#include <stdlib.h>
#include <math.h>

/**
 *  Key generation functions
 *
 *  Description: Receives the number of keys to generate in the n_keys
 *               parameter. The generated keys go from 1 to max. The
 * 				 keys are returned in the keys parameter which must be
 *				 allocated externally to the function.
 */

/**
 *  Function: uniform_key_generator
 *               This function generates all keys from 1 to max in a sequential
 *               manner. If n_keys == max, each key will just be generated once.
 */
void uniform_key_generator(int *keys, int n_keys, int max)
{
    int i;

    for (i = 0; i < n_keys; i++)
        keys[i] = 1 + (i % max);

    return;
}

/**
 *  Function: potential_key_generator
 *               This function generates keys following an approximately
 *               potential distribution. The smaller values are much more
 *               likely than the bigger ones. Value 1 has a 50%
 *               probability, value 2 a 17%, value 3 the 9%, etc.
 */
void potential_key_generator(int *keys, int n_keys, int max)
{
    int i;

    for (i = 0; i < n_keys; i++)
    {
        keys[i] = .5 + max / (1 + max * ((double)rand() / (RAND_MAX)));
    }

    return;
}

PDICT init_dictionary(int size, char order)
{
    PDICT dict = NULL;
    int *table = NULL;
    int i;
    if (size < 0 || (order != SORTED && order != NOT_SORTED))
    {
        return NULL;
    }

    dict = (PDICT)malloc(sizeof(DICT));
    if (!dict)
    {
        return NULL;
    }

    table = (int *)malloc(size * sizeof(int));

    if (!table)
    {
        free_dictionary(dict);
        return NULL;
    }

    for (i = 0; i < size; i++)
    {
        table[i] = 0;
    }

    dict->size = size;
    dict->n_data = 0;
    dict->order = order;
    dict->table = table;

    return dict;
}

void free_dictionary(PDICT pdict)
{
    free(pdict->table);
    free(pdict);
}

int insert_dictionary(PDICT dict, int key)
{
    int i, aux = 0, A = 0;
    if (!dict)
    {
        return ERR;
    }

    if (dict->n_data == dict->size)
    {
        dict->size++;
        dict->table = (int *)realloc(dict->table, dict->size * (sizeof(int)));
        if (dict->table == NULL)
        {
            dict->size--;
            return ERR;
        }
    }

    dict->table[dict->n_data] = key;
    dict->n_data++;

    if (dict->order == SORTED)
    {
        A = dict->table[dict->n_data - 1];
        for (i = dict->n_data - 2; i >= 0 && dict->table[i] >= A; i--)
        {
            aux = dict->table[i + 1];
            dict->table[i + 1] = dict->table[i];
            dict->table[i] = aux;
        }
    }

    return OK;
}

int massive_insertion_dictionary(PDICT pdict, int *keys, int n_keys)
{
    int i;
    if (!pdict || !keys)
    {
        return ERR;
    }

    for (i = 0; i < n_keys; i++)
    {
        if (insert_dictionary(pdict, keys[i]) == ERR)
        {
            return ERR;
        }
    }

    return OK;
}

int search_dictionary(PDICT pdict, int key, int *ppos, pfunc_search method)
{
    int OB = 0, F = 0, L = pdict->n_data - 1;
    if (!pdict || !ppos || !method)
    {
        return ERR;
    }
    OB = method(pdict->table, F, L, key, ppos);
    if (OB == ERR)
    {
        return ERR;
    }
    return OB;
}

int bin_search(int *table, int F, int L, int key, int *ppos)
{
    int mid;
    int OB = 0;

    if (!table || !ppos || F > L)
    {
        return ERR;
    }
    
    while (F <= L)
    {
        mid = F + (L - F) / 2;
        OB++;

        if (table[mid] == key)
        {
            *ppos = mid;
            OB++;
            return OB;
        }

        if (table[mid] < key)
        {
            F = mid + 1;
        }
        else
        {
            L = mid - 1;
        }
        OB += 2;
    }

    return NOT_FOUND;
}

int lin_search(int *table, int F, int L, int key, int *ppos)
{
    int i, OB = 0;

    if (!table || !ppos || F > L)
    {
        return ERR;
    }
    

    for (i = F; i <= L; i++)
    {
        OB++;
        if (table[i] == key)
        {
            *ppos = i;
            return OB;
        }
    }

    return NOT_FOUND;
}

int lin_auto_search(int *table, int F, int L, int key, int *ppos)
{
    int i, OB = 0, aux = 0;

    if (!table || !ppos || F > L)
    {
        return ERR;
    }

    for (i = F; i <= L; i++)
    {
        OB++;
        if (table[i] == key)
        {
            *ppos = i;
            if (i != F)
            {
                aux = table[i - 1];
                table[i - 1] = table[i];
                table[i] = aux;
            }
            return OB;
        }
    }

    return NOT_FOUND;
}
