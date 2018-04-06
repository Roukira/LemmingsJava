#include <stdio.h>
#include <stdlib.h>
#include <stdint.h> 
#include <inttypes.h>
#include <math.h>
#include <time.h>
#include "FonctionsDeBases.h"

#define LIN(i,j,m,n) ((i)*(n) + (j))

void changementLigneGauss(int8_t *A, unsigned int ligne1, unsigned int ligne2, unsigned int n){
	unsigned int j;
	int8_t temp;
	for (j = 0; j < n; j ++){
		temp = A[LIN(ligne1,j,n,n)];
		A[LIN(ligne1,j,n,n)] = A[LIN(ligne2,j,n,n)];
		A[LIN(ligne2,j,n,n)] = temp;	
	}	
}

void gaussianElimination( int8_t *A, int8_t *b, unsigned int n){
/*Cette fonction prend en argument une matrice A, un vecteur b, la taille n.
Elle modifie A et b en appliquant l'elimination de Gauss sur A et b.*/
	unsigned int i,j,r;
	
	for (i = 0; i < n; i++){
		int8_t temp=0;
		unsigned int cpt=i+1;
		int changementAvecDerniereLigne = 0;  //on regarde si on fait un changement avec la derniere ligne et dans ce cas on ne fera pas de soustractions sur les lignes en dessous
		//ON regarde si on a besoin de changer de ligne, si oui on le fait
		if (A[LIN(i,i,n,n)] == 0){
			//changementLigneOblige = 1;
			while(cpt<n && A[LIN(cpt,i,n,n)] == 0){
				cpt++;
			}
			if (cpt < n){
				changementLigneGauss(A,i,cpt,n);//changement matrice 
				printf("changement entre la ligne %u et %u\n",i,cpt);
				temp = b[i];     //changement de ligne pour vecteur
				b[i] = b[cpt];
				b[cpt] = temp;				
				if(cpt == n-1){
					changementAvecDerniereLigne = 1;
				}
			}
		}
		//Maintenant on a soit changer une ligne, soit on pas 
		//Si besoin on fait la methode du pivot
		if (changementAvecDerniereLigne == 0){
			for (j = i+1; j < n; j++){
				if( A[LIN(j,i,n,n)]!= 0){   //on entre que si en dessous du pivot c'est un 1
					printf("addition de la ligne %u sur la ligne %u\n",i,j);
					b[j] = b[j] ^ b[i];
					for (r = i; r < n; r++){
						A[LIN(j,r,n,n)] = A[LIN(j,r,n,n)] ^ A[LIN(i,r,n,n)];
					}	
				}
			}
		}
	}
}

void solveTriangularUpper(int8_t *x, int8_t *A, int8_t *b, unsigned int n){
/*Cette fonction prend en argument une matrice A, deux vecteurs x et b, la taille n.
On suppose que l'elimination de Gauss est deja faite.
Elle resout AX = B et modifie X.*/
	int j;
	int i, r;
	//int8_t sum;
	copyVector( x,b,n);
	/*for (i = n-1; i >= 0; i--){
		sum = 0;
		for (j = i+1; j < n; j++){
			sum += A[LIN(i,j,n,n)]*x[j];
		}	
		x[i] = (b[i]-(sum))/A[LIN(i,i,n,n)];
	}*/
	for (i = n-1; i >= 0; i--){
		if(A[LIN(i,i,n,n)]==1){  //On verifie qu on ai besoint daddictioner la ligne
			for (r = i-1; r >= 0; r--){
				if(A[LIN(r,i,n,n)]==1){
					x[r] = x[r] ^ x[i];
					for (j = i; j < n; j++){
						A[LIN(r,j,n,n)] = A[LIN(r,j,n,n)] ^ A[LIN(i,j,n,n)];
					}
				}			
			}
		}
	}
}

int solveSystemGauss( int8_t *x, int8_t *A, int8_t *b, unsigned int n){
/*Cette fonction prend en argument une matrice A, deux vecteurs x et b, la taille n.
Elle resout le systeme AX=B et modifie X.*/
	int8_t *Abyss = allocateMatrix(n,n);
	int8_t *bis = allocateVector(n);
	int8_t *resSuppose = allocateVector(n);
	
	copyMatrix( Abyss,A,n,n);
	copyVector( bis,b,n);
	gaussianElimination(Abyss,bis,n);
	printf("Matrice triangularise\n");
	printMatrix(Abyss,n,n);
	if(determinantMatrixTriangulaire(Abyss,n)==0){
		printf("Matrice non inversible, pas de resultat.\n");
		freeMatrix(Abyss);
		freeVector(bis);
		free(resSuppose);
		return 0;
	}
	solveTriangularUpper(x,Abyss,bis,n);
	printf("Vecteur x : \n");
	printVector(x,n);
	
	matrixVectorProduct(resSuppose,A,x,n,n );
	
	if (compareVector(b,resSuppose,n)){
		printf("On retrouve bien A*x = b!\n");
	}
	else{
		printf("Determinant non nul, mais A*x !=b : ERREUR de code!\n");
		freeMatrix(Abyss);
		freeVector(bis);
		free(resSuppose);
		return 0;
	}
	
	freeMatrix(Abyss);
	freeVector(bis);
	free(resSuppose);
	return 1;
}




