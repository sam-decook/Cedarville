package com.mycompany.project5;

/**
 * This class provides a Coord (coordinate) object to store a location (x, y)
 *
 * @author Sam DeCook
 * @version 1.0
 *          Created October 2021
 * 
 *          Description: This class provides an abstraction for coordinates,
 *          bringing
 *          them together into one location for cleaner more readable code
 */
public class Coord {
    public int x;
    public int y;

    /**
     * Default constructor. Initializes x and y to 0
     */
    public Coord() {
        this(0, 0);
    }

    /**
     * Constructs an instance of Coord with user defined location
     * 
     * @param x
     * @param y
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
