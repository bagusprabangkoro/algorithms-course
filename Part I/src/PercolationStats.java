import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percolatePercents;
    private int dimension;
    private int trials;
    private double meanValue;
    private double stddevValue;

    public PercolationStats(int N, int trials) {
        if (N <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Dimension or trials must be > 0");
        }
        dimension = N;
        this.trials = trials;
        percolatePercents = new double[trials];
        for (int i = 0; i < trials; i++) {
            // Monte Carlo Simulation
            int count = simulate();
            //StdOut.println("trial " + i + " | count: " + count + " | percent: " + (double) count / (double) (dimension * dimension));
            percolatePercents[i] = (double) count / (double) (dimension * dimension);
        }

        // perform calculation to get 1 time calculation
        meanValue = StdStats.mean(percolatePercents);
        stddevValue = StdStats.stddev(percolatePercents);
    }

    private int simulate() {
        Percolation percolation = new Percolation(dimension);

        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, dimension + 1);
            int col = StdRandom.uniform(1, dimension + 1);
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
            }
        }
        return percolation.numberOfOpenSites();
    }

    public double mean() {
        return meanValue;
    }

    public double stddev() {
        return stddevValue;
    }

    public double confidenceLo() {
        return meanValue - 1.96 * stddevValue / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return meanValue + 1.96 * stddevValue / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
