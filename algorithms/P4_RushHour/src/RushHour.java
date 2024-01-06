import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;

public class RushHour {
    // To track of the vehicles on the board
    static Vehicle[] vehicles;

    // Keep track of the int -> color mapping we create
    static HashMap<Integer, String> colorMap = new HashMap<>();

    // Memoize so we don't repeat calculations 
    // Trick: we will store the parent position in the value like a linked list
    // - We can use this later to find the moves to an optimal solution
    static HashMap<String, String> visited = new HashMap<>();

    public enum Direction { NORTH, SOUTH, EAST, WEST }

    static boolean solutionFound = false;
    static Vehicle[][] finalBoard;


    public static void main(String[] args) throws FileNotFoundException {
        /*------------------------- Parse the input -------------------------*/
        Vehicle[][] startingBoard = new Vehicle[6][6];

        Scanner sc = new Scanner(new File("input.txt"));
        //Scanner sc = new Scanner(System.in);
        
        // First line contains the number of cars in the problem
        int numVehicles = sc.nextInt();
        vehicles = new Vehicle[numVehicles];
        
        // Map each color to a number
        int colorNum = 0;

        // Parse each vehicle according to this format:
        // Line 1 - car (length = 2) or truck (length = 3)
        // Line 2 - color
        // Line 3 - v (vertical) or h (horizontal)
        // Line 4 - row coordinate (decrement by 1)
        // Line 5 - column coordinate (decrement by 1)
        //          - The input gives locations on the range 1-6
        //          - Our board's indices go from 0-5
        for (int n = 0; n < numVehicles; n++) {
            int length = sc.next().equals("car") ? 2 : 3;   //line 1
            String color = sc.next();                                //line 2
            boolean vertical = sc.next().equals("v");       //line 3
            int i = sc.nextInt()-1;                                  //line 4
            int j = sc.nextInt()-1;                                  //line 5
            
            // Add vehicle to board
            Vehicle v = new Vehicle(length, vertical, colorNum);
            vehicles[n] = v;
            
            for (int l = 0; l < length; l++) {
                startingBoard[i][j] = v;
                
                if (vertical) i++;
                else          j++;
            }
            
            // Add mapping
            colorMap.put(colorNum, color);
            colorNum++;
        }


        /*---------------------- Find optimal solution ----------------------*/
        visited.put(boardToString(startingBoard), "start");
        
        // Check if it's a one-mover
        checkSolutionFound(startingBoard);
        if (solutionFound) {
            printSolution(finalBoard);
            return;
        }

        // We will be using breadth-first search
        Queue<Vehicle[][]> searchQueue = new LinkedList<>();
        searchQueue.add(startingBoard);

        while (!searchQueue.isEmpty()) {
            Vehicle[][] board = searchQueue.remove();

            // Find adjancencies and add them to the search queue
            for (Vehicle v : vehicles) {
                Vector<Vehicle[][]> moves = findMoves(board, v);
                searchQueue.addAll(moves);

                if (solutionFound) {
                    printSolution(finalBoard);
                    return;
                }
            }
        }

        System.out.println("Emptied search queue without finding solution.");
        System.out.println("Input a solvable board.");
    }

    /**Returns a vector of boards of all moves that can be made with vehicle v
     */
    private static Vector<Vehicle[][]> findMoves(Vehicle[][] board, Vehicle v) {
        // Find all moves for the vehicle v on this board and return the boards in a collection (maybe a vector will work)
        Vector<Vehicle[][]> boardMoves = new Vector<>();

        if (v.vertical) {   // Check moving north and south
            boardMoves.addAll(returnBoards(board, v, Direction.NORTH));
            boardMoves.addAll(returnBoards(board, v, Direction.SOUTH));

        } else {            // Check moving east and west
            boardMoves.addAll(returnBoards(board, v, Direction.EAST));
            boardMoves.addAll(returnBoards(board, v, Direction.WEST));
        }

        return boardMoves;
    }

    /**Returns a vector of boards off all moves that can be made with vehicle v
     * in a specified direction
     * This method passes a cloned board to the moveVehicle method
     */
    private static Vector<Vehicle[][]> returnBoards(Vehicle[][] board, 
            Vehicle v, Direction dir) {
        Vector<Vehicle[][]> boards = new Vector<>();

        int moves = movesAvailable(board, v, dir);

        for (int amt = 1; amt <= moves; amt++) {
            // Clone board before moving vehicle
            Vehicle[][] newBoard = new Vehicle[6][6];
            for (int i = 0; i < board.length; i++) {
                newBoard[i] = board[i].clone();
            }
            
            // Memoize and add the board to the vector if 
            // - it isn't a solution and
            // - it hasn't been visited
            moveVehicle(newBoard, v, dir, amt);

            if (!visited.containsKey(boardToString(newBoard))) {
                visited.put(boardToString(newBoard), boardToString(board));
    
                checkSolutionFound(newBoard);

                boards.add(newBoard);
            }
        }

        return boards;
    }

    /**Returns the amount of moves a vehicle can make in a given direction
     */
    private static int movesAvailable(Vehicle[][] board, Vehicle v, Direction dir) {
        Coor location = findVehicle(board, v);
        int numMoves = 0;
        
        // Adjust location to be the first search location
        // You have to adjust where you start for SOUTH and WEST
        // - the head location is specified as the top- or left-most square
        switch (dir) {
            case NORTH: location.i -= 1;        break;
            case SOUTH: location.i += v.length; break;
            case WEST:  location.j -= 1;        break;
            case EAST:  location.j += v.length; break;
        }

        // Check bounds
        while (location.i >= 0 && location.i < 6 && 
               location.j >= 0 && location.j < 6 &&
               board[location.i][location.j] == null) {
            
            numMoves++;
            
            // Increment search location
            switch (dir) {
                case NORTH: location.i--; break;
                case SOUTH: location.i++; break;
                case WEST:  location.j--; break;
                case EAST:  location.j++; break;
            }
        }

        return numMoves;
    }

    /**Returns a board after moving a vehicle an amount in a given direction
     * This method modifies the board it receives in place
     */
    private static void moveVehicle(Vehicle[][] board, Vehicle v, Direction dir, int amt) {
        // Remove current vehicle from the board
        Coor currLocation = findVehicle(board, v);
        int i = currLocation.i;
        int j = currLocation.j;
        
        for (int n = 0; n < v.length; n++) {
            board[i][j] = null;
            
            if (v.vertical) i++;
            else            j++;
        }
        
        // Set the next location and ...
        Coor nextLocation = currLocation;

        // ... change coordinate based on amount
        switch (dir) {
            case NORTH: nextLocation.i -= amt; break;
            case SOUTH: nextLocation.i += amt; break;
            case WEST:  nextLocation.j -= amt; break;
            case EAST:  nextLocation.j += amt; break;
        }

        // Add it back in the new position
        i = nextLocation.i;
        j = nextLocation.j;
        
        for (int n = 0; n < v.length; n++) {
            board[i][j] = v;

            if (v.vertical) i++;
            else            j++;
        }
    }

    /**Checks if a board gives a solution by if the red car's path is clear
     */
    private static void checkSolutionFound(Vehicle[][] board) {
        Vehicle redCar = vehicles[0];   //first car in input is the red car
        Coor location = findVehicle(board, redCar);

        // Move search location to first square right of the car
        int i = location.i;
        int j = location.j + redCar.length;
        
        // Check if the red car's path is clear
        while (j < 6) {
            if (board[i][j] != null) return;

            j++;
        }
        // Solution has been found if execution gets past the while loop
        
        // Move car to exit and call printSolution
        Vehicle[][] lastBoard = new Vehicle[6][6];
        for (i = 0; i < board.length; i++) {
            lastBoard[i] = board[i].clone();
        }
        moveVehicle(lastBoard, redCar, Direction.EAST, 4-location.j);
        
        // Add to visited hash map
        visited.put(boardToString(lastBoard), boardToString(board));
        
        solutionFound = true;
        finalBoard = lastBoard;
    }

    /**Prints out the solution according to the format required of us
     */
    private static void printSolution(Vehicle[][] finalBoard) {
        // Use the visited hash map find the solution path
        Vector<Vehicle[][]> solutionPath = new Vector<>();

        solutionPath.add(finalBoard);

        String prev = visited.get(boardToString(finalBoard));

        while (!prev.equals("start")) {
            // Add the board to the beggining of the list
            solutionPath.add(0, stringToBoard(prev));

            prev = visited.get(prev);
        }

        // Print out first line, giving the amount of moves
        // - It is one less than the size. The moves are like edges
        //   between nodes (boards)
        int boards = solutionPath.size();
        
        if (boards > 2) {
            System.out.println(boards-1 + " moves");
        } else {
            System.out.println(boards-1 + " move");
        }

        // Print out the moves
        // We went overboard here
        for (int i = 0; i < boards-1; i++) {
            System.out.println(
                compareMove(
                    solutionPath.elementAt(i), 
                    solutionPath.elementAt(i+1)
                )
            );
        }
    }
    
    /**Finds the location of a vehicle
     */
    private static Coor findVehicle(Vehicle[][] board, Vehicle v) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++ ){
                if (board[i][j] != null && board[i][j].colorNum == v.colorNum) {
                    return new Coor(i,j);
                }
            }
        }

        // This should never be called
        return new Coor(-1, -1);
    }

    /**Returns a string describing the move from the previous to current position
     */
    private static String compareMove(Vehicle[][] prev, Vehicle[][] curr) {
        // Search for first difference between the boards. This is the head
        int i = 0;
        int j = 0;
        searchloop:  //we label the outer loop so we can break out of it
        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) {
                if (prev[i][j] != curr[i][j]) {
                    break searchloop; //break out of both loops
                }
            }
        }
        
        // Get the vehicle and use it to find its locations
        Vehicle v = (curr[i][j] != null) ? curr[i][j] : prev[i][j];
        
        Coor currLoc = findVehicle(curr, v);
        Coor prevLoc = findVehicle(prev, v);
        
        // Find amount of spaces moved. Use its sign to determine direction
        int spaces;
        Direction dir;
        if (v.vertical) {
            spaces = currLoc.i - prevLoc.i;
            dir = (spaces < 0) ? Direction.NORTH : Direction.SOUTH;

        } else {
            spaces = currLoc.j - prevLoc.j;
            dir = (spaces < 0) ? Direction.WEST : Direction.EAST;
        }
        spaces = Math.abs(spaces);

        // Turn the direction it moved into a string
        String direction = "";
        switch (dir) {
            case NORTH: direction = "U"; break;
            case SOUTH: direction = "D"; break;
            case EAST:  direction = "R"; break;
            case WEST:  direction = "L"; break;
        }
        
        // Write to a string
        return colorMap.get(v.colorNum) + " " + spaces + " " + direction;
    }
    
    /**Returns a string that represents the board
     * A space is encoded with the vehicle's number or a '.' for an empty spot
     */
    private static String boardToString(Vehicle[][] board) {
        StringBuilder rep = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == null) {
                    rep.append(".");
                } else {
                    rep.append(String.valueOf(board[i][j].colorNum));
                }
            }
        }

        return rep.toString();
    }

    /**Converts a representative string back into a board
     */
    private static Vehicle[][] stringToBoard(String s) {
        // Put vehicles into a hash map for linear-time look-up
        HashMap<Integer, Vehicle> vehicleMap = new HashMap<>();
        for (Vehicle v : vehicles) {
            vehicleMap.put(v.colorNum, v);
        }

        Vehicle[][] board = new Vehicle[6][6];

        // This tracks the index of the string
        int idx = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                char c = s.charAt(idx);

                if (c != '.') {
                    int vehicleNum = c - '0';
                    board[i][j] = vehicleMap.get(vehicleNum);
                }

                idx++;
            }
        }

        return board;
    }
}