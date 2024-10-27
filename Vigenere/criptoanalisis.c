#include "../Utils/utils.h"
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define N_LETRAS 26
#define IC_ENG 0.065

const double frecuencias_eng[N_LETRAS] = {
    0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061, 0.070,
    0.002, 0.008, 0.040, 0.024, 0.067, 0.075, 0.019, 0.001, 0.060,
    0.063, 0.091, 0.028, 0.010, 0.023, 0.001, 0.020, 0.001};

void help(char **argv) {
  fprintf(stderr, "Usage: %s -l longtitud de clave -i filein\n", argv[0]);
}

int main(int argc, char *argv[]) {
  char *file_in = NULL;
  int longitud_clave = 0;

  int opt;
  if (argc < 3) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "l:i:")) != -1) {
    switch (opt) {
    case 'l':
      longitud_clave = atoi(optarg);
      if (longitud_clave <= 0) {
        fprintf(stderr, "Error: Valor de longitud de clave incorrecto\n");
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

  if (file_in == NULL || longitud_clave == 0) {
    fprintf(stderr, "Error: Faltan argumentos requeridos.\n");
    help(argv);
    return ERR;
  }

  FILE *in = fopen(file_in, "r");
  if (in == NULL) {
    fprintf(stderr, "Error: No se ha conseguido abrir el archivo de entrada\n");
    return ERR;
  }

  fseek(in, 0, SEEK_END);
  long file_size = ftell(in);
  fseek(in, 0, SEEK_SET);

  long subsecuencias_size = file_size / longitud_clave;

  // Reservar memoria para las subsecuencias
  char **subsecuencias = (char **)malloc(longitud_clave * sizeof(char *));
  if (subsecuencias == NULL) {
    fclose(in);
    return ERR;
  }

  for (int i = 0; i < longitud_clave; i++) {
    subsecuencias[i] = (char *)malloc(subsecuencias_size * sizeof(char));
    if (subsecuencias[i] == NULL) {
      for (int j = 0; j < i; j++) {
        free(subsecuencias[j]);
      }
      free(subsecuencias);
      fclose(in);
      return ERR;
    }
  }

  // Leer el fichero de entrada y generar las subsecuencias
  int pos = 0;
  char c;
  while ((c = fgetc(in)) != EOF && pos < file_size) {
    int idx_subseq = pos % longitud_clave;
    int idx_char = pos / longitud_clave;
    if (idx_char < subsecuencias_size) {
      subsecuencias[idx_subseq][idx_char] = c;
    }
    pos++;
  }

  fclose(in);

  int letras_k[longitud_clave];
  printf("Valores de M(k_i) y mejores k para cada subsecuencia:\n");
  for (int i = 0; i < longitud_clave; i++) {
    int frecuencias[N_LETRAS] = {0};

    // Contar frecuencias absolutas de cada letra
    for (int j = 0; j < subsecuencias_size; j++) {
      char letra = subsecuencias[i][j];
      if (letra >= 'A' && letra <= 'Z') {
        frecuencias[letra - 'A']++;
      }
    }

    // Calcular frecuencias relativas
    double frecuencias_relativas[N_LETRAS];
    for (int j = 0; j < N_LETRAS; j++) {
      frecuencias_relativas[j] = (double)frecuencias[j] / subsecuencias_size;
    }

    // Calcular M(k_i) y encontrar el k óptimo que maximiza M(k_i)
    int mejor_k = 0;
    double mejor_m_ki = 0.0;
    double diferencia_minima = fabs(IC_ENG - mejor_m_ki);

    printf("Subsecuencia %d:\n", i + 1);
    for (int k = 0; k < N_LETRAS; k++) {
      double m_ki = 0.0;
      for (int j = 0; j < N_LETRAS; j++) {
        int desplazado_idx = (j + k) % N_LETRAS;
        m_ki += frecuencias_eng[j] * frecuencias_relativas[desplazado_idx];
      }
      printf("M(k = %d): %f\n", k, m_ki);

      // Verificar si es el valor más cercano al índice de coincidencia
      double diferencia_actual = fabs(IC_ENG - m_ki);
      if (diferencia_actual < diferencia_minima) {
        diferencia_minima = diferencia_actual;
        mejor_k = k;
        mejor_m_ki = m_ki;
      }
    }

    letras_k[i] = mejor_k;
    printf("Mejor k para subsecuencia %d es %d con M(k) = %f\n\n", i + 1,
           mejor_k, mejor_m_ki);
  }

  printf("La clave criptoanalizada es: \n");
  for (int i = 0; i < longitud_clave; i++) {
    printf("%c", letras_k[i] + 'A');
  }
  printf("\n");

  for (int i = 0; i < longitud_clave; i++) {
    free(subsecuencias[i]);
  }
  free(subsecuencias);

  return 0;
}
