import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

/*Predecessor matrix:
 * if i == j or if no path exists, it is null
 * else, it is the predecessor of j on some sortest path from i
 */

public class RoadsScholar {
    static double[][] weight;   //distance of each road
    static double[][] shortestPath;
    static int[][] predecessor;

    public static void main(String[] args) throws FileNotFoundException {
        /* -------------------------- Parse Input -------------------------- */
        Scanner sc = new Scanner(new File("input.txt"));
        //Scanner sc = new Scanner(System.in);

        int numIntersections = sc.nextInt();
        int numRoads = sc.nextInt();
        int numCities = sc.nextInt();
        
        // Parse roads
        weight = new double[numIntersections][numIntersections];
        predecessor = new int[numIntersections][numIntersections];

        // Set all weights to infinity
        for (int i = 0; i < numIntersections; i++) {
            for (int j = 0; j < numIntersections; j++) {
                weight[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        // Set all predecessors to -1, indicating null
        for (int i = 0; i < numIntersections; i++) {
            for (int j = 0; j < numIntersections; j++) {
                predecessor[i][j] = -1;
            }
        }
        
        for (int i = 0; i < numRoads; i++) {
            int intersection1 = sc.nextInt();
            int intersection2 = sc.nextInt();
            double dist = sc.nextDouble();

            weight[intersection1][intersection2] = dist;
            weight[intersection2][intersection1] = dist;
            predecessor[intersection1][intersection2] = intersection1;
            predecessor[intersection2][intersection1] = intersection2;
        }

        // Parse cities
        HashMap<Integer, String> cities = new HashMap<>();

        for (int i = 0; i < numCities; i++) {
            int intersectionNum = sc.nextInt();
            String cityName = sc.next();

            cities.put(intersectionNum, cityName);
        }

        // Parse signs
        int numSigns = sc.nextInt();
        Sign[] signs = new Sign[numSigns];

        for (int i = 0; i < numSigns; i++) {
            int fromIntersection = sc.nextInt();
            int toIntersection = sc.nextInt();
            double dist = sc.nextDouble(); //the distance along the road the sign is located

            signs[i] = new Sign(fromIntersection, toIntersection, dist);
        }


        /* ----------------- Solve All-Pairs Shortest-Path ----------------- */
        // Not the most efficient, but easier to code up than multiple SSSPs
        shortestPath = floydWarshall(weight, predecessor);

        /* --------------------- Solve and print signs --------------------- */
        for (Sign sign : signs) {
            for (int city : cities.keySet()) {
                // Check if shortest path to the city goes through the road the sign is on
                if (roadOnShortest(sign, city)) {
                    // Add distance to sign
                    double dist = shortestPath[sign.fromIntersection][city] - sign.distance;
                    sign.addDistanceCityPair(dist, cities.get(city));
                }
            }
        }
        
        // Print out solution
        for (int i = 0; i < signs.length; i++) {
            signs[i].print();
            if (i+1 < signs.length) {
                System.out.println();
            }
        }
    }

    private static boolean roadOnShortest(Sign sign, int city) {
        int fromInt = sign.fromIntersection;
        int toInt = sign.toIntersection;

        if (fromInt == city) return false;

        //checking if on shortest path
        if (city == toInt) return true;
        
        int pred = predecessor[fromInt][city];
        return roadOnShortest(sign, pred);
    }

    private static double[][] floydWarshall(double[][] weight, int[][] predecessor) {
        int n = weight.length;

        // Clone weights
        double[][] best = new double[n][n];
        for (int i = 0; i < n; i++) {
            best[i] = weight[i].clone();
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Find the weight if you go through vertex k
                    double throughInter = best[i][k] + best[k][j];
                    
                    // Update if it is smaller
                    if (best[i][j] > throughInter) {
                        best[i][j] = throughInter;
                        predecessor[i][j] = predecessor[k][j];
                    }
                }
            }
        }

        return best;
    }
}