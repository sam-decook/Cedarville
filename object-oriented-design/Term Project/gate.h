#ifndef gate_h
#define gate_h

#include <stdio.h>
#include <string>

using namespace std;

class Wire;

/*
 States will be represented as integers
 0 as 0          1 as 1          X as 2
 */

class Gate {
public:
    //Constructor
    Gate(Wire* in1, Wire* in2, Wire* output, int delay, string type);
    
    //Getters
    Wire* GetInput(int num) const;
    Wire* GetOutput()       const;
    int   GetDelay()        const;
    
    //Preform the tri-state logic
    int DoLogic(Wire* inA, Wire* inB);
    //Check if wire change causes output wire to change
    bool Evaluate(Wire* prev, Wire* next);
    
    //Friend functions
    friend int NOTGate(int a);
    friend int ANDGate(int a, int b);
    friend int ORGate(int a, int b);
    friend int XORGate(int a, int b);
    
private:
    Wire* input1;
    Wire* input2;
    Wire* output;
    
    int state;
    int delay;
    string type;
};

#endif /* gate_h */
