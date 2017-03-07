#include <string.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <stdio.h>
#include <strings.h>
#include <stdlib.h>
#include <sys/types.h>
#include <arpa/inet.h>

int main(int argc, char ** argv) {

  struct sockaddr_in dest;
  int sockfd, mes_len, status;
  char * mes;

  if(argc != 2) {
    printf("Mauvais nombre d'arguments\n");
  }

  /* Structure sockaddr_in */

  memset(&dest, 0, sizeof(dest));
  dest.sin_family = AF_INET;
  dest.sin_port = htons(7654);
  dest.sin_addr.s_addr = inet_addr("127.0.0.1");

  /* Création de la socket */

  sockfd = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
  if(sockfd == -1) {
    perror("socket: ");
    return -1;
  }

  mes = argv[1];
  mes_len = strlen(mes);
  printf("%s\n", mes);

  /* Envoi du messasge */

  status = sendto(sockfd, mes, mes_len, 0, (struct sockaddr *) &dest, sizeof(dest));
  if(status == -1) {
    printf("Erreur sendto\n");
    return -1;
  }

  return 0;
}
