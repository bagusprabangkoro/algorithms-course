package union.find;

import edu.princeton.cs.algs4.StdOut;

// TODO fix x & y index
//   model:
//      grin N x N
//      top left index: 1, 1
//      right bottom index: N, N
// TODO how to implement ?
//  - imaginary root: top root & bottom root
//  - mark type for top & bottom

public class Percolation_failed {
    class Grid {
        public int x;
        public int y;

        public Grid(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private boolean isPercolate = false;
    // 1: connected to top
    // 0: not connected to top and bottom
    // -1: connected to bottom
    private Grid[][] grid;
    private final int TOP = 1;
    private final int MIDDLE = 0;
    private final int BOTTOM = -1;
    private int[][] type;
    private int[][] sz;
    private boolean[][] opened;
    private int numberOfOpenSite = 0;
    private final int dimension;

    public Percolation_failed(int N) {
        grid = new Grid[N + 1][N + 1];
        type = new int[N + 1][N + 1];
        sz = new int[N + 1][N + 1];
        opened = new boolean[N + 1][N + 1];
        dimension = N;
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                grid[i][j] = new Grid(i, j);
                type[i][j] = MIDDLE;
                sz[i][j] = 1;
                opened[i][j] = false;
            }
        }
    }

    private Grid root(int x, int y) {
        // current grid is root
        if (grid[x][y].x == x && grid[x][y].y == y) {
            return grid[x][y];
        }
        // convert parent type to TOP or BOTTOM if condition satisfied
        if (type[x][y] == TOP || type[x][y] == BOTTOM) {
            type[grid[x][y].x][grid[x][y].y] = type[x][y];
        }
        // path compression
        grid[x][y] = new Grid(grid[x][y].x, grid[x][y].y);

        // traverse to parent
        return root(grid[x][y].x, grid[x][y].y);
    }

    private void union(int x1, int y1, int x2, int y2) {
        Grid root1 = root(x1, y1);
        Grid root2 = root(x2, y2);
        // already on the same root, no modification needed
        if (root1.x == root2.x && root1.y == root2.y) {
            return;
        }

        // type-union
        if (type[x1][y1] != MIDDLE && type[x2][y2] == MIDDLE) {
            // convert point 2 if point 1 is not middle
            type[x2][y2] = type[x1][y1];
        } else if (type[x2][y2] != MIDDLE && type[x1][y1] == MIDDLE) {
            // convert point 1 if point 2 is not middle
            type[x1][y1] = type[x2][y2];
        } else if (type[x1][y1] != type[x2][y2]) {
            // point 1 and point 2 is top-bottom
            isPercolate = true;
        }

        // weighted-union
        if (sz[root1.x][root1.y] < sz[root2.x][root2.y]) {
            grid[root1.x][root1.y] = new Grid(root2.x, root2.y);
            sz[root2.x][root2.y] += sz[root1.x][root1.y];
        } else {
            grid[root2.x][root2.y] = new Grid(root1.x, root1.y);
            sz[root1.x][root1.y] += sz[root2.x][root2.y];
        }
    }

    public void open(int x, int y) {
        if (isOpen(x, y)) {
            return;
        }

        numberOfOpenSite++;
        opened[x][y] = true;

        // initiate type when start opening
        if (y == 1) {
            type[x][y] = TOP;
        }
        if (y == dimension) {
            type[x][y] = BOTTOM;
        }

        // union top (x,y) & (x,y-1)
        if (y >= 2 && isOpen(x, y - 1)) {
            union(x, y, x, y - 1);
        }
        // union bottom (x,y) & (x,y+1)
        if (y < dimension && isOpen(x, y + 1)) {
            union(x, y, x, y + 1);
        }
        // union left (x,y) & (x-1,y)
        if (x >= 2 && isOpen(x - 1, y)) {
            union(x, y, x - 1, y);
        }
        // union right (x,y) & (x+1,y)
        if (x < dimension && isOpen(x + 1, y)) {
            union(x, y, x + 1, y);
        }
    }

    public boolean isOpen(int x, int y) {
        if (x < 1 || x > dimension || y < 1 || y > dimension) {
            throw new IllegalArgumentException();
        }
        return opened[x][y];
    }

    public boolean isFull(int x, int y) {
        if (x < 1 || x > dimension || y < 1 || y > dimension) {
            throw new IllegalArgumentException();
        }
        return !opened[x][y];
    }

    public int numberOfOpenSites() {
        return numberOfOpenSite;
    }

    public boolean percolates() {
        return isPercolate;
    }

    public void printType() {
        StdOut.println("---- Type ----");
        for (int i = 1; i < dimension + 1; i++) {
            for (int j = 1; j < dimension + 1; j++) {
                StdOut.print(" " + type[j][i] + " ");
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        Percolation_failed percolationFailed = new Percolation_failed(4);
        percolationFailed.open(1, 1);
        percolationFailed.open(2, 2);
        percolationFailed.open(3, 2);
        percolationFailed.open(4, 2);
        percolationFailed.open(1, 2);



        StdOut.println("percolate: " + percolationFailed.percolates());
//        StdOut.println("number of open sites: " + percolation.numberOfOpenSites());
        percolationFailed.printType();
    }
}
