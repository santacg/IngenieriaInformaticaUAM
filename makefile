FLAGS = -Wall -Wextra -pedantic -g 

TARGET = afin
OBJS = afin.o utils.o

all: $(TARGET)

$(TARGET): $(OBJS)
	gcc -o $@ $^ -lgmp -L.

afin.o: afin.c utils.o 
	gcc -c $(FLAGS) $^

utils.o: utils.c utils.h
	gcc -c $(FLAGS) $^

clean: 
	rm -f $(TARGET) $(OBJS)

