//Author:  Sam DeCook
//Date:    Feb 6 2020
//Purpose: Class definition for ComplexNumber

#include <iostream>
using namespace std;

class ComplexNumber {
 public:
   // constructors (copy constructor not necessary, included for illustration)
   ComplexNumber(float real = 0.0, float imag = 0.0);
   ComplexNumber(const ComplexNumber &rhs);
   
   ComplexNumber(const char* str);

   // assignment operator (not necessary, included for illustration)
   ComplexNumber& operator=(const ComplexNumber &rhs);

   // the multiplication operation is a member function
   ComplexNumber operator*(const ComplexNumber &rhs);

   // equality operator needed for unit test in HW2
   bool operator==(const ComplexNumber &rhs) const;

   // friend operator functions
   friend ComplexNumber operator+(const ComplexNumber &a, const ComplexNumber &b);
   friend ComplexNumber operator-(const ComplexNumber &a, const ComplexNumber &b);
   friend ostream& operator<<(ostream &out, const ComplexNumber &b);
   friend istream& operator>>(istream &in, ComplexNumber &b);

   //Getters
   double getReal() const { return r; }

   double getImag() const { return i; }

 private:
   float r;
   float i;
};