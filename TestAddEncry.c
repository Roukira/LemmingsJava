#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <time.h>
#include "addEncry.h"
#include "FonctionsDeBases.h"

int main(int argc, char** argv){
  unsigned int taille = 128u;
  if(argc == 2) taille = (unsigned int)atoi(argv[1]);
  int8_t *m = allocateVector(taille);//message à coder
  int8_t *k = allocateVector(taille);//clé d'encryptage
  int8_t *res = allocateVector(taille);//message codé
  
  srand(time(NULL));

  randFill(m,taille);//remplissage aléatoire
  randFill(k,taille);//du message et de la clé

  printf("\nmessage : ");
  printTab(m,taille);//affichage du message
  printf("\ncle : ");
  printTab(k,taille);//de la clé (facultatif)

  addEncry(m,k,res,taille);//cryptage du message m
  printf("\nmessage crypte : ");
  printTab(res,taille);// avec la clé k et stockage du message codé dans res(res = m + k)

  addEncry(res,k,res,taille);//ajoute de la clé k sur res (res + k = m)
  printf("\nmessage decrypte : ");
  printTab(res,taille);//affichage du nouveau resultat (on veut que ce soit m)
  
  check(res,m,taille);//print true si res=m False sinon
  
  freeVector(m);
  freeVector(k);
  freeVector(res);

  return 0;
}
