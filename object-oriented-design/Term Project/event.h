#ifndef event_h
#define event_h

#include <stdio.h>
#include "wire.h"


class Event {
public:
    //Constructor
    Event(Wire* wire, int newSate, int time, int count);
    
    //Big three
    Event(const Event& e);
    Event& operator=(const Event& e);
    ~Event();
    
    //Getters
    Wire* GetWire()     const;
    int   GetTime()     const;
    int   GetNewState() const;
    int   GetCount()    const;
    
    //Comparison operator overloads
    bool operator>(const Event& rhs) const;
    bool operator<(const Event& rhs) const;
    
private:
    Wire* wire;
    int   time;
    int   newState;
    int   count;
};

#endif /* event_h */
