#ifndef GAUBINAIRE
#define GAUBINAIRE

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h> 
#include <inttypes.h>
#include <math.h>
#include <time.h>

void changementLigneGauss(int8_t *A, unsigned int ligne1, unsigned int ligne2, unsigned int n);

void gaussianElimination( int8_t *A, int8_t *b, unsigned int n);

void solveTriangularUpper(int8_t *x, int8_t *A, int8_t *b, unsigned int n);

int solveSystemGauss( int8_t *x, int8_t *A, int8_t *b, unsigned int n);

#endif
