FLAGS = -Wall -Wextra -pedantic -g 

OBJS = Afin/afin.o Hill/hill.o Vignere/vignere.o utils.o

hill: Hill/hill.o utils.o
	gcc -o $@ $^ -lgmp

hill.o: Hill/hill.c utils.h
	gcc -c $(FLAGS) $^

afin.o: afin.c utils.h
	gcc -c $(FLAGS) $^

vignere: Vignere/vignere.o 
	gcc -o $@ $^

vignere.o: vignere.c
	gcc -c $(FLAGS) $^

utils.o: Utils/utils.c Utils/utils.h
	gcc -c $(FLAGS) $^ -lgmp

clean: 
	rm -f hill afin vignere $(OBJS) 

