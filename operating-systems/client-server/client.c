#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main (int argc, char **argv) {
    int sockfd;
    char sendline[100];
    char recvline[100];
    struct sockaddr_in servaddr;
 
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    bzero(&servaddr, sizeof servaddr);
 
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(50505);
 
    inet_pton(AF_INET, "127.0.0.1", &(servaddr.sin_addr));
 
    connect(sockfd, (struct sockaddr *) &servaddr, sizeof(servaddr));
 
    fgets(sendline, 100, stdin); //stdin = 0, for standard input
    write(sockfd, sendline, strlen(sendline) + 1);
    
    read(sockfd, recvline, 100);
    printf("%s", recvline);
    close(sockfd);
    
    return 0;
}
