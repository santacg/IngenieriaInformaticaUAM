FLAGS = -g -Wall -Wextra -pedantic 

OBJS = Afin/afin.o Hill/hill.o Vignere/vignere.o Flujo/flujo.o utils.o Procesado/preprocesado.o

hill: Hill/hill.o utils.o
	gcc -o $@ $^ -lgmp

hill.o: Hill/hill.c utils.h
	gcc -c $(FLAGS) $< -o $@

afin.o: Afin/afin.c utils.h
	gcc -c $(FLAGS) $< -o $@

vignere: Vignere/vignere.o 
	gcc -o $@ $^

vignere.o: Vignere/vignere.c
	gcc -c $(FLAGS) $< -o $@

flujo: Flujo/flujo.o
	gcc -o $@ $^

flujo.o: Flujo/flujo.c
	gcc -c $(FLAGS) $< -o $@

procesado: Procesado/preprocesado.o
	gcc -o $@ $^

Procesado/preprocesado.o: Procesado/preprocesado.c
	gcc -c $(FLAGS) $< -o $@

utils.o: Utils/utils.c
	gcc -c $(FLAGS) $< -o $@

clean: 
	rm -f hill afin vignere flujo procesado $(OBJS)

