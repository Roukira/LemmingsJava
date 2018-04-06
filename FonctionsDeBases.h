#ifndef FONCTIONSDEBASES
#define FONCTIONSDEBASES

int8_t *allocateVector( unsigned int m);

int8_t *allocateMatrix( unsigned int m, unsigned int n);

void freeVector( int8_t *v);

void freeMatrix( int8_t *A);

void copyVector( int8_t *v, int8_t *u, unsigned int m);

void copyMatrix(int8_t *B, int8_t *A, unsigned int m, unsigned int n);
void elementVector(int8_t *v, unsigned int k, unsigned int m);

void identityMatrix(int8_t *A, unsigned int m, unsigned int n);

int8_t randomBinValue();

void randomBinVector(int8_t *v, unsigned int m);

void randomBinMatrix( int8_t *A, unsigned int m, unsigned int n);

void readVector( int8_t *v, unsigned int m);

void readMatrix( int8_t *A, unsigned int m, unsigned int n);

void printSystem(int8_t *A, int8_t *v, unsigned int m, unsigned int n);

void printVector( int8_t *v, unsigned int m);

void printMatrix( int8_t *A, unsigned int m, unsigned int n);

int8_t maximumAbsVector(int8_t *v, unsigned int m);

int8_t maximumAbsMatrix(int8_t *A, unsigned int m, unsigned int n);

void setMatrixColumn(int8_t *A, int8_t *v, unsigned int k, unsigned int m, unsigned int n);

void setMatrixRow(int8_t *A, int8_t *v, unsigned int k, unsigned int m, unsigned int n);

void scaleVector(int8_t alpha, int8_t *v, unsigned int m);

void scaleMatrix(int8_t alpha, int8_t *A, unsigned int m, unsigned int n);

void addBinVector(int8_t *w, int8_t *u, int8_t *v, unsigned int m);

void addBinMatrix(int8_t *C, int8_t *A, int8_t *B, unsigned int m , unsigned int n);

int8_t scalarBinProduct( int8_t* u, int8_t* v, unsigned int m);

void matrixVectorProduct(int8_t *v, int8_t *A, int8_t *u, unsigned int m, unsigned int n);

void vectorMatrixProduct(int8_t *v, int8_t *u, int8_t *A, unsigned int m, unsigned int n);

void matrixMatrixProduct( int8_t *C, int8_t *A, int8_t *B, unsigned int m, unsigned int n, unsigned int p);

void matrixSquare(int8_t *B, int8_t *A, unsigned int m, unsigned int n);

int compareVector( int8_t *v, int8_t *u, unsigned int n);

int determinantMatrixTriangulaire( int8_t *A , unsigned int n);

#endif


















