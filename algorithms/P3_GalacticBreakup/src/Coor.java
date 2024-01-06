public class Coor {
    int n;
    int m;
    int k;

    public Coor(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }

    public Coor(int num, int[] dim) {
        n = num % dim[0];
        m = (num / dim[0]) % dim[1];
        k = num / (dim[0] * dim[1]);
    }
}