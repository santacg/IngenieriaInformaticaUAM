CC = gcc
FLAGS = -g -Wall -Wextra -pedantic
LIBS = -lgmp

OBJS = Afin/afin.o Hill/hill.o Vignere/vignere.o Flujo/flujo.o Transposicion/transposicion.o Utils/utils.o Procesado/preprocesado.o 

all: hill afin vignere flujo transposicion procesado

hill: Hill/hill.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS)

afin: Afin/afin.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS)

vignere: Vignere/vignere.o
	$(CC) -o $@ $^

flujo: Flujo/flujo.o
	$(CC) -o $@ $^

transposicion: Transposicion/transposicion.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS)

procesado: Procesado/preprocesado.o
	$(CC) -o $@ $^

Hill/hill.o: Hill/hill.c Utils/utils.h
	$(CC) -c $(FLAGS) $< -o $@

Afin/afin.o: Afin/afin.c Utils/utils.h
	$(CC) -c $(FLAGS) $< -o $@

Vignere/vignere.o: Vignere/vignere.c
	$(CC) -c $(FLAGS) $< -o $@

Flujo/flujo.o: Flujo/flujo.c
	$(CC) -c $(FLAGS) $< -o $@

Transposicion/transposicion.o: Transposicion/transposicion.c Utils/utils.h
	$(CC) -c $(FLAGS) $< -o $@

Procesado/preprocesado.o: Procesado/preprocesado.c
	$(CC) -c $(FLAGS) $< -o $@

Utils/utils.o: Utils/utils.c
	$(CC) -c $(FLAGS) $< -o $@

clean:
	rm -f hill afin vignere flujo transposicion procesado $(OBJS)

