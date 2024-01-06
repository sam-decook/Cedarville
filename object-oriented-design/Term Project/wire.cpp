#include "wire.h"

Wire::Wire(string name, int index) {
    this->name = name;
    this->index = index;
    
    //We always construct wire with a blank outputs vector
    //We fill the vector when we parse the gates
    vector<Gate*> blank;
    outputs = blank;
    
    state = 2;
    history = "";
}

//Getters and Setter
int Wire::GetState() const{
    return state;
}

int Wire::GetIndex() const {
    return index;
}

string Wire::GetName() const {
    return name;
}

string Wire::GetHistory() const {
    return history;
}

vector<Gate*> Wire::GetOutputs()  const {
    return outputs;
}


void Wire::SetState(int x) {
    state = x;
}

void Wire::SetIndex(int i) {
    index = i;
}

void Wire::SetName(string n) {
    name = n;
}

void Wire::SetHistory(string h) {
    history = h;
}

void Wire::AddGate(Gate* g) {
    outputs.push_back(g);
}
