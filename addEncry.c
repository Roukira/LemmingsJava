#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>


int8_t bitabit(int8_t a, int8_t b){
  return (a ^ b);//addition bit à bit modulo 2
}

void addEncry(int8_t* m, int8_t* k, int8_t* res, unsigned int taille){
  int i;

  for(i = 0; i < taille; ++i){
    res[i] = bitabit(m[i], k[i]);
  }
}

void randFill(int8_t* a, unsigned int taille){
  int i;

  for(i = 0; i < taille; ++i){
    a[i] = (int8_t)(rand() % 2);
  }
}

void printTab(int8_t* a, unsigned int taille){
  int i;
  putchar('[');
  for(i = 0; i < taille; ++i){
    printf("%" PRId8, a[i]);
    if (i<taille-1) putchar(',');
  }
  printf("]\n");
}

void check(int8_t *m, int8_t *res, unsigned int taille){
  int i;

  for(i = 0; i < taille; ++i){
    if(m[i] != res[i]){
      printf("Les messages ne sont pas identiques.\n");
      return;
    }
  }
  printf("Les messages sont identiques.\n");
} 
