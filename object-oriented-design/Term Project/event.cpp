#include "wire.h"
#include "event.h"

//Constructor
Event::Event(Wire* wire, int time, int newState, int count) {
    this->newState = newState;
    this->time = time;
    this->wire = wire;
    this->count = count;
}

//Big Three
Event::Event(const Event& e) {
    Wire w = *e.GetWire();
    
    this->wire = new Wire(w.GetName(), w.GetIndex());
    this->time = e.GetTime();
    this->time = e.GetTime();
    this->count = e.GetCount();
}

Event& Event::operator=(const Event& e) {
    if (this == &e) {
        return *this;
    }
    
    delete this->wire;
    this->wire = e.GetWire();
    
    this->time = e.GetTime();
    this->time = e.GetTime();
    this->count = e.GetCount();
    
    return *this;
}

Event::~Event() {
    delete wire;
}

//Getters
Wire* Event::GetWire() const {
    return wire;
}

int Event::GetNewState() const {
    return newState;
}

int Event::GetTime() const {
    return time;
}

int Event::GetCount() const {
    return count;
}

//Comparison operator overloads
bool Event::operator>(const Event& rhs) const {
    //Event goes first if it happens earlier or was parsed earlier (use <)
    
    if (this->GetTime() == rhs.GetTime()) {
        //Secondary sort
        return this->GetCount() < rhs.GetCount();
    } else {
        return this->GetTime() < rhs.GetTime();
    }
}

bool Event::operator<(const Event& rhs) const {
    return (*this > rhs);
}
