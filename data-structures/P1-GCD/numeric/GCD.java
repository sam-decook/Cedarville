package numeric;

/**
 * This class provides a method for computing the Greatest Common Denominator
 * (GCD) of two integers
 *
 * @author Sam DeCook
 * @version 1.0
 *          File: GCD.java
 *          Created: Sep 2021
 *          ©Copyright Sam DeCook. All rights reserved
 *
 *          Description: This class provides a computeGCD method which computes
 *          the GCD
 *          of two integers. It also contains a main which allows for direct
 *          calls from the command line.
 *          GCD doesn't inherit from any other class nor is intended to be
 *          inherited from.
 */

public class GCD {
	public static void main(String[] args) {
		if (args.length == 2) {
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			System.out.println("GCD of " + x + " and " + y + " is: " + computeGCD(x, y));

		} else {
			System.out.println("Error: Wrong number of arguments, 2 are required");
		}
	}

	/**
	 * Computes greatest common denominator
	 * 
	 * @param int x, int y The integers you want to compute the GCD of
	 * @return double the GCD
	 */
	public static int computeGCD(int x, int y) {
		// Make x and y positive
		if (x < 0) {
			x = -x;
		}
		if (y < 0) {
			y = -y;
		}

		if (y == 0) {
			return x;

		} else {
			while (y != 0) {
				// Perform GCD(x, y) = GCD(y, x mod y)
				int a = x; // temp. var to store x
				x = y;
				y = a % y;
			}

			return x;
		}
	}
}
