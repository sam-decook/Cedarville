// Author:  Sam DeCook
// Date:    2/26/21
// Purpose: To implement a simple, sorted linked-list of positive integers

#include "LLSortedPosInt.h"

// The linked-list is constructed of Node elements
struct Node {
   int   key;
   Node *next;
};

// createNode() allocates a Node and initializes it with the
// given parameter values to simplify the initilization of a Node
static NodePtr createNode(int key, NodePtr p) {
   NodePtr n = new Node;

   n->key  = key;
   n->next = p;

   return n;
}

/* Student implementation begins here */

// Constructors
LLSortedPosInt::LLSortedPosInt() {
   // create the sentinal Node at the head of the list
    head = createNode(HEAD_OF_LIST, nullptr);
}

LLSortedPosInt::LLSortedPosInt(int key) {
    // create the sentinal Node at the head of the list
    head = createNode(HEAD_OF_LIST, nullptr);
    
    // add the single element key, as long as it is positive
    if (key >= 0) {
        head->next = createNode(key, nullptr);
    }
}

LLSortedPosInt::LLSortedPosInt(int *keys, int n) {
    // create the sentinal node at the head of the list
    head = createNode(HEAD_OF_LIST, nullptr);
    
    for (int i = 0; i < n; i++) {
        // add new Nodes for each positive value in keys
        if (*keys > 0) {
            // Inserts the key sorted
            insert(*keys);
        }
        keys++;
    }
}

LLSortedPosInt::LLSortedPosInt(const LLSortedPosInt &list) {
    // create a deep copy of the input list l
    head = createNode(HEAD_OF_LIST, nullptr);
    NodePtr p = head;
    
    // Pointer used to walk through l
    NodePtr p2 = list.head->next;
    
    while (p2 != nullptr) {
        p->next = createNode(p2->key, nullptr);
        p = p->next;
        p2 = p2->next;
    }
    
}

LLSortedPosInt::~LLSortedPosInt() {
    // free the Nodes in *this after sentinal
    NodePtr p = head->next;
    
    // free the list, other than the sentinal
    int key;
    while (p != nullptr) {
        key = p->key;
        p = p->next;    // So you're pointing at a node after the old one is deleted
        remove(key);
    }
    
    // free sentinal
    delete head;
}

// Assignment Operator
LLSortedPosInt&  LLSortedPosInt::operator=(const LLSortedPosInt &list) {
    // handle self assignment
    if (this == &list) {
        return *this;
    }

    // free old elements of the list before the new elements from l are assigned
    NodePtr p = head->next;
    
    // free the list, other than the sentinal
    int key;
    while (p != nullptr) {
        key = p->key;
        p = p->next;    // So you're pointing at a node after the old one is deleted
        remove(key);
    }
    
    // Reset the sentinal
    head->next = nullptr;
    p = head;

    // build the list as a deep copy of l
    // Pointer used to walk through l
    NodePtr p2 = list.head->next;
    
    while (p2 != nullptr) {
        p->next = createNode(p2->key, nullptr);
        p = p->next;
        p2 = p2->next;
    }

    return *this;
}

// Print Operator (a non-member function)
ostream&  operator<<  (ostream &out, const LLSortedPosInt &l) {
    // A list will be printed with the values enclosed in angle brakcets
    // Ex: <1, 2, 3>
    out << "<";
    
    NodePtr p = l.head->next;       //You don't print out the sentinal
    
    if (p != nullptr) {             //Check for blank list
        out << p->key;              //Fence-post problem
        p = p->next;
        
        while (p != nullptr) {
            out << ", " << p->key;
            p = p->next;
        }
    }
    
    out << ">";

    return out;
}

// Boolean Functions
bool LLSortedPosInt::isEmpty() const {
    // return true if only the sentinal is in the list; return false otherwise
    return (head->key == HEAD_OF_LIST) && (head->next == nullptr);
}

bool LLSortedPosInt::containsElement(int key) const {
    // return true if key is in the list; return false otherwise
    NodePtr p = head->next;
    
    while (p != nullptr) {
        if (p->key == key) {
            return true;
        }
        p = p->next;
    }
    
    return false;
}

// Other Operator Member Functions
bool LLSortedPosInt::operator==(const LLSortedPosInt &l) const {
    // Skip past the sentinal node
    NodePtr p1 = this->head->next;
    NodePtr p2 =     l.head->next;
    
    while (p1 != nullptr && p2 != nullptr) {
        if (p1->key != p2->key) {
            return false;
        }
        p1 = p1->next;
        p2 = p2->next;
    }
    
    // One must be null. If the other one isn't, it points
    // to another node, making the list unequal
    if (p1 != p2) {
        return false;
    }
    
    return true;
}

bool LLSortedPosInt::operator!=(const LLSortedPosInt &l) const {
   return !(*this == l);
}

// Other Operator Functions (non-member functions)
LLSortedPosInt  operator+ (const LLSortedPosInt &l1,
                              const LLSortedPosInt &l2)  {
    // combine l1 and l2 sorted, allow duplicates
    LLSortedPosInt sum = l1;
    
    // starts after sentinal
    NodePtr p2 = l2. head->next;
    
    while (p2 != nullptr) {
        sum.insert(p2->key);
        p2 = p2->next;
    }
    
    return sum;
}

LLSortedPosInt  operator- (const LLSortedPosInt &l1,
                              const LLSortedPosInt &l2)  {
    // remove all of l2 from l1, reclaim any storage
    LLSortedPosInt diff = l1;
    
    // start after sentinal
    NodePtr p2 = l2. head->next;
    
    while (p2 != nullptr) {
        diff.remove(p2->key);
        p2 = p2->next;
    }
    
    return diff;
}

// The following helper functions are provide to assist you in
// building the class--these helper functions are useful in
// several places among the functions you will write--take time
// to learn what they do

// insert() inserts an element in the linked list in sorted order
void LLSortedPosInt::insert(int key) {

   // setup pointers to walk the list
   NodePtr npp = head;
   NodePtr npc = head->next;

   // walk the list, searching until the given key value is exceeded
   while (npc != NULL && npc->key <= key) {
      npp = npc;
      npc = npc->next;
   }

   // insert the new value into the list
   npp->next = createNode(key, npc);
}

// remove() removes an element from the list (if it is present)
void LLSortedPosInt::remove(int key) {

   // negative values should not be stored in the list
   if (key <= 0) {
      return;
   }

   // setup pointers to walk the list
   NodePtr npp = head;
   NodePtr npc = head->next;

   // search the list until the end (if necessary)
   while (npc != NULL) {

      // if the key value is found, then splice this Node from the list and
      // reclaim its storage
      if (npc->key == key) {
         npp->next = npc->next;
         delete npc;
         break;
      }

      // walk the pointers to the next Node
      npp = npc;
      npc = npc->next;
   }
}
