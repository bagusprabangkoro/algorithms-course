import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF wquf;
  private final int dimension;
  private final int topRoot = 0;
  private final int bottomRoot;
  private boolean[][] opened;
  private int openCount;

  public Percolation(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Percolation dimension must be > 0.");
    }
    wquf = new WeightedQuickUnionUF(n * n + 2);
    opened = new boolean[n + 1][n + 1];
    dimension = n;
    bottomRoot = n * n + 1;
    openCount = 0;
    for (int row = 1; row < n + 1; row++) {
      for (int col = 1; col < n + 1; col++) {
        opened[row][col] = false;
      }
    }
  }

  public void open(int row, int col) {
    if (isOpen(row, col)) {
      return;
    }
    opened[row][col] = true;
    openCount++;
    if (row == 1) {
      wquf.union(topRoot, rowColToIndex(row, col));
    } else if (row == dimension) {
      wquf.union(bottomRoot, rowColToIndex(row, col));
    }

    // top
    if (row > 1 && isOpen(row - 1, col)) {
      wquf.union(rowColToIndex(row, col), rowColToIndex(row - 1, col));
    }
    // bottom
    if (row < dimension && isOpen(row + 1, col)) {
      wquf.union(rowColToIndex(row, col), rowColToIndex(row + 1, col));
    }
    // left
    if (col > 1 && isOpen(row, col - 1)) {
      wquf.union(rowColToIndex(row, col), rowColToIndex(row, col - 1));
    }
    // right
    if (col < dimension && isOpen(row, col + 1)) {
      wquf.union(rowColToIndex(row, col), rowColToIndex(row, col + 1));
    }
  }

  public boolean isOpen(int row, int col) {
    if (row < 1 || row > dimension || col < 0 || col > dimension) {
      throw new IllegalArgumentException("row or column out of range");
    }
    return opened[row][col];
  }

  public boolean isFull(int row, int col) {
    if (!isOpen(row, col)) {
      return false;
    }
    // check either it's already connected to top
    int top = wquf.find(topRoot);
    int root = wquf.find(rowColToIndex(row, col));
    return root == top;
  }

  public int numberOfOpenSites() {
    return openCount;
  }

  public boolean percolates() {
    return wquf.find(topRoot) == wquf.find(bottomRoot);
  }

  private int rowColToIndex(int row, int col) {
    return (row - 1) * dimension + col;
  }

  public static void main(String[] args) {
    Percolation percolation = new Percolation(3);
    percolation.open(1, 1);
    percolation.open(2, 1);

    StdOut.println("isFull: " + percolation.isFull(2, 1));
    StdOut.println("isFull: " + percolation.isFull(3, 1));
    StdOut.println("percolate: " + percolation.percolates());
  }
}
