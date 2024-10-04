FLAGS = -Wall -Wextra -pedantic -g 

OBJS = afin.o hill.o utils.o

hill: hill.o utils.o
	gcc -o $@ $^ -lgmp

hill.o: hill.c utils.h
	gcc -c $(FLAGS) $^

afin.o: afin.c utils.h
	gcc -c $(FLAGS) $^

utils.o: utils.c utils.h
	gcc -c $(FLAGS) $^ -lgmp

clean: 
	rm -f hill afin $(OBJS) 

