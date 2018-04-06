#ifndef LINENCRY
#define LINENCRY

void createZi(int8_t *Z, int8_t *L, int8_t *K, int8_t *F, unsigned int n);

void createSystem(int8_t* Sys, int8_t *F, int8_t* L, unsigned int n);

int decrypt(int8_t *res, int8_t *L, int8_t *Z, int8_t *F, unsigned int n);


#endif
