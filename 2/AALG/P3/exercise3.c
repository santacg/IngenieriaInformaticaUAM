/***********************************************************/
/* Program: exercise2 Date:                             */
/* Authors:                                                */
/*                                                         */
/* Program that writes in a file the average times         */
/* of the search algorithm                                 */
/*                                                         */
/* Input: Command line                                     */
/* -num_min: minimum number of elements in the table       */
/* -num_max: maximum number of elements in the table       */
/* -incr: increment                                       */
/* -fkeys: number of keys to search                        */
/* -numP: Introduce the number of permutations to average  */
/* -outputFile: Name of the output file                    */
/*                                                         */
/* Output: 0 if there was an error                         */
/*        -1 otherwise                                     */
/***********************************************************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "permutations.h"
#include "search.h"
#include "times.h"

int main(int argc, char** argv)
{
  char name1[256], name3[256], name6[256], name7[256], name8[256], name9[256], name10[256], name11[256], name12[256];
  short ret;
 
  srand(time(NULL));

  printf("Practice number 3, section 3\n");
  printf("Done by: Your names\n");
  printf("Group: Your group\n");

  strcpy(name1,"lin_search.txt");
  strcpy(name3,"bin_search.txt");
  strcpy(name6,"lin_auto_search.txt");
  strcpy(name7,"lin_auto_search_1.txt");
  strcpy(name8,"lin_auto_search_100.txt");
  strcpy(name9,"lin_auto_search_10000.txt");
  strcpy(name10,"bin_search_1.txt");
  strcpy(name11,"bin_search_100.txt");
  strcpy(name12,"bin_search_10000.txt");

  /* calculamos los tiempos */
  ret = generate_search_times(lin_search, uniform_key_generator, NOT_SORTED, 
                                name1, 10000, 150000, 2500, 1);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(bin_search, uniform_key_generator, SORTED, 
                                name3, 10000, 150000, 2500, 1);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(lin_auto_search, potential_key_generator, NOT_SORTED, 
                                name6, 10000, 150000, 2500, 1);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(lin_auto_search, potential_key_generator, NOT_SORTED, 
                                name7, 1000, 10000, 200, 1);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(lin_auto_search, potential_key_generator, NOT_SORTED, 
                                name8, 1000, 10000, 200, 100);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(bin_search, uniform_key_generator, SORTED, 
                                name10, 1000, 10000, 200, 1);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(bin_search, uniform_key_generator, SORTED, 
                                name11, 1000, 10000, 200, 100);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(lin_auto_search, potential_key_generator, NOT_SORTED, 
                                name9, 1000, 10000, 200, 10000);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  ret = generate_search_times(bin_search, uniform_key_generator, SORTED, 
                                name12, 1000, 10000, 200, 10000);
  if (ret == ERR) { 
    printf("Error in function generate_search_times\n");
    exit(-1);
  }

  printf("Correct output \n");

  return 0;
}

