#include <netinet/in.h>
#include <sys/socket.h>
#include <stdio.h>
#include <strings.h>
#include <stdlib.h>
#include <sys/types.h>
#include <arpa/inet.h>

int main(int argc, char ** argv) {

  struct sockaddr_in myaddr = { 0 };
  int sockfd, mes_len;
  char * mes;

  /* Structure sockaddr_in */

  myaddr.sin_family = AF_INET;
  myaddr.sin_port = 7654;
  myaddr.sin_addr.s_addr = inet_addr("224.0.0.1");

  /* Création de la socket */

  sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if(sockfd == -1) {
    perror("socket: ");
    return -1;
  }

  mes = argv[1];
  mes_len = sizeof(mes);

  /* Envoi du messasge */

  sendto(sockfd, mes, mes_len, 0, (struct sockaddr*) &myaddr, sizeof(myaddr));

  return 0;
}
