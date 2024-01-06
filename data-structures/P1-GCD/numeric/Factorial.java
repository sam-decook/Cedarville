package numeric;

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
	public static void main(String[] args) {
		if (args.length == 1) {
			int x = Integer.parseInt(args[0]);
			System.out.println("Factorial of " + x + " is: " + computeFactorial(x));

		} else {
			System.out.println("Error: Wrong number of arguments, only 1 is required");
		}
	}

	public static double computeFactorial(int x) {
		if (x >= 0) {
			int output = 1; // Both 0! and 1! are 1

			for (int i = 2; i <= x; i++) {
				output *= i;
			}

			return output;
		} else {
			System.out.println("Error: Cannot compute factorial of a negative number");
			return -1;
		}
	}
}
