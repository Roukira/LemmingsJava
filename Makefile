GCC_FLAGS = -Wall -Werror -g
all: TestGauBinaire TestAddEncry TestLinEncry

addEncry.o	: addEncry.c
	gcc $(GCC_FLAGS) -c addEncry.c

FonctionsDeBases.o	: FonctionsDeBases.c
	gcc $(GCC_FLAGS) -c FonctionsDeBases.c -lm

GauBinaire.o	: GauBinaire.c FonctionsDeBases.h
	gcc $(GCC_FLAGS) -c GauBinaire.c FonctionsDeBases.c
	
LinEncry.o	: LinEncry.c FonctionsDeBases.h GauBinaire.h
	gcc $(GCC_FLAGS) -c LinEncry.c FonctionsDeBases.c GauBinaire.c
	
TestAddEncry		: TestAddEncry.c addEncry.o FonctionsDeBases.o 
	gcc $(GCC_FLAGS) -o TestAddEncry TestAddEncry.c addEncry.o FonctionsDeBases.o 
	
TestGauBinaire		: TestGauBinaire.c GauBinaire.o FonctionsDeBases.o
	gcc $(GCC_FLAGS) -o TestGauBinaire TestGauBinaire.c GauBinaire.o FonctionsDeBases.o

TestLinEncry		: TestLinEncry.c LinEncry.o GauBinaire.o FonctionsDeBases.o
	gcc $(GCC_FLAGS) -o TestLinEncry TestLinEncry.c LinEncry.o GauBinaire.o FonctionsDeBases.o

clean	:
	rm -f *.o TestGauBinaire TestAddEncry TestLinEncry
