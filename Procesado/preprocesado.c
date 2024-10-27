#include <stdio.h>

void help(char **argv) { printf("Use: %s file_in file_out\n", argv[0]); }

void procesado(FILE *in, FILE *out) {
  char c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      fputc(c, out);
    } else if (c >= 'a' && c <= 'z') {
      c = c + ('A' - 'a');
      fputc(c, out);
    }
  }
}

int main(int argc, char *argv[]) {
  if (argc < 3) {
    printf("Error: Input and output files needed\n");
    help(argv);
    return 1;
  }

  char *file_in = argv[1];
  char *file_out = argv[2];

  if (file_in == NULL || file_out == NULL) {
    printf("Error: Input and output files needed\n");
    help(argv);
    return 1;
  }

  FILE *in = fopen(file_in, "r");
  if (in == NULL) {
    printf("Error: File in is null\n");
    return 1;
  }

  FILE *out = fopen(file_out, "w");
  if (out == NULL) {
    printf("Error: File out is null\n");
    fclose(in);
    return 1;
  }

  procesado(in, out);

  fclose(in);
  fclose(out);

  return 0;
}
