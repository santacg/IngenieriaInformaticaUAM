#include "../Utils/utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

void help(char **argv) {
  fprintf(stderr, "Usage: %s {-C|-D} -k cadena -i infile -o outfile\n",
          argv[0]);
}

int vigenere(FILE *in, FILE *out, char *key, int mode) {

  if (in == NULL || out == NULL || key == NULL)
    return ERR;

  int n = strlen(key);
  if (n <= 0) {
    return ERR;
  }

  int i = 0;
  int k[n];
  while (key[i] != '\0') {
    if (key[i] >= 'A' && key[i] <= 'Z') {
      k[i] = key[i] - 'A';
    }
    i++;
  }

  i = 0;
  char c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      if (i == n)
        i = 0;

      if (mode == MODE_ENCRYPT) {
        c = ((k[i] + (c - 'A')) % 26) + 'A';
      } else {
        c = (((c - 'A' - k[i] + 26) % 26) + 'A');
      }
      fputc(c, out);
      i++;
    }
  }

  return 1;
}

int main(int argc, char **argv) {
  char *file_in = NULL, *file_out = NULL;
  char *key = NULL;
  int mode = ERR;

  int opt;
  if (argc < 5) {
    help(argv);
    return ERR;
  }

  while ((opt = getopt(argc, argv, "CDk:i:o:")) != -1) {
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
    case 'k':
      key = optarg;
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

  if (mode == ERR || key == NULL || file_in == NULL || file_out == NULL) {
    fprintf(stderr, "Error: Missing required arguments.\n");
    help(argv);
    return ERR;
  }

  FILE *in, *out;

  if (file_in != NULL) {
    in = fopen(file_in, "r");
    if (in == NULL)
      return ERR;
  }

  if (file_out != NULL) {
    out = fopen(file_out, "w");
    if (out == NULL) {
      fclose(in);
      return ERR;
    }
  }

  struct timespec start_time, end_time;

  clock_gettime(CLOCK_MONOTONIC, &start_time);
  vigenere(in, out, key, mode);
  clock_gettime(CLOCK_MONOTONIC, &end_time);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

  printf("time: %lf \n", elapsed_time);

  fclose(in);
  fclose(out);
  return OK;
}
