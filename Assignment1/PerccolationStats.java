import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int trials;
    private double meanValue;
    private double stddevValue;
    private double confidenceLoValue;
    private double confidenceHiValue;

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid arguments");

        this.trials = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, col))
                    percolation.open(row, col);
            }

            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        meanValue = StdStats.mean(thresholds);
        stddevValue = StdStats.stddev(thresholds);
        double confidenceFraction = 1.96 * stddevValue / Math.sqrt(trials);
        confidenceLoValue = meanValue - confidenceFraction;
        confidenceHiValue = meanValue + confidenceFraction;
    }

    // Sample mean of percolation threshold
    public double mean() {
        return meanValue;
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return stddevValue;
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLoValue;
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHiValue;
    }

    // Test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
