CC=gcc
CFLAGS=-g 
LIBFLAGS=-pthread

INCLUDES=
OBJS=server.o client.o
SRC=server.c client.c

EXE=server client

all: clean $(EXE)

server: server.o
	$(CC) $(LIBFLAGS) $^ -o $@ 

server.o: server.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $^

client: client.o
	$(CC) $(LIBFLAGS) $^ -o $@ 

client.o: client.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $^

clean: 
	rm -f $(OBJS) $(EXE)
