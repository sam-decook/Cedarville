#include "FIFO.h"

FIFO::FIFO(int numPageFrames) : ReplacementAlgorithm(numPageFrames) {
    pageFaultCount = 0;
}

void FIFO::insert(int pageNumber) {
    //Search through to see if it is already there
    for (int x : frameList) {
        if (x == pageNumber) {
            return; //do nothing, it's already loaded
        }
    }
    
    //If the page isn't in the frames, add to the back
    pageFaultCount++;
    frameList.push_back(pageNumber);

    //Remove first page if size exceeds the max frame count
    if (frameList.size() > pageFrameCount) {
        frameList.pop_front();
    }
}
