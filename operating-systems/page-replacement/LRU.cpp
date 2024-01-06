#include "LRU.h"

LRU::LRU(int numPageFrames) : ReplacementAlgorithm(numPageFrames) {
    pageFaultCount = 0;
}

void LRU::insert(int pageNumber) {
    //Search through to see if it is already there
    for (int x : frameList) {
        if (x == pageNumber) {
            //Added code to implement LRU
            //Front is removed first, so back (most recently used) will be removed last
            frameList.remove(x);
            frameList.push_back(x);
            
            return;
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
