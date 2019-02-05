/* File: INSERTION_SORT
 * in page 10
 */
#include <stdio.h>
#include <time.h>

int main(void)
{
	int num_list[20] = {1, 7, 9, 7, 9, 2, 4, 6, 8, 10,
			    23,12,34,55,11,12,12,42,99,22,};
	
	
	clock_t start = clock();
	
	for (int j = 2; j <= 20; j++)
	{
		int key = num_list[j];
		int i = j - 1;

		while (i > 0 && num_list[i] > key)
		{
			num_list[i+1] = num_list[i];
			i = i -1;
		}
		num_list[i + 1] = key;     

	}

	for (int m = 0;m < 20; m++)
		printf("%d ", num_list[m]);

	putchar('\n');

	clock_t end = clock();
	double time = (double)(end - start)/ CLOCKS_PER_SEC;

	printf("%f", time); 


	return 0;
}
