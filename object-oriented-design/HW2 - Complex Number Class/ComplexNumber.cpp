//Author:  Sam DeCook
//Date:    Feb 6 2020
//Purpose: Class implementation for ComplexNumber

#include <cstdio>
#include <cstring>
#include <string>
#include "ComplexNumber.h"

using namespace std;

ComplexNumber::ComplexNumber(float real, float imag) {
   r = real;
   i = imag;
}

ComplexNumber::ComplexNumber(const ComplexNumber &rhs) {
   r = rhs.r;
   i = rhs.i;
}

ComplexNumber::ComplexNumber(const char* str) {
    const char* iLocation = strchr(str, 'i');
    float x, y;

    int n = sscanf(str, "%f%f", &x, &y);

    if (n == 2) {
        r = x;
        i = y;

    } else if (n == 1) {
        if (iLocation == nullptr) { //Number is real
            r = x;
            i = 0;
        } else {                    //Check if i is implicit
            if ( *(iLocation - 1) == '+') {
                r = x;
                i = 1;
            } else if ( *(iLocation - 1) == '-') {
                r = x;
                i = -1;
            } else {                //We only have a simple imaginary in float x
                r = 0;
                i = x;
            }
        }

    } else {                        //n == 0, sscanf didn't find any numbers
        if (strlen(str) == 1) {     //Just the letter i
            r = 0;
            i = 1;
        } else {                    //The letter i plus signage
            if ( *(iLocation - 1) == '+') {
                r = 0;
                i = 1;
            } else {                //Must be negative
                r = 0;
                i = -1;
            }
        }
    }
}

ComplexNumber& ComplexNumber::operator= (const ComplexNumber &rhs){
   r = rhs.r;
   i = rhs.i;

   return *this;
}

bool ComplexNumber::operator== (const ComplexNumber &rhs) const {
   return (r == rhs.r && i == rhs.i);
}

/* Definition of complex multiplication
    Let x = a + bi and 
        y = c + di
    x * y = (ac - bd) + (ad + bc)i
*/
ComplexNumber ComplexNumber::operator* (const ComplexNumber &rhs) {
    ComplexNumber result;

    result.r = (r * rhs.r) - (i * rhs.i);
    result.i = (r * rhs.i) + (i * rhs.r);

    return result;
}

ComplexNumber operator+ (const ComplexNumber &a, const ComplexNumber &b) {
   ComplexNumber result;

   result.r = a.r + b.r;
   result.i = a.i + b.i;

   return result;
}

ComplexNumber operator- (const ComplexNumber &a, const ComplexNumber &b) {
   ComplexNumber result;

   result.r = a.r - b.r;
   result.i = a.i - b.i;

   return result;
}

ostream& operator<< (ostream &out, const ComplexNumber &b) {

   bool rPrinted = false;

   if (b.r != 0 || (b.r == 0 && b.i == 0)) {
      out << b.r;
      rPrinted = true;
   }
   
   if (b.i > 0) {
      if (rPrinted) {
         out << "+";
      }
      if (b.i != 1) {
         out << b.i;
      }
      out << "i";
   } else if (b.i < 0) {
      if (b.i == -1) {
         out << "-";
      } else {
         out << b.i;
      }
      out << "i";
   }

   return out;
}

istream& operator>>(istream &in, ComplexNumber &b) {
    string input;
    in >> input;
    b = ComplexNumber(input.c_str());
    return in;
}