/* *******************************************************************
 * Summary: This file includes a demonstration of a solution to the
 * producer-consumer problem within interprocess communication.
 *
 * Author: Sam DeCook
 * Created: Feb 2022
 * ******************************************************************/

#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <chrono>

using namespace std;

static const int MAX = 12;              //amount of letters produced
static const int N = 7;                 //buffer size

mutex mtx;
char buffer[N];
int ind = 0;                            //index of next empty spot
condition_variable_any cvProd;          //signals between threads
condition_variable_any cvCons;

void producer() {
    char c;
    for (int i = 0; i < MAX; i++) {
        c = 'A' + rand() % 26;          //generate random char
        
        mtx.lock();                     /* ENTER CRITICAL REGION */
        while (ind == N) {
            cvProd.wait(mtx);           //wait if buffer is full
        }
        
        buffer[ind] = c;
        ind++;
        //ASCII uppercase and lowercase are 32 apart (upper -> lower)
        cout << (char) (c + 32) << endl;//print out lowercase
        
        cvCons.notify_all();            //wakeup consumer
        mtx.unlock();                   /* LEAVE CRITICAL REGION */
    }
}

void consumer() {
    char c;
    for (int i = 0; i < MAX; i++) {
        mtx.lock();                     /* ENTER CRITICAL REGION */
        while (ind == 0) {
            cvCons.wait(mtx);           //wait if buffer is empty
        }
        
        ind--;
        c = buffer[ind];                //buffer isn't overwritten
        cout << " " << c << endl;       //print out uppercase
        
        cvProd.notify_one();            //wakeup producer
        mtx.unlock();                   /* LEAVE CRITICAL REGION */
    }
}

int main(int argc, char** argv) {
    cout << "The Producer-Consumer Problem" << endl;
    cout << "Buffer size: 7" << endl;
    
    thread prod (producer);
    this_thread::sleep_for(chrono::milliseconds(20));
    thread cons (consumer);
    
    prod.join();                        //re-syncronize
    cons.join();
        
    return 0;
}
