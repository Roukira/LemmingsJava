#include <stdio.h>
#include <stdlib.h>
#include <stdint.h> 
#include <inttypes.h>
#include <math.h>
#include <time.h>
#include "GauBinaire.h"
#include "FonctionsDeBases.h"

int main (int argc, char **argv)
{	
	srand(time(NULL));
	unsigned int m = 3;
	unsigned int n = 3;
	if (argc == 2){
		m = (unsigned int)atoi(argv[1]);
		n = m;
	}
	else if (argc == 3){
		m = (unsigned int)atoi(argv[1]);
		n = (unsigned int)atoi(argv[2]);
	}
	int8_t *A = allocateMatrix(m,n);
	int8_t *v = allocateVector(m);
	int8_t *x = allocateVector(m);
	printf("\nMatrice de depart A aleatoire :\n");
	randomBinMatrix(A,m,n);
	printMatrix(A,m,n);
	printf("Vecteur aleatoire v :\n");
	randomBinVector(v,m);
	printVector(v,m);
	solveSystemGauss(x,A,v,m);
	
	printf("\n Le resultat x pour A*x=v est :\n");
	printVector(x,m);
	/*printf("\nElimination de Gauss\n==================\n");
	gaussianElimination(A,v,m);
	printMatrix(A,m,m);
	printVector(v,m);
	printf("\nRemonter des pivots\n==================\n");
	solveTriangularUpper(x,A,v,m);
	printMatrix(A,m,m);
	printf("Vecteur x tel que A*x = v\n");*/
	return 0;	
}
