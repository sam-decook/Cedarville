#ifndef wire_h
#define wire_h

#include <stdio.h>
#include <vector>
#include <string>

using namespace std;

class Gate;

class Wire {
public:
    //We always initialize wire with a blank vector<Gate*>
    Wire(string name, int index);

    //Getters and setters
    int             GetState() const;
    int             GetIndex() const;
    string           GetName() const;
    string        GetHistory() const;
    vector<Gate*> GetOutputs() const;
    
    void SetState  (int x);
    void SetIndex  (int i);
    void SetName   (string n);
    void SetHistory(string h);
    void AddGate   (Gate* g);

private:
    int state;
    int index;
    string name;
    string history;

    vector<Gate*> outputs;
};

#endif /* wire_h */
