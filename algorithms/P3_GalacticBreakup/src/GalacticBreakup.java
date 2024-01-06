import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class GalacticBreakup {
    // Push each line (which describes a monarchy) onto the stack
    static Stack<String> monarchies = new Stack<>();
    
    // 3D array to represent the empire. Used for checking around
    //   each dominion to see if it can be added to another set
    static DisjointSet[][][] empire;

    static int monthsDisconnected = 0;
    static int numSets = 0;


    public static void main(String[] args) throws FileNotFoundException {
        /*--------------------------- Parse input ---------------------------*/
        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new File("input.txt"));
        
        // First three numbers of first line are 3D dimensions of empire
        int[] dim = new int[3];
        for (int i = 0; i < 3; i++) {
            dim[i] = sc.nextInt();
        }

        empire = new DisjointSet[dim[0]][dim[1]][dim[2]];
        
        // Fourth (last) number is amount of monarchies in the empire
        int numMonarchies = sc.nextInt();
        sc.nextLine(); //scanner is at the end of the first line, move to next
        
        for (int i = 0; i < numMonarchies; i++) {
            monarchies.push(sc.nextLine());
        }
        

        /*-------------------------- Solve problem --------------------------*/
        while (!monarchies.empty()) {
            // Get line (describes a monarchy) from top of the stack
            sc = new Scanner(monarchies.pop());

            // First number tells us the number of dominions in the monarchy
            int numDominions = sc.nextInt();

            for (int i = 0; i < numDominions; i++) {
                int dominion = sc.nextInt();

                // This takes the value and maps it to 3D coordinates
                Coor c = new Coor(dominion, dim);

                // Create a new disjoint set (DS) with it and add to map
                DisjointSet current = new DisjointSet(dominion);
                empire[c.n][c.m][c.k] = current;
                numSets++;

                // Do the search around the dominion
                if (c.n > 0)        { unionTwo(current, empire[c.n-1][c.m][c.k]); }
                if (c.n+1 < dim[0]) { unionTwo(current, empire[c.n+1][c.m][c.k]); }

                if (c.m > 0)        { unionTwo(current, empire[c.n][c.m-1][c.k]); }
                if (c.m+1 < dim[1]) { unionTwo(current, empire[c.n][c.m+1][c.k]); }

                if (c.k > 0)        { unionTwo(current, empire[c.n][c.m][c.k-1]); }
                if (c.k+1 < dim[2]) { unionTwo(current, empire[c.n][c.m][c.k+1]); }
            }

            // After the monarchy is done, check if it is connected (only 1 disjoint set)
            if (numSets > 1) monthsDisconnected++;
        }

        System.out.println(monthsDisconnected);
    }

    private static void unionTwo(DisjointSet first, DisjointSet second) {
        // Check if the node exists
        if (second == null) return;

        // Find the representatives
        first = first.findSet();
        second = second.findSet();

        // If they are in the same set, return early, don't union the same set
        if (first == second) return;

        // Union the two DSs and decrement the counter
        first.union(second);
        numSets--;
    }
}