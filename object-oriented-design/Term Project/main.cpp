#include <stdio.h>
#include <vector>
#include <string>
#include <queue>
#include <iostream>
#include <fstream>
#include "gate.h"
#include "wire.h"
#include "event.h"

using namespace std;

//Used while parsing circuit file, in gate creation
//Checks if a wire exist, creates one if not
void HandleImplicitWire(vector<Wire*> wires, int wireIndex) {
    if (wires.size() - 1 < wireIndex) { //The wire doesn't exist and is outside the vector
        //Fill preceding spots with blank wires
        while (wires.size() < wireIndex) {
            wires.emplace_back(new Wire("", 0));
        }
        
        //Create implicit wire
        wires.emplace_back(new Wire("", wireIndex));
    }
    else if (wires[wireIndex]->GetIndex() == 0) { //The wire doesn't exist, it is just blank
        //Just update the index
        wires[wireIndex]->SetIndex(wireIndex);
    } //else the wire exists, and we don't have to do anything
}



int main() {

    // 1. Parse circuit file
    ifstream circuitIS;     //Circuit input file stream
    string fileName;

    // Open file
    cout << "Enter circuit file: ";
    cin >> fileName;
    circuitIS.open(fileName + ".txt");

    if (!circuitIS.is_open()) {
        cerr << "Error: could not open file";
    }

    //Used to simulate the circuit
    vector<Wire*> wires(1);
    vector<Gate*> gates;

    //Wire variables
    string wireName;
    int wireNum;

    //Gate variables
    string gateName;
    string gateDelayS;  //Can't parse straight to int
    int gateDelay;
    int wireIn1;
    int wireIn2;
    int wireOut;

    //Parse the file
    string input;
    getline(circuitIS, input);  //Skip first line

    while (!circuitIS.eof()) {
        //The wire's number corresponds with its index in the wires vector
        //Wires start out going nowhere
        //They are connected to gates when the gates are parsed

        //Read keyword
        circuitIS >> input;

        if (input == "INPUT" || input == "OUTPUT") {  //Wire
            circuitIS >> wireName >> wireNum;

            //Fill array with blank wires to get to correct index
            while (wires.size() < wireNum) {
                wires.emplace_back(new Wire("", 0));
            }
            
            //Add the wire in the correct index
            if (wires.size() == wireNum) {
                wires.emplace_back(new Wire(wireName, wireNum));
            }
            else { //Blank wire exists at index, fill in the wire
                wires.at(wireNum) = new Wire(wireName, wireNum);
            }
        }
        else if (input == "NOT") {  //NOT gate
            gateName = input;
            circuitIS >> gateDelayS >> wireIn1 >> wireOut;
            
            //Change gateDelay from string to int
            gateDelay = stoi(gateDelayS.substr(0, gateDelayS.find("ns")));
            
            //Create any wires called that weren't created
            HandleImplicitWire(wires, wireIn1); //Input wire
            HandleImplicitWire(wires, wireOut); //Input wire 1

            //Add to vector
            Gate* g = new Gate(wires[wireIn1], nullptr, wires[wireOut], gateDelay, gateName);
            gates.push_back(g);

            //Connect input wire to gate
            wires[wireIn1]->AddGate(g);
        }
        else {  //Gates with 2 inputs
            //Parse
            gateName = input;
            circuitIS >> gateDelayS >> wireIn1 >> wireIn2 >> wireOut;

            //Change gateDelay from string to int
            gateDelay = stoi(gateDelayS.substr(0,gateDelayS.find("ns")));

            //Create any wires called that weren't created
            HandleImplicitWire(wires, wireIn1); //Input wire 1
            HandleImplicitWire(wires, wireIn2); //Input wire 2
            HandleImplicitWire(wires, wireOut); //Output wire

            //Add to vector
            Gate* g = new Gate(wires[wireIn1], wires[wireIn2], wires[wireOut], gateDelay, gateName);
            gates.push_back(g);

            //Connect input wires to gate
            wires[wireIn1]->AddGate(g);
            wires[wireIn2]->AddGate(g);
        }
    }

    // Close file
    circuitIS.close();



    // 2. Parse vector file
    ifstream vectorIS;
    vectorIS.open(fileName + "_v.txt");

    if (!vectorIS.is_open()) {
        cerr << "Error: could not open file";
    }

    priority_queue<Event*> eventQueue; //The main simulation will run off of this
    int count = 1; //Secondary sort mechanism

    //Event variables
    wireName = "";  //already declared, reset
    int time;
    int state;
    Wire* currentWire;

    getline(vectorIS, input); //Skip top line

    while (!vectorIS.eof()) {
        vectorIS >> input; //Skip keyword
        vectorIS >> wireName >> time >> state;

        //Find wire
        for (int i = 1; i < wires.size(); i++) {
            if (wireName == wires.at(i)->GetName()) {
                currentWire = wires.at(i);

                //Create and add event to queue
                Event* myEvent = new Event(currentWire, time, state, count);
                eventQueue.push(myEvent);
                count++;
            }
        }
    }



    // 3. Simulate circuit
    time = 0;
    int counterTime = 0;

    while (!eventQueue.empty()) {
        //Remove top Event e from queue
        Event* currEvent = eventQueue.top();
        time = currEvent->GetTime();
        eventQueue.pop();

        //set each wire's history
        for (int i = 1; i < wires.size(); i++) {
            string oldState = to_string(wires[i]->GetState());  //We fill the history retroactively
                                                                //Just as the wire is going to change, we update the history
                                                                //  until 1ns before the time of the event (when it changes)
            int origCounterTime = counterTime;

            //Add the old state to the history until just before the "present" time
            /*for (counterTime; counterTime < time; counterTime++) {
                wires[i]->SetHistory(wires[i]->GetHistory() + oldState);
            } Commented out to remove warning */

            counterTime = origCounterTime; //Must reset for the next wire
        }
        counterTime = time; //So we don't overwrite history

        //Determine if e causes a future wire state change
        Wire* currWire = currEvent->GetWire();
        Wire* nextWire = currWire;
        nextWire->SetState(currEvent->GetNewState()); //nextWire has the new state

        vector<Gate*> gates = currWire->GetOutputs();

        //Check every gate the wire leads to
        for (int i = 0; i < gates.size(); i++) {
            Gate* gate = gates[i];

            if (gate->Evaluate(currWire, nextWire)) {  //It causes a change
                //Create and queue any future wire state changes as new Events
                //Get params to create a new event
                Wire* output = gate->GetOutput();
                int eventTime = time + gate->GetDelay();
                int nextState;

                //To find the next state, first find the other wire
                Wire* constWire;
                if (currWire == gate->GetInput(1)) {
                    constWire = gate->GetInput(2);
                }
                else {
                    constWire = gate->GetInput(1);
                }

                //With the other wire, perform gate logic to set the next state
                nextState = gate->DoLogic(nextWire, constWire);

                //Schedule the new event
                Event* newE = new Event(output, eventTime, nextState, count);
                count++;
                eventQueue.emplace(newE);

                // Apply e's effect to the wire
                currWire->SetState(nextState);
            } //It doesn't cause a change, no events need to be created
        }
    }

    //Print up to after the simulation ends
    for (int i = 1; i < wires.size(); i++) {
        string oldState = to_string(wires[i]->GetState());
        int origCounterTime = counterTime;

        //Add the old state to the history until after the time the simulation ends (5ns after)
        /*for (counterTime; counterTime <= time + 5; counterTime++) {
            wires[i]->SetHistory(wires[i]->GetHistory() + oldState);
        } Commented out to remove warning */
        counterTime = origCounterTime;
    }

    

    // 4. Print output
    cout << "Waveform of circuit" << endl;

    //Loop through all wires outputting only the named wires' history
    for (int i = 1; i < wires.size(); i++) {
        if (wires[i]->GetName() != "") { //Only print named wires
            Wire* wire = wires[i];
            cout << wire->GetName() << ": ";

            for (int j = 0; j < wire->GetHistory().size(); j++) {
                //Change each number in history to a visual representation
                if (wire->GetHistory()[j] == '0') {
                    cout << "_";

                }
                else if (wire->GetHistory()[j] == '1') {
                    cout << "-";

                }
                else {
                    cout << "X";
                }
            }

            cout << endl;
        }
    }


    return 0;
}
