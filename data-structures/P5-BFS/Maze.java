package com.mycompany.project5;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class solves a maze formatted correctly
 * 
 * @author Sam DeCook
 * @version 1.0
 *          Created October 2021
 * 
 *          Description: This class has a main method which creates an array to
 *          represent
 *          the maze and solves that maze and two helper functions to factor out
 *          repeated
 *          code and perform a recursive print
 */
public class Maze {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rows = sc.nextInt();
        int cols = sc.nextInt();
        sc.nextLine(); // To move past (now) empty line

        Location[][] maze = new Location[rows][cols];

        // Parsing maze to create 2D array of Location objects
        LinkedList<Location> searchList = new LinkedList();

        for (int i = 0; i < rows; i++) {
            String row = sc.nextLine();

            for (int j = 0; j < cols; j++) {
                /*
                 * For Coord, j is x, i is y because you are iterating through the top row with
                 * j
                 */
                maze[i][j] = new Location(row.charAt(j), -1,
                        new Coord(j, i), null);

                if (row.charAt(j) == 'S') {
                    maze[i][j].setDistance(0);
                    searchList.add(maze[i][j]); // Where the search starts
                }
            }
        }

        // Search through array
        Location target = null;

        while (!searchList.isEmpty() && target == null) {
            Location curr = searchList.removeFirst();
            Coord coor = curr.getLocation();

            // Coordinate system is inverted between Coord and maze
            // Check search direction is within maze and target isn't found
            if (coor.x - 1 >= 0 && target == null) { // Search left
                target = search(curr, maze[coor.y][coor.x - 1], searchList);
            }
            if (coor.x + 1 < cols && target == null) { // Search right
                target = search(curr, maze[coor.y][coor.x + 1], searchList);
            }
            if (coor.y + 1 < rows && target == null) { // Search up
                target = search(curr, maze[coor.y + 1][coor.x], searchList);
            }
            if (coor.y - 1 >= 0 && target == null) { // Search down
                target = search(curr, maze[coor.y - 1][coor.x], searchList);
            }
        }

        // Print out path
        if (target == null) {
            System.out.println("Maze not solvable.");
        } else {
            int dist = target.getDistance();
            printSolution(target);
            System.out.println("Total distance = " + dist);
        }
    }

    private static Location search(Location curr, Location search,
            LinkedList list) {
        // First check if the target was found
        if (search.getType() == 'T') {
            search.setDistance(curr.getDistance() + 1);
            search.setDiscoveredFrom(curr);
            return search;
            // If not, check if location is open and undiscovered
        } else if (search.getType() == '.' && search.getDistance() == -1) {
            search.setDistance(curr.getDistance() + 1);
            search.setDiscoveredFrom(curr);
            list.add(search);
        }
        return null;

    }

    private static void printSolution(Location n) {
        // Recurse until you reach the start location
        if (n.getDiscoveredFrom() != null) {
            printSolution(n.getDiscoveredFrom());
        }

        // Print out maze forwards
        System.out.println(n.toString());
    }
}
