#include "../Utils/utils.h"
#include <stdint.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>

#define LFSR_FEED 0xB400 // Feedback para LFSR
#define LFSR_SEED 0xACE1 // Semilla inicial para LFSR

void help(char **argv) {
  fprintf(stderr, "Usage: %s {-C|-D} -i infile -o outfile\n", argv[0]);
}

uint8_t stream() {
  uint8_t key_stream = 0;
  uint16_t lfsr = LFSR_FEED;

  for (int i = 0; i < 8; i++) {
    // XOR entre ciertas posiciones para retroalimentación del LFSR
    uint16_t bit = ((lfsr >> 0) ^ (lfsr >> 2) ^ (lfsr >> 3) ^ (lfsr >> 5)) & 1;
    lfsr = (lfsr >> 1) |
           (bit << 15); // Desplaza LFSR a la derecha e inserta el nuevo bit
    key_stream = (key_stream << 1) | bit; // Construye el flujo de claves
  }

  return key_stream;
}

void cifrado_stream(FILE *in, FILE *out) {
  char c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      c = c - 'A';
      uint8_t key_stream = stream();
      uint8_t byte_cifrado = (c ^ key_stream) + 'A';
      fputc(byte_cifrado, out);
    }
  }
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  int mode = ERR;

  int opt;
  if (argc < 6) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDk:n:i:o:")) != -1) {
    switch (opt) {
    case 'C':
      if (mode == MODE_DECRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = MODE_ENCRYPT;
      break;
    case 'D':
      if (mode == MODE_ENCRYPT) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = MODE_DECRYPT;
      break;
    case 'i':
      file_in = optarg;
      break;
    case 'o':
      file_out = optarg;
      break;
    default:
      help(argv);
      return ERR;
    }
  }

  if (mode == ERR || file_in == NULL || file_out == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in, *out;

  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL) {
      return ERR;
    }
  } else {
    in = stdin;
  }

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fclose(in);
      return ERR;
    }
  } else {
    out = stdout;
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  cifrado_stream(in, out);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
