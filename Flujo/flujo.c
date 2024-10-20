#include "../Utils/utils.h"
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

#define LFSR_FEED 0xB400
#define LFSR_SEED 0xACE1

uint8_t stream() {

  uint8_t key_stream = 0;
  uint16_t lfsr = LFSR_FEED;

  for (int i = 0; i < 8; i++) {
    uint16_t bit = ((lfsr >> 0) ^ (lfsr >> 2) ^ (lfsr >> 3) ^ (lfsr >> 5)) & 1;
    lfsr = (lfsr >> 1) | (bit << 15);

    key_stream = (key_stream << 1) | bit;
  }

  return key_stream;
}

void stream_cipher(FILE *in, FILE *out) {
  char c;
  while ((c = fgetc(in)) != EOF) {
    c = c - 'A';
    uint8_t keystream = stream();
    uint8_t cipher_byte = (c ^ keystream) + 'A';
    fputc(cipher_byte, out);
  }
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  int mode = ERR;
  FILE *in, *out;

  for (int i = 1; i < argc; i++) {
    if (strcmp("-C", argv[i]) == 0) {
      if (mode == 1) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 0; // Cifrar
    } else if (strcmp("-D", argv[i]) == 0) {
      if (mode == 0) {
        fprintf(stderr, "Error: Cannot set both modes at the same time\n");
        return ERR;
      }
      mode = 1; // Descifrar
    } else if (strcmp(argv[i], "-i") == 0) {
      if (i + 1 >= argc || strstr(argv[++i], ".txt") == NULL) {
        fprintf(stderr, "Error: Invalid input file\n");
        return ERR;
      }
      file_in = argv[i];
    } else if (strcmp(argv[i], "-o") == 0) {
      if (i + 1 >= argc || strstr(argv[++i], ".txt") == NULL) {
        fprintf(stderr, "Error: Invalid output file\n");
        return ERR;
      }
      file_out = argv[i];
    } else {
      fprintf(stderr, "Error: Unknown parameter %s\n", argv[i]);
      return ERR;
    }
  }

  if (mode == ERR) {
    fprintf(stderr, "Error: -C or -D must be specified\n");
    return ERR;
  }

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
  stream_cipher(in, out);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
