/* File: timing.c in page 38
 * Prints the running times for problem sizes that double,
 * using a single loop
 */
#include <stdio.h>
#include <time.h>

int main(void)
{
        int problem_size = 10000000; 
        int times, work;
        clock_t start, end;

        printf("%12s%16s\n", "Problem Size", "Seconds");
        
        for (times = 0; times < 5; times++)
        {
                start = clock(); 
                
                for (work = 0; work <= problem_size;work++)
                {                        
                        work += 1;
                        work -= 1;
                }
                end = clock(); 
		
		double elapsed = (double)(end - start) / CLOCKS_PER_SEC;
                printf("%12d%16.3f\n", problem_size, elapsed);

                problem_size *= 2;
        }

        return 0;
} 
