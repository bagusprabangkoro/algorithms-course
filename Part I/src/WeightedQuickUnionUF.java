import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF {
    private int[] nodes;
    private int[] sz;
    public WeightedQuickUnionUF(int N) {
        nodes = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = i;
            sz[i] = 1;
        }
    }

    private int findRoot(int p) {
        if (nodes[p] == p) {
            return p;
        }
        // path compression
        nodes[p] = nodes[nodes[p]];
        return findRoot(nodes[p]);
    }

    public boolean connected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    public void union(int p, int q) {
        int rootP = findRoot(p);
        int rootQ = findRoot(q);
        if (rootP == rootQ) {
            return;
        }
        if (sz[rootP] < sz[rootQ]) {
            nodes[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        } else {
            nodes[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
    }

    public static void main(String[] args) {
        WeightedQuickUnionUF union1 = new WeightedQuickUnionUF(10);
        union1.union(1, 2);
        union1.union(3, 4);
        union1.union(5, 6);
        union1.union(7, 8);
        union1.union(0, 9);
        union1.union(3, 5);

        StdOut.println("1 & 2: " + union1.connected(1, 2));
        StdOut.println("1 & 3: " + union1.connected(1, 3));
        StdOut.println("1 & 4: " + union1.connected(1, 4));
        StdOut.println("1 & 5: " + union1.connected(1, 5));
        StdOut.println("1 & 6: " + union1.connected(1, 6));
        StdOut.println("1 & 7: " + union1.connected(1, 7));
        StdOut.println("3 & 4: " + union1.connected(3, 4));
        StdOut.println("3 & 5: " + union1.connected(3, 5));
        StdOut.println("3 & 6: " + union1.connected(3, 6));
        StdOut.println("3 & 7: " + union1.connected(3, 7));
    }
}
