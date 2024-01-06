public class DisjointSet {
    /*-------------------- Fields ---------------------*/
    DisjointSet parent;
    int value;
    int rank;

     /*---------------------Constructor----------------*/
     public DisjointSet(int value) {
        parent = this;
        this.value = value;
        rank = 0;
     }

    /*-------------------- Methods --------------------*/
    public DisjointSet findSet() {
        // Recursively set each node's parent to the representative node
        // This compresses the tree and gives future O(1) operations
        if (this != parent) {
            parent = parent.findSet();
        }
        return parent;
    }

    public DisjointSet union(DisjointSet other) {
        // The set with the smaller rank is appended
        if (other.rank > this.rank) {
            this.parent = other;
            other.rank++;
            return other;
        } 
        else {
            other.parent = this;
            this.rank++;
            return this;
        }
    }
}