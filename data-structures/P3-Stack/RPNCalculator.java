package com.mycompany.project3;

import java.util.Scanner;
import java.util.Stack;

/**
 * This class implements a Reverse Polish Notation calculator
 *
 * @author samdecook
 * @version 1.0
 *          File: RPNCalculator.java
 *          Created September 2021
 * 
 *          Description: Reverse Polish Notation receives two numbers and then
 *          takes the
 *          next operator and performs it on those two numbers.
 *          Ex: 2 + 3 is formatted 2 3 + in RPN
 */
public class RPNCalculator {

    /**
     * Performs arithmetic calculation on a string formatted in Reverse Polish
     * Notation
     * 
     * @param s The RPN string to be calculated
     * @return double
     * @throws InvalidRPNStringException for errors
     *                                   1. Dividing by 0
     *                                   2. Character other than double and +,-,*,/
     *                                   3. Not enough numbers to do operation
     *                                   4. More than one number in the stack at the
     *                                   end
     */
    public static double calculate(String s) {
        Scanner sc = new Scanner(s);
        Stack<Double> stack = new Stack<>();

        while (sc.hasNext()) {
            // If the next token isn't a double, it's the operator or an error
            if (sc.hasNextDouble()) {
                Double d = sc.nextDouble();
                stack.push(d);

            } else {
                String op = sc.next();

                // Initial error checking
                // The string must be an operand
                // There must be enough numbers to do the operation
                if (!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))) {
                    throw new InvalidRPNStringException("Unable to parse string");
                } else if (stack.size() < 2) {
                    throw new InvalidRPNStringException("Insufficient numbers for operation");
                }

                double n1 = stack.pop();
                double n2 = stack.pop();

                switch (op) {
                    case "+":
                        stack.push(n2 + n1);
                        break;
                    case "-":
                        stack.push(n2 - n1);
                        break;
                    case "*":
                        stack.push(n2 * n1);
                        break;
                    case "/":
                        if (n1 == 0.0) {
                            throw new InvalidRPNStringException("Cannot divide by 0");
                        }
                        stack.push(n2 / n1);
                        break;
                }
            }
        }

        // If stack doesn't have exactly one double left, the operation didn't finish
        if (stack.size() != 1) {
            throw new InvalidRPNStringException("Improper number of arguments");
        }
        return stack.pop();
    }
}
