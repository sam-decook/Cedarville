// Author:    Sam DeCook
// Purpose:  This file includes the main and reverse function needed to complete HW1
// Date:       30 Jan 2021


#include <iostream>
#include <string>
#include <cstring>
using namespace std;

int zymain(int, char**);
void reverse(char*, char*);

int main(int argc, char **argv) {
    return zymain(argc, argv);
}

int zymain(int argc, char **argv) {
    char* str = argv[1];

    //Ensure correct amount of arguments
    if (argc != 4) {
        cerr << "Usage: must have exactly four arguments\n";
        return 1;
    }

    //Check indexes are integers
    if (!isdigit(*argv[2]) || !isdigit(*argv[3])) {
        cerr << "Usage: indexes must be integers\n";
        return 2;
    }

    //Check front index
    if (atoi(argv[2]) > 0 || atoi(argv[2]) >= atoi(argv[3])) {
        cerr << "Usage: front index must be greater than 0 and less than rear index\n";
        return 3;
    }

    //Check rear index
    if ((int) strlen(str) < atol(argv[3])) {
        cout << "Usage: rear index must be less than the size of the string\n";
        return 4;
    }
    
    int fIndex = atoi(argv[2]);
    int rIndex = atoi(argv[3]);
    
    //Pointers to the first and last char to be reversed
    char* front = &(str[fIndex]);
    char* rear = &(str[rIndex]);
    
    reverse(front, rear);
    cout << " \"" << str << "\"";
    
    return 0;
}

void reverse(char* front, char* rear) {
    char temp;

    while (front < rear) {
        //Flip the chars
        temp = *front;
        *front = *rear;
        *rear = temp;

        //Move pointers toward each othe
        front++;
        rear--;
    }
}