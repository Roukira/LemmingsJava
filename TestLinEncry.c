#include <stdio.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stdint.h>
#include "LinEncry.h"
#include "FonctionsDeBases.h"
#include "GauBinaire.h"

int main(int argc, char ** argv){
	unsigned int n = 20;
	if(argc == 2) n = (unsigned int)atoi(argv[1]);
	printf("debug\n");
	int8_t *K = allocateVector(n);
	printf("debug\n");
	int8_t *L = allocateMatrix(n,n);
	printf("debug\n");
	int8_t *F = allocateVector(n);
	printf("debug\n");
	int8_t *Z = allocateVector(n);
	printf("debug\n");
	int8_t *Kcalc = allocateVector(n);
	
	int8_t *debug = allocateVector(n);
	int8_t *debugMat = allocateMatrix(n,n);
	int8_t *debugMatTri = allocateMatrix(n,n);
	int8_t *debugRes = allocateVector(n);
	
	srand(time(NULL));
	do{
		printf("\n\n\n\nDEBUTTTTTTTTTTTTTTTTTTTTTTT  \n\n\n\n");
		randomBinVector(K,n);
		printf("Voici K : \n");
		printVector(K,n);
	
		randomBinVector(F,n);
		printf("Voici F : \n");
		printVector(F,n);
	
		randomBinMatrix(L,n,n);
		printf("Voici L : \n");
		printMatrix(L,n,n);
		
		createZi(Z,L,K,F,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		
		
		createSystem(debugMat,F,L,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		copyVector(debug,Z,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		copyMatrix(debugMatTri,debugMat,n,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		gaussianElimination(debugMatTri,debug,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		matrixVectorProduct(debugRes,debugMat,K,n,n);
		printf("Voici Z : \n");
		printVector(Z,n);
		printf("Z ?\n");
		printVector(debugRes,n);
		int i = 0;
		int boolean = 0;
		for(i=0;i<n;i++){
			if(debugRes[i]!=Z[i]){
				printf("ERREURRRRRRRRRRRRRRRR\n\n\nERREURRRRRRRRRRRRRRRR\n\n");
				boolean = 1;
				//exit(1);
			}
		}
		printf("comparaison : \n");
		printf("Sys : \n");
		printMatrix(debugMat,n,n);
		printf("Voici K : \n");
		printVector(K,n);
	
		printf("Voici F : \n");
		printVector(F,n);
	
		printf("Voici L : \n");
		printMatrix(L,n,n);
		
		printf("Voici Z : \n");
		printVector(Z,n);
		
		printf("\n\n\nFIN COMPARAISON \n\n\n");
		if(boolean) exit(1);
		
	
		printf("Voici Z : \n");
		printVector(Z,n);
	
		printf("debug\n");
		printf("debug\n");
	}while(!decrypt(Kcalc,L,Z,F,n));
	
	printf("Rappel de K : \n");
	printVector(K,n);
	
	printf("Voici K2 : \n");
	printVector(Kcalc,n);
	
	
	
	printf("Voici Z : \n");
	printVector(Z,n);
	createZi(Z,L,Kcalc,F,n);
	printf("Voici Z : \n");
	printVector(Z,n);
	
	
	
	int k = 0,sum = 0;
	for(k=0;k<n;k++){
		if (K[k]==Kcalc[k]) sum++;
	}
	printf("nombre qui coincide par rapport a %d : %d\n",n,sum);
	
	
	free(K);
	free(L);
	free(F);
	free(Z);
	free(Kcalc);
	return 0;
}
