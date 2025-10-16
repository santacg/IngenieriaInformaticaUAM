#include <immintrin.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#define ARRAY_SIZE 2048
#define NUMBER_OF_TRIALS 100000

/*
 * Statically allocate our arrays.  Compilers can
 * align them correctly.
 */
static double a[ARRAY_SIZE], b[ARRAY_SIZE], c;

int main(int argc, char *argv[]) {
  int i, t;

  int n_trials = atoi(argv[1]);

  __m256d vb = {0.0, 1.0, 2.0, 3.0};
  __m256d va = {1.0, 2.0, 3.0, 4.0};

  __m256d constant = {4.0, 4.0, 4.0, 4.0};
  /* Populate A and B arrays */
  for (i = 0; i < ARRAY_SIZE; i += 4) {
    _mm256_store_pd(&b[i], vb);
    _mm256_store_pd(&a[i], va);
    vb = _mm256_add_pd(vb, constant);
    va = _mm256_add_pd(va, constant);
  }

  __m256d mm = {1.0001, 1.0001, 1.0001, 1.0001};
  __m256d sum = {0.0, 0.0, 0.0, 0.0}; // to hold partial sums

  struct timeval start_time, end_time;

  /* Perform an operation a number of times */
  gettimeofday(&start_time, NULL);
  for (t = 0; t < n_trials; t++) {
    for (i = 0; i < ARRAY_SIZE; i += 4) {
      // Load arrays
      __m256d va = _mm256_load_pd(&a[i]);
      __m256d vb = _mm256_load_pd(&b[i]);
      // Compute m*a+b
      __m256d tmp = _mm256_fmadd_pd(mm, va, vb);
      // Accumulate results
      sum = _mm256_add_pd(tmp, sum);
    }
  }
  // Get sum[2], sum[3]
  __m128d xmm = _mm256_extractf128_pd(sum, 1);
  // Extend to 256 bits: sum[2], sum[3], 0, 0
  __m256d ymm = _mm256_castpd128_pd256(xmm);
  // Perform sum[0]+sum[1], sum[2]+sum[3], sum[2]+sum[3], 0+0
  sum = _mm256_hadd_pd(sum, ymm);
  // Perform sum[0]+sum[1]+sum[2]+sum[3]…
  sum = _mm256_hadd_pd(sum, sum);
  c = sum[0];

  gettimeofday(&end_time, NULL);
  double elapsed_time = (end_time.tv_sec - start_time.tv_sec) +
                        (end_time.tv_usec - start_time.tv_usec) / 1e6;

  printf("t: %d \n", t);
  printf("elapsed_time: %lf \n", elapsed_time);

  printf("result: %lf\n", c);
  return 0;
}
