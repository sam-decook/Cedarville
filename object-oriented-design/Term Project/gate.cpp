#include "gate.h"
#include "wire.h"

//Constructor
//NOTE: For a NOT gate, make the parameter in2 a nullptr
Gate::Gate(Wire *in1, Wire* in2, Wire* output, int delay, string type) {
    this->input1 =    in1;
    this->input2 =    in2;
    this->output = output;
    this->delay =  delay;
    this->type =   type;
    
    state = 2;
}

//Friend functions
int NOTGate(int a) {
    if (a == 0) {
        return 1;
    } else if(a == 1) {
        return 0;
    } else {
        return 2;
    }
}

int ANDGate(int a, int b) {
    if (a == 1 && b == 1) {
        return 1;
    } else if (a == 1 && b == 2) {
        return 2;
    } else if (a == 1 && b == 2) {
        return 2;
    } else {
        return 0;
    }
}

int ORGate(int a, int b) {
    if (a == 1 || b == 1) {
        return 1;
    } else if (a == 0 && b == 2) {
        return 1;
    } else if (a == 0 && b == 2) {
        return 1;
    } else {
        return 0;
    }
}

int XORGate(int a, int b) {
    if (a == 0 && b == 1) {
        return 1;
    } else if (a == 1 && b == 0) {
        return 1;
    } else if (a || b == 2) {
        return 2;
    } else {
        return 0;
    }
}

//Perform tri-state logic
int Gate::DoLogic(Wire* inA, Wire* inB) {
    if (type == "NOT") {
        return NOTGate(inA->GetState());
        
    } else if (type == "AND") {
        return ANDGate(inA->GetState(), inB->GetState());
        
    } else if (type == "OR") {
        return ORGate(inA->GetState(), inB->GetState());
        
    } else if (type == "XOR") {
        return XORGate(inA->GetState(), inB->GetState());
        
    } else if (type == "NAND") {
        return NOTGate( ANDGate(inA->GetState(), inB->GetState()) );
        
    } else if (type == "NOR") {
        return NOTGate( ORGate(inA->GetState(), inB->GetState()) );
        
    } else if (type == "XNOR") {
        return NOTGate( XORGate(inA->GetState(), inB->GetState()) );
        
    } else {
        return -1;
    }
}

//Check if wire change causes output wire to change
//The wires prev and next are the same wire, barring the state change
bool Gate::Evaluate(Wire* prev, Wire* next) {
    Wire* constWire;
    
    // Check which wire changed
    if (prev == input1) {
        constWire = input2;
    } else {
        constWire = input1;
    }

    //If their outputs differ, it requires a new Event
    return DoLogic(prev, constWire) != DoLogic(next, constWire);
}

//Getters
Wire* Gate::GetInput(int num) const {
    if (num == 1) {
        return input1;
    } else {
        return input2;
    }
}

Wire* Gate::GetOutput() const {
    return output;
}

int Gate::GetDelay() const {
    return delay;
}
