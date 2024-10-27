#include "../Utils/utils.h"
#include <glib.h>
#include <gmp.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l ngramas -i filein\n", argv[0]);
}

int *factorizar(int num, int *n_factores) {
  int *factores = malloc(sizeof(int) * num);
  if (factores == NULL)
    return NULL;

  *n_factores = 0;
  for (int i = 2; i <= num; i++) {
    while (num % i == 0) {
      factores[*n_factores] = i;
      (*n_factores)++;
      num /= i;
    }
  }

  return factores;
}

void add_factores(GHashTable *hash_factores, int *factores, int num_factores) {
  // Iteramos sobre cada factor en el array de factores
  for (int i = 0; i < num_factores; i++) {
    gpointer clave =
        GINT_TO_POINTER(factores[i]); // Convertimos el entero en puntero
    // Buscamos el factor en el hash; si no existe, retorna NULL
    int *count = (int *)g_hash_table_lookup(hash_factores, clave);
    if (count == NULL) {
      // Si el factor no está en el hash, lo añadimos con un 1
      count = g_new(int, 1);
      *count = 1;
      g_hash_table_insert(hash_factores, clave, count);
    } else {
      // Si el factor está en el hash, incrementamos la cuenta
      (*count)++;
    }
  }
}

void add_ngrama(GHashTable *table, char *ngrama, int posicion) {
  // Obtemos la lista si ya existe el ngrama
  // Añandimos la posicion a la lista
  GSList *list_pos = g_hash_table_lookup(table, ngrama);

  // Agregamos la posicion a la lista del ngrama correspondiente
  // Si la lista no existe se crea con g_slist_append
  list_pos = g_slist_append(list_pos, GINT_TO_POINTER(posicion + 1));
  // Si el n-grama es nuevo lo insertarmos en la tabla hash
  if (g_hash_table_lookup(table, ngrama) == NULL) {
    g_hash_table_insert(table, g_strdup(ngrama), list_pos);
  }
}

int kasiski(FILE *in, int n_grama) {

  if (in == NULL || n_grama <= 0) {
    return ERR;
  }

  // Empleamos un hash con listas enlazadas simples de la biblioteca GLIB para
  // almacenar los ngramas
  GHashTable *hash_ngramas = g_hash_table_new_full(
      g_str_hash, g_str_equal, g_free, (GDestroyNotify)g_slist_free);

  // Se lee el archivo con fread para obtener todos los ngramas posibles del
  // texto
  char buffer[n_grama + 1]; // +1 para el \0
  while (fread(buffer, sizeof(char), n_grama, in) == (size_t)(n_grama)) {
    // Elimanamos los saltos de línea del buffer
    int j = 0;
    for (int i = 0; i < n_grama; i++) {
      if (buffer[i] != '\n') {
        buffer[j++] = buffer[i];
      }
    }
    buffer[j] = '\0';

    // Verificamos si el n-grama resultante es del tamaño deseado
    if (j == n_grama) {
      // Añadimos el n_grama al hash con su posicion
      int pos = ftell(in) - n_grama;
      add_ngrama(hash_ngramas, buffer, pos);
    }

    // Movemos el puntero del archivo n_grama - 1 posiciones hacia atrás
    fseek(in, -(n_grama - 1), SEEK_CUR);
  }
  // Imprimir todos los n-gramas y sus posiciones
  // GHashTableIter iter;
  // gpointer key, value;
  // g_hash_table_iter_init(&iter, hash_ngramas);
  //
  // printf("N-gramas y sus posiciones:\n");
  // while (g_hash_table_iter_next(&iter, &key, &value)) {
  //   char *ngram = (char *)key;
  //   GSList *positions = (GSList *)value;
  //
  //   printf("N-grama: %s, posiciones: ", ngram);
  //   for (GSList *pos = positions; pos != NULL; pos = pos->next) {
  //     printf("%d ", GPOINTER_TO_INT(pos->data));
  //   }
  //   printf("\n");
  // }
  //

  // Buscamos las listas que tengan mas de una distancia
  GHashTableIter iter;
  gpointer clave, valor;
  g_hash_table_iter_init(&iter, hash_ngramas);
  // Inicializamos un hash para almacenar los factores de las distancias
  GHashTable *hash_factores =
      g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, g_free);
  while (g_hash_table_iter_next(&iter, &clave, &valor)) {
    GSList *list_pos = (GSList *)valor;

    // Para las listas que tengan mas de una distancia claculamos la distancia
    // entre sus posiciones contando desde la primera posicion
    if (g_slist_length(list_pos) > 1) {
      int first_pos = GPOINTER_TO_INT(list_pos->data);
      for (GSList *list_ele = list_pos->next; list_ele != NULL;
           list_ele = list_ele->next) {
        int current_pos = GPOINTER_TO_INT(list_ele->data);
        int distancia = current_pos - first_pos;
        // Se factoriza la distancia y se añanden los factores al hash de
        // factores
        int n_factores;
        int *factores = factorizar(distancia, &n_factores);

        if (factores == NULL) {
          free(factores);
          g_hash_table_destroy(hash_ngramas);
          g_hash_table_destroy(hash_factores);
          return ERR;
        }

        add_factores(hash_factores, factores, n_factores);
        free(factores);
      }
    }
  }

  printf("Factores y numero de apariciones:\n");
  g_hash_table_iter_init(&iter, hash_factores);
  int max_factor = -1;
  int factor = -1;
  while (g_hash_table_iter_next(&iter, &clave, &valor)) {
    factor = *(int *)valor;
    if (factor > max_factor)
      max_factor = factor;

    printf("Factor %d aparece %d veces\n", GPOINTER_TO_INT(clave), factor);
  }
  printf("\n");

  g_hash_table_destroy(hash_ngramas);
  g_hash_table_destroy(hash_factores);
  return factor;
}

int main(int argc, char *argv[]) {
  char *file_in = NULL;
  int n_grama;

  int opt;
  while ((opt = getopt(argc, argv, "l:i:")) != -1) {
    switch (opt) {
    case 'l':
      n_grama = atoi(optarg);
      if (n_grama <= 0) {
        fprintf(stderr, "Error: Valor de n-grama incorrecto\n");
        return ERR;
      }
      break;
    case 'i':
      file_in = optarg;
      break;
    default:
      help(argv);
      return ERR;
    }
  }

  if (file_in == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in = fopen(file_in, "r");
  if (in == NULL) {
    fprintf(stderr, "Error: No se ha conseguido abrir el archivo de entrada\n");
    return ERR;
  }

  printf("La longitud de clave más probable es %d\n", kasiski(in, n_grama));
  fclose(in);

  return 0;
}
