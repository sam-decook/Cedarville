package com.mycompany.project5;

/**
 * This class provides a Location object to store relevant maze information
 *
 * @author Sam DeCook
 * @version 1.0
 *          Created October 2021
 * 
 *          Description: This class stores the type, distance in the path,
 *          location in
 *          the maze array, and the location it was discovered from (this allows
 *          it to
 *          trace the solution once the target is reached)
 */
public class Location {
    private char type;
    private int distance;
    private Coord location;
    private Location discoveredFrom;

    /**
     * Constructs an instance of the Location class
     * 
     * @param t    type
     * @param dist distance from start
     * @param loc  coordinate location in array
     * @param disc location discovered from
     */
    public Location(char t, int dist, Coord loc, Location disc) {
        type = t;
        distance = dist;
        location = loc;
        discoveredFrom = disc;
    }

    /**
     * Returns a string of the location's location
     *
     * @return <x y>
     */
    @Override
    public String toString() {
        return "<" + location.y + " " + location.x + ">";
    }

    public char getType() {
        return type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int d) {
        distance = d;
    }

    public Coord getLocation() {
        return location;
    }

    public Location getDiscoveredFrom() {
        return discoveredFrom;
    }

    public void setDiscoveredFrom(Location l) {
        discoveredFrom = l;
    }
}
