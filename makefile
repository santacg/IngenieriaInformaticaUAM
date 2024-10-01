$FLAGS = -Wall -Wextra -pedantic -g 

$TARGET = main
$OBJS = main.o

main: main.o
	gcc -o $@ $^ 

main.o: main.c utils.h
	gcc -c $(FLAGS) main.c

clean: 
	rm -f main.o main
