#ifndef ADDENCRY
#define ADDENCRY

    
int8_t bitabit(int8_t a, int8_t b);

void addEncry(int8_t* m, int8_t* k, int8_t* res, unsigned int taille);

void randFill(int8_t* a, unsigned int taille);

void printTab(int8_t* a, unsigned int taille);

void check(int8_t *m, int8_t *res, unsigned int taille); 


#endif
