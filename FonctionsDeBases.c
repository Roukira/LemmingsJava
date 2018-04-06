#include <stdio.h>
#include <stdlib.h>
#include <stdint.h> 
#include <inttypes.h>
#include <math.h>

#define LIN(i,j,m,n) ((i)*(n) + (j))

int8_t *allocateVector( unsigned int m){
/*Cette fonction prend en argument un entier non signe m.
Elle retourne un nouveau vecteur alloue.*/
	int8_t *vect = (int8_t*)calloc(m,sizeof(int8_t)+m-1);
	if(!vect){
		printf("Erreur allocation vecteur\n");
		exit(1);
	}
	return vect;
}

int8_t *allocateMatrix( unsigned int m, unsigned int n){
/*Cette fonction prend en argument deux entiers non signes m et n.
Elle retourne une nouvelle matrice allouee.*/
	int8_t *mat = (int8_t*)calloc(m*n,sizeof(int8_t));
	if(!mat){
		printf("Erreur allocation matrice\n");
		exit(1);
	}
	return mat;
}

void freeVector( int8_t *v){
/*Cette fonction prend en argument un vecteur v.
Elle libere la memoire associee a v.*/
	free(v);
}

void freeMatrix( int8_t *A){
/*Cette fonction prend en argument une matrice A.
Elle libere la memoire associee a A.*/
	free(A);
}

void copyVector( int8_t *v, int8_t *u, unsigned int m){
/*Cette fonction prend en argument un vecteur v, un vecteur u, un entier non signe m.
Elle copie u dans v.*/
	unsigned int i;
	for( i=0;i<m;i++){
		//printf("\n avant | v[%u] : %d\n",i,v[i]);
		//printf("\n avant | u[%u] : %d\n",i,u[i]);
		v[i] = u[i];
		//printf("\n apres | v[%u] : %d\n",i,v[i]);
		//printf("\n apres | u[%u] : %d\n",i,u[i]);
	}
	
}

void copyMatrix(int8_t *B, int8_t *A, unsigned int m, unsigned int n){
/*Cette fonction prend en argument une matrice B, une matrice A, deux entiers non signes m et n.
Elle copie A dans B.*/
	unsigned int i,j;
	for (i=0;i<m;i++){
		for (j=0;j<n;j++){
			B[LIN(i,j,m,n)] = A[LIN(i,j,m,n)];
		}
	}
	

}

void elementVector(int8_t *v, unsigned int k, unsigned int m){
/*Cette fonction prend en argument un vecteur v,deux entiers non signes k et m.
Elle affecte a v[i] 0 si i != k et 1 si i = k.*/
	unsigned int i;
	for (i=0;i<m;i++) v[i] = 0;
	v[k] = 1;
		
}

void identityMatrix(int8_t *A, unsigned int m, unsigned int n){
/*Cette fonction prend en argument une matrice A, deux entiers non signes m et n.
Elle affecte a A[i][j] 0 si i!=j et 1 si i = j.*/
	unsigned int i,j,tailleMin;
	for (i=0;i<m;i++){
		for (j=0;j<n;j++){
			A[LIN(i,j,m,n)] = 0;
		}
	}
	if (m<n)tailleMin = m;
	else tailleMin = n;
	for (i=0;i<tailleMin;i++){
		A[LIN(i,i,m,n)]=1;
	}
	
}

int8_t randomBinValue(){
  return (int8_t)(rand()%2) ;
}

void randomBinVector(int8_t *v, unsigned int m){
  unsigned int i;
  for(i = 0; i < m; ++i){
    v[i] = randomBinValue();
  }
}
void randomBinMatrix(int8_t *A, unsigned int m, unsigned int n){
  unsigned int i, j;
  for(i = 0; i < m; ++i){
    for(j = 0; j < n; ++j){
      A[LIN(i,j,m,n)] = randomBinValue();
    }
  }
}


void readVector( int8_t *v, unsigned int m){
/*fait saisir à l’utilisateur un vecteur de dimension m.
Si l’une des saisies effectuées n’est correcte, la fonction échouera avec un appel à exit(1)*/

	unsigned int i;
	int scan;
	printf("Lecture du vecteur...\n");
	for (i = 0; i<m;i++){
		printf(" Rentrez la valeur de v[%d] : ",i);
		if (scanf("%d",&scan)!=1){
			printf("Erreur de saisie\n");
			exit(1);
		}
		v[i] = (int8_t)scan;
		printf("\n");
	}
}

void readMatrix( int8_t *A, unsigned int m, unsigned int n){
/*Cette fonction prend en argument une matrice A et deux entiers non signes m et n.
Elle fait saisir a l'utilisateur une matrice de taille m,n*/

	unsigned int i,j;
	int scan;
	printf("Lecture de la matrice...\n");
	for (i = 0; i<m;i++){
		for (j = 0;j<n;j++){
			printf(" Rntrez la valeur de A[%d,%d] : ",i,j);
			if (scanf("%d",&scan)!=1){
				printf("Erreur de saisie\n");
				exit(1);
			}
			A[LIN(i,j,m,n)] = (int8_t)scan;
			printf("\n");
		}
	}
}

void printSystem(int8_t *A, int8_t *v, unsigned int m, unsigned int n){
  /*affiche la matrice A de taille m,n à côté du vecteur de longueur m*/
	unsigned int i,j;
	printf("(");
	for (i=0;i<m;i++){
		for(j=0;j<n;j++){
			if (j==n-1) printf("%" PRId8,A[LIN(i,j,m,n)]);
			else printf("%" PRId8 ", ",A[LIN(i,j,m,n)]);
		}
		printf("| %" PRId8 ")", v[i]);
		if (i != m-1) printf("\n(");
		
	}
	printf("\n\n");
}


void printVector( int8_t *v, unsigned int m){
/* affiche vecteur v*/
	unsigned int i;
	printf("(");
	printf("%d",v[0]);
	for (i=1;i<m;i++){
		printf(",%d",v[i]);
	}
	printf(")\n\n");
}

void printMatrix( int8_t *A, unsigned int m, unsigned int n){
/*affiche la matrice A de taille m,n*/
	unsigned int i,j;
	printf("(");
	for (i=0;i<m;i++){
		//printf("\ni : %d\n",i);
		for(j=0;j<n;j++){
			//printf("\nj : %d\n",j);
			if (j==n-1) printf("%d",A[LIN(i,j,m,n)]);
			else printf("%d , ",A[LIN(i,j,m,n)]);
		}
		if (i != m-1) printf(")\n(");
		
	}
	printf(")\n\n");
}

int8_t maximumAbsVector(int8_t *v, unsigned int m){
/* renvoie la valeur v[i] de l’élément du vecteur v qui est maximale en valeur absolue parmi tous les éléments du vecteur.*/
	int8_t max = fabs(v[0]);
	unsigned int i;
	int8_t vact;
	for (i=1; i<m;i++){
		vact = fabs(v[i]);
		if (max<vact) max = vact;
	}
	return max;

}

int8_t maximumAbsMatrix(int8_t *A, unsigned int m, unsigned int n){
/* renvoie la valeur A[i,j] de l’élément de la matrice A qui est maximale en valeur absolue parmi tous les éléments de la matrice.*/ 
	int8_t max = fabs(A[LIN(0,0,m,n)]);
	unsigned int i,j;
	int8_t Aact;
	for (i=0; i<m;i++){
		for (j=0;j<n;j++){
			Aact = fabs(A[LIN(i,j,m,n)]);
			if (max<Aact) max = Aact;
		}
	}
	return max;
}

void setMatrixColumn(int8_t *A, int8_t *v, unsigned int k, unsigned int m, unsigned int n){
/*Cette fonction prend en argument une matrice A, un vecteur v, 3 entiers non signes k,m et n.
Elle affecte a la colonne k de A le vecteur v.*/
	if (k>=n)
	{
		printf("Erreur la colonne n'existe pas.\n");
		return;
	}
	unsigned int i;
	for (i=0;i<m;i++){
		A[LIN(i,k,m,n)] = v[i];
	}
	
}

void setMatrixRow(int8_t *A, int8_t *v, unsigned int k, unsigned int m, unsigned int n){
/*Cette fonction prend en argument une matrice A, un vecteur v, 3 entiers non signes k,m et n.
Elle affecte a la ligne k de A le vecteur v.*/
	if (k>=m)
	{
		printf("Erreur la ligne n'existe pas.\n");
		return;
	}
	unsigned int j;
	for (j=0;j<n;j++){
		A[LIN(k,j,m,n)] = v[j];
	}
}

void scaleVector(int8_t alpha, int8_t *v, unsigned int m){
/*Cette fonction prend en argument un flottant int8_t precision alpha, un vecteur v, un entier non signe m.
Elle multiplie v par le flottant alpha.*/
	unsigned int i;
	for (i=0;i<m;i++) v[i] *=alpha;
}

void scaleMatrix(int8_t alpha, int8_t *A, unsigned int m, unsigned int n){
/*Cette fonction prend en argument un flottant int8_t precision alpha, une matrice A, deux entiers non signes m,n.
Elle multiplie A par le flottant alpha.*/
	unsigned int i,j;
	for (i = 0; i < m; i++)
	{
		for (j = 0; j < n; j++)
		{
			A[LIN(i,j,m,n)]*=alpha;
		}
	}
}

void addBinVector(int8_t *w, int8_t *u, int8_t *v, unsigned int m){
/*Affecte aux elements w[i] la somme u[i]+v[i]*/
	unsigned int i;
	for (i = 0; i < m; i++){
		w[i] = u[i] ^ v[i];
	}
}

void addBinMatrix(int8_t *C, int8_t *A, int8_t *B, unsigned int m , unsigned int n){
/*Affecte aux elements C[i][j] la somme A[i][j]+B[i][j]*/
	unsigned int i,j;
	for (i = 0; i < m; i++)
	{
		for (j = 0; j < n; j++)
		{
			C[LIN(i,j,m,n)] = A[LIN(i,j,m,n)] ^ B[LIN(i,j,m,n)];
		}
	}
}

int8_t scalarBinProduct( int8_t* u, int8_t* v, unsigned int m){
	unsigned int i;
	int8_t res = 0;
	for(i = 0; i < m; ++i){
		res += u[i] & v[i];
	}
	return res%2;
}

void matrixVectorProduct(int8_t *v, int8_t *A, int8_t *u, unsigned int m, unsigned int n){
/* Calcul le produit de la matrice A de taille m,n avec le vecteur u de taille n 
Et affecte au vecteur v ce produit*/
	unsigned int i,j;
	int8_t resi = 0;
	for (i = 0; i < m; i++)
	{
		resi = 0;
		for (j = 0; j < n; j++)
		{
			resi ^= A[LIN(i,j,m,n)] & u[j]; 
		}

		v[i] = resi;
	}
}


void vectorMatrixProduct(int8_t *v, int8_t *u, int8_t *A, unsigned int m, unsigned int n){
/* Calcul le produit de la matrice A de taille m,n avec le vecteur u de taille n 
Et affecte au vecteur v ce produit*/
	unsigned int i,j;
	int8_t resi = 0;
	for (i = 0; i < m; i++)
	{
		resi = 0;
		for (j = 0; j < n; j++)
		{
			resi ^= A[LIN(j,i,m,n)] & u[j]; 
		}

		v[i] = resi;
	}
}

void matrixMatrixProduct( int8_t *C, int8_t *A, int8_t *B, unsigned int m, unsigned int n, unsigned int p){
/*  Calcul le produit matricielle d'une matrice A de taille m,n et de la matrice B de taille n,p
Et affecte a la matrice C de taille m,p ce produit*/ 
	unsigned int i,j,k;
	int8_t resij = 0;
	for (i = 0; i < m; i++)
	{
		for (j = 0; j < p; j++)
		{
			resij = 0;
			for (k = 0; k < n; k++){
				resij ^= A[LIN(i,k,m,n)] & B[LIN(k,j,n,p)];
			}
			C[LIN(i,j,m,p)] = resij;
		}
	}
}

void matrixSquare(int8_t *B, int8_t *A, unsigned int m, unsigned int n){

  matrixMatrixProduct(B,A,A,m,n,n);
  
}

/*void matrixPowerT(int8_t *B,int8_t *A, unsigned int t,unsigned int m, unsigned int n){ 
  if (!t)  return identityMatrix(m,n);
  else if (t == 1)  return A;

  if (!(t % 2)){
    return matrixPowerT(matrixMatrixProduct(A,A,m,n,n),t/2,m,n);
  }

  return matrixMatrixProduct(A,matrixPowerT(matrixMatrixProduct(A,A,m,n,n),t/2,m,n),m,n,n);
} A RECODER*/

int compareVector( int8_t *v, int8_t *u, unsigned int n){
	unsigned int i;
	for (i = 0; i < n; i ++)
	{
		if (v[i]!=u[i]) return 0;
	}
	return 1;
}


int determinantMatrixTriangulaire( int8_t *A , unsigned int n){
	unsigned int i;
	int8_t det =1;
	for (i = 0; i < n; i ++)
	{
		det *=  A[LIN(i,i,n,n)];
	}
	return det;
}



















