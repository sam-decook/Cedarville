package com.mycompany.project2;

/**
 * This class provides a method for computing the factorial of an integer
 * 
 * @author Sam DeCook
 * @version 1.0
 *          File: Factorial.java
 *          Created: Sep 2021
 *          ©Copyright Sam DeCook. All rights reserved
 * 
 *          Description: This class provides a computeFactorial method which
 *          computes the factorial
 *          of an integer. It also contains a main which allows for direct calls
 *          from the command line.
 *          Factorial doesn't inherit from any other class nor is intended to be
 *          inherited from.
 */

public class Factorial {
    public static double computeFactorial(int x) {
        if (x >= 0) {
            int output = 1; // Both 0! and 1! are 1

            for (int i = 2; i <= x; i++) {
                output *= i;
            }

            return output;

        } else {
            throw new NegativeNumberException("Cannot compute factorial of a negative number");
        }
    }
}
