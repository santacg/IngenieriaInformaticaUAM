CC=gcc
CFLAGS=-g -Wall
LIBFLAGS=-pthread

INCLUDES=
OBJS=server.o client.o picohttpparser.o
SRC=server.c client.c

EXE=server client

all: clean $(EXE)

server: server.o picohttpparser.o
	$(CC) $(LIBFLAGS) $^ -o $@ 

server.o: server.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $^

client: client.o
	$(CC) $(LIBFLAGS) $^ -o $@ 

client.o: client.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $<

picohttpparser.o: picohttpparser.c picohttpparser.h
	$(CC) $(CFLAGS) -c $<

clean: 
	rm -f $(OBJS) $(EXE)
