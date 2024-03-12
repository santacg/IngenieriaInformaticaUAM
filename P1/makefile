CC=gcc
CFLAGS=-g -Wall -pthread
LIBFLAGS=-pthread

SRCPATH=./src/
INCLUDEPATH=./includes/
OBJPATH=./obj/
OBJS=server.o picohttpparser.o httpresponse.o 

EXE=server 

all: clean folders $(EXE)

folders:
	mkdir $(OBJPATH)

server: $(OBJPATH)server.o $(OBJPATH)httpresponse.o $(OBJPATH)picohttpparser.o
	$(CC) $^ -o $@ 

$(OBJPATH)server.o: $(SRCPATH)server.c $(SRCPATH)httpresponse.c $(INCLUDEPATH)httpresponse.h
	$(CC) $(CFLAGS) -c $(SRCPATH)server.c -o $(OBJPATH)server.o 

$(OBJPATH)picohttpparser.o: $(SRCPATH)picohttpparser.c $(INCLUDEPATH)picohttpparser.h
	$(CC) $(CFLAGS) -c $(SRCPATH)picohttpparser.c -o $(OBJPATH)picohttpparser.o

$(OBJPATH)httpresponse.o: $(SRCPATH)httpresponse.c $(SRCPATH)picohttpparser.c $(INCLUDEPATH)httpresponse.h $(INCLUDEPATH)picohttpparser.h
	$(CC) $(CFLAGS) -c $(SRCPATH)httpresponse.c -o $(OBJPATH)httpresponse.o

valgrind:
	valgrind --leak-check=full --track-origins=yes --show-leak-kinds=all ./$(EXE)

clean: 
	rm -rf $(OBJPATH) $(EXE)
