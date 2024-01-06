import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MakingChange {
    public static final int BOTTOM_UP = 1;
    public static final int RECURSIVE = 2;
    public static final int MEMOIZATION = 3;

    private static int[] denominations;
    private static int[] problems;
    private static int[][] table;

    public static int[][] makeChange(int type) {
        if (type == BOTTOM_UP) {
            return bottomUp();
        } else if (type == RECURSIVE) {
            return recursive();
        } else if (type == MEMOIZATION) {
            return memoization();
        } else {
            return new int[][] {{-1}};
        }
    }

    private static int[][] bottomUp() {
        // Find largest problem to solve to set up the memoization table
        //   the 0th position is empty to improve indexing
        int max = 0;
        for (int problem : problems) {
            if (problem > max) max = problem;
        }

        table = new int[max+1][denominations.length];

        // Pre-fill solution for 1 cent
        table[1][0] = 1;

        // Fill in the table of solutions
        for (int n = 1; n <= max; n++) {
            solve(n, true);
        }

        // Gather the solutions to the problems
        int[][] solutions = new int[problems.length][denominations.length];
        for (int i = 0; i < solutions.length; i++) {
            int problem = problems[i];
            solutions[i] = table[problem];
        }
        
        return solutions;
    }

    private static int[][] recursive() {
        // Set up array for solutions
        int[][] solutions = new int[problems.length][denominations.length];

        // Recursively solve each problem
        for (int i = 0; i < problems.length; i++) {
            solutions[i] = solve(problems[i], false);
        }

        return solutions;
    }

    private static int[][] memoization() {
        // Find largest problem to solve to set up the memoization table
        //   the 0th position is empty to improve indexing
        int max = 0;
        for (int problem : problems) {
            if (problem > max) max = problem;
        }

        table = new int[max+1][denominations.length];

        // Pre-fill solution for 1 cent
        table[1][0] = 1;

        // Set up array for solutions
        int[][] solutions = new int[problems.length][denominations.length];

        // Recursively solve each problem and memoize
        for (int i = 0; i < problems.length; i++) {
            solutions[i] = solve(problems[i], true);
        }

        return solutions;
    }

    private static int[] solve(int n, boolean memoized) {
        // 0 takes 0 coins, return a zeroed array
        if (n == 0) return new int[denominations.length];

        // If the solution has been computed, just return it
        // Add isEmpty method
        if (memoized && hasSolution(table[n])) return table[n].clone();

        // Find which denomination produces the best solution
        int[] best = new int[denominations.length];
        Arrays.fill(best, 1000);

        for (int i = 0; i < denominations.length; i++) {
            // Find how many coins it takes when you take out the denomination
            int r = n - denominations[i];

            if (r < 0) break;

            int[] solution = solve(r, memoized);
            solution[i]++;

            // Only keep the best solution
            if (numCoins(solution) < numCoins(best)) {
                best = solution;
            }
        }
        
        if (memoized) { //store the solution
            table[n] = best.clone();
        }

        return best;
    }

    private static boolean hasSolution(int[] solution) {
        for (int num : solution) {
            if (num != 0) return true;
        }

        return false;
    }

    private static int numCoins(int[] solution) {
        int num = 0;
        for (int coins : solution) {
            num += coins;
        }

        return num;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Parse input
        //Scanner input = new Scanner(System.in);
        Scanner input = new Scanner(new File("input.txt"));

        //   Denominations (input contains number denoting amt of denoms)
        int numDenominations = input.nextInt();
        denominations = new int[numDenominations];

        for (int i = 0; i < numDenominations; i++) {
            denominations[i] = input.nextInt();
        }


        //   Problems (input contains number denoting amt of problems)
        int numProblems = input.nextInt();
        problems = new int[numProblems];

        for (int i = 0; i < numProblems; i++) {
            problems[i] = input.nextInt();
        }

        // Run different types
        int[][] solutions = makeChange(MEMOIZATION);

        // Print out solutions
        for (int i = 0; i < solutions.length; i++) {
            System.out.print(problems[i] + " cents =");

            for (int j = denominations.length-1; j >= 0; j--) {
                if (solutions[i][j] == 0) continue;

                System.out.print(" " + denominations[j] + ":" + solutions[i][j]);
            }
            System.out.println();
        }


        // Instrument each type
        int[] types = {BOTTOM_UP, MEMOIZATION, RECURSIVE};
        problems = new int[] {70};

        long start, finish;
        for (int type : types) {
            start = System.nanoTime();
            makeChange(type);
            finish = System.nanoTime();
            System.out.println("Type " + type + " time: " + (finish-start));
        }
    }
}