## Compilateur C et éditeur de liens
CC = gcc
## Options pour la compilation du C
CFLAGS  = -Wall -Werror -ansi -pedantic
#  Spécifie la version de la norme POSIX à respecter
# CFLAGS += -D_XOPEN_SOURCE=500
#  Active les informations de débogage
CFLAGS += -g -D_BSD_SOURCE
## Options de l’édition de liens
LDFLAGS  = -g

SendUDP: SendUDP.o
	${CC} ${LDFLAGS} -o $@ $^

clean:
	rm *.o || true

realclean: clean
	rm SendUDP || true
