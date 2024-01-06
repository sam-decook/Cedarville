import java.util.HashMap;
import java.util.Vector;

public class Sign {
    int fromIntersection;
    int toIntersection;
    double distance;
    HashMap<Integer, String> distanceCityPair;

    public Sign(int fromIntersection, int toIntersection, double distance) {
        this.fromIntersection = fromIntersection;
        this.toIntersection = toIntersection;
        this.distance = distance;
        distanceCityPair = new HashMap<>();
    }

    public void addDistanceCityPair(double distance, String name) {
        // Round distance to nearest whole. 0.5 rounds up
        int rounded = (int) distance;

        if (distance - rounded >= 0.5) rounded++;

        distanceCityPair.put(rounded, String.format("%-19s", name));
    }

    public void print() {
        Vector<Integer> distances = new Vector<>();

        // Collect the keys and sort them
        distanceCityPair.forEach((k, v) -> distances.add(k));

        distances.sort(null);

        // Print out in sorted order
        for (int i = 0; i < distances.size(); i++) {
            // Check if there is a tie
            if (i+1 < distances.size() && distances.get(i) == distances.get(i+1)) {
                String city1 = distanceCityPair.get(distances.get(i));
                String city2 = distanceCityPair.get(distances.get(i+1));

                // Sort cities alphabetically
                if (city1.compareTo(city2) < 0) { //city1 goes first
                    System.out.println(city1 + " " + distances.get(i));
                    System.out.println(city2 + " " + distances.get(i+1));
                } else {
                    System.out.println(city2 + " " + distances.get(i+1));
                    System.out.println(city1 + " " + distances.get(i));
                }

                i = i + 1;
            }
            else {
                // Print normally
                int d = distances.get(i);
                System.out.println(distanceCityPair.get(d) + " " + d);
            }
        }
    }
}
