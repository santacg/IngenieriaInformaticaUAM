#include <stdio.h>

void help() { printf("Use: preprocesado file_in file_out\n"); }

int main(int argc, char *argv[]) {
  if (argc < 3) {
    printf("Error: Input and output files needed\n");
    help();
    return 1;
  }

  char *file_in = argv[1];
  char *file_out = argv[2];

  if (file_in == NULL || file_out == NULL) {
    printf("Error: Input and output files needed\n");
    help();
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

  char c;
  while ((c = fgetc(in)) != EOF) {
    if (c >= 'A' && c <= 'Z') {
      if (fputc(c, out) == EOF) {
        printf("Error: Fputc EOF\n");
        fclose(in);
        fclose(out);
        return 1;
      }
    } else if (c >= 'a' && c <= 'z') {
      c = c + ('A' - 'a');
      if (fputc(c, out) == EOF) {
        printf("Error: Fputc EOF\n");
        fclose(in);
        fclose(out);
        return 1;
      }
    }
  }

  fclose(in);
  fclose(out);

  return 0;
}
