import edu.princeton.cs.algs4.StdOut;

public class QuickFindUF {
    private int[] nodes;

    public QuickFindUF(int N) {
        nodes = new int[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return nodes[p] == nodes[q];
    }

    public void union(int p, int q) {
        int pGroup = nodes[p];
        int qGroup = nodes[q];
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == pGroup) {
                nodes[i] = qGroup;
            }
        }
    }

    public static void main(String[] args) {
        QuickFindUF union1 = new QuickFindUF(10);
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
