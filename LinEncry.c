#include <stdio.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stdint.h>
#include <time.h>
#include "FonctionsDeBases.h"
#include "GauBinaire.h"

#define LIN(i,j,m,n) ((i)*(n)+(j))

void createZi(int8_t *Z, int8_t *L, int8_t *K, int8_t *F, unsigned int n){
	 unsigned int i,j;
	 int8_t *KLiTemp = allocateVector(n);
	 int8_t *KLi = allocateVector(n);
	 int8_t zi;
	 copyVector(KLi,K,n);
	 for(i = 0; i < n ; i++){
		zi = 0;	
		vectorMatrixProduct(KLiTemp,KLi,L,n,n);
		copyVector(KLi,KLiTemp,n);
		//printf("Voici le vecteur KLi :\n");
		//printVector(KLi,n);
		for(j = 0; j < n; j++){
   			zi ^= KLi[j] & F[j];
  		}
  		Z[i] = zi;
  		
	}
	free(KLiTemp);
	free(KLi);
	//printVector(Z,n);
}

void createSystem(int8_t* Sys, int8_t *F, int8_t* L, unsigned int n){
	unsigned int i, j, k;
	int8_t* Li = allocateMatrix(n,n);
	int8_t* L2 = allocateMatrix(n,n);
	//printf("L : \n");
	//printMatrix(L,n,n);
	identityMatrix(L2, n, n);
	for(i = 0; i < n; ++i){
		matrixMatrixProduct(Li, L2, L, n, n, n);
		printf("Voici L^%u : \n",i+1);
		//printMatrix(Li,n,n);
		for(j = 0; j < n; ++j){
			Sys[LIN(i,j,n,n)] = 0;
			for(k = 0; k < n; ++k){
				Sys[LIN(i,j,n,n)] ^= F[k] & Li[LIN(j,k,n,n)];
			}
		}
		copyMatrix(L2, Li, n, n);
		
	
	}
	//printf("debug1\n");
	freeMatrix(L2);
	//printf("debug2\n");

	freeMatrix(Li);

}
int decrypt(int8_t *res, int8_t *L, int8_t *Z, int8_t *F, unsigned int n){
	int8_t* Sys = allocateMatrix(n,n);	
	createSystem(Sys, F, L, n);
	printf("Voici le systeme lineaire : \n");
	printMatrix(Sys,n,n);
	printf("Qui foit un vector X doit valoir : \n");
	printVector(Z,n);
	int boolean = solveSystemGauss(res, Sys, Z, n);
	freeMatrix(Sys);
	return boolean;
}

