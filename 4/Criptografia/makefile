CC = gcc
FLAGS = -g -Wall -Wextra -pedantic
LIBS = -lgmp

OBJS = Afin/afin.o Hill/hill.o Vigenere/vigenere.o Flujo/flujo.o Transposicion/transposicion.o Utils/utils.o Procesado/preprocesado.o Vigenere/kasiski.o Vigenere/ic.o Vigenere/criptoanalisis.o utils.o 

all: hill afin vigenere flujo transposicion procesado kasiski ic criptoanalisis

hill: Hill/hill.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS)

afin: Afin/afin.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS)

vigenere: Vigenere/vigenere.o
	$(CC) -o $@ $^

ic: Vigenere/ic.o
	$(CC) -o $@ $^

kasiski: Vigenere/kasiski.o Utils/utils.o
	$(CC) -o $@ $^ $(LIBS) `pkg-config --libs glib-2.0`

criptoanalisis: Vigenere/criptoanalisis.o
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

Vigenere/vigenere.o: Vigenere/vigenere.c Utils/utils.h
	$(CC) -c $(FLAGS) $< -o $@

Vigenere/ic.o: Vigenere/ic.c
	$(CC) -c $(FLAGS) $< -o $@

Vigenere/kasiski.o: Vigenere/kasiski.c
	$(CC) -c $(FLAGS) `pkg-config --cflags glib-2.0` $< -o $@

Vigenere/criptoanalisis.o: Vigenere/criptoanalisis.c
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
	rm -f hill afin vigenere flujo transposicion procesado kasiski ic criptoanalisis $(OBJS)

