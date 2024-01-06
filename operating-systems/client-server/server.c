#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int main() {
    char str[100];
    int listen_fd, comm_fd;
    struct sockaddr_in servaddr;
 
    listen_fd = socket(AF_INET, SOCK_STREAM, 0);
 
    bzero(&servaddr, sizeof(servaddr));
 
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = htons(INADDR_ANY);
    servaddr.sin_port = htons(50505);
    
    while (1) {
        bind(listen_fd, (struct sockaddr *) &servaddr, sizeof(servaddr));
        listen(listen_fd, 10);
 
        comm_fd = accept(listen_fd, (struct sockaddr*) NULL, NULL);
    
        bzero(str, 100);
        read(comm_fd, str, 100);
                
        //Transform: all letters to lowercase, vowels to uppercase
        //ASCII represents A-Z with 65-90, a-z with 97-122. Difference of 32
        char vowels[] = {'a', 'e', 'i', 'o', 'u'};
        
        for (int i = 0; i < strlen(str); i++) {
            if (str[i] >= 65 && str[i] <= 90) { //uppercase letters to lower
                str[i] = str[i] + 32;
            }
            
            for (int j = 0; j < 5; j++) {
                if (str[i] == vowels[j]) { //vowels to uppercase
                    str[i] = str[i] - 32;
                }
            }
        }
 
        printf("Echoing back - %s", str);
        write(comm_fd, str, strlen(str) + 1);
    }
}
