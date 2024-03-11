CC=gcc
CFLAGS=-g -Wall
LIBFLAGS=-pthread

INCLUDES=
OBJS=server.o picohttpparser.o httpresponse.o 
SRC=server.c client.c 

EXE=server client
SERVER= server

all: clean $(EXE)
server: $(SERVER)

server: $(OBJS)
	$(CC) $(LIBFLAGS) $^ -o $@ 

server.o: server.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $^

client: client.o
	$(CC) $(LIBFLAGS) $^ -o $@ 

client.o: client.c
	$(CC) $(CFLAGS) $(LIBFLAGS) -c $<

picohttpparser.o: picohttpparser.c picohttpparser.h
	$(CC) $(CFLAGS) -c $<

httpresponse.o: httpresponse.c httpresponse.h
	$(CC) $(CFLAGS) -c $<

clean: 
	rm -f $(OBJS) $(EXE) client.o
