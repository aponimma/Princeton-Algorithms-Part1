import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    /**
     * Create a new PercolationStats object whcih performs trials independent
     * experiment on an n-by-n grid
     * @param n the size of the grids
     * @param trials the number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            int times = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    times++;
                }
            }
            thresholds[i] = (double) times / (n * n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }

    /**
     * Return the sample mean of percolation threshold
     * @return the sample mean
     */
    public double mean() {
        return mean;
    }

    /**
     * Return the sample standard deviation of percolation threshold
     * @return the sample standard deviation
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Return the low endpoint of 95% confidence interval
     * @return the low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * Return the high endpoint of 95% confidence interval
     * @return the high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, trials);
        System.out.println(pStats.mean());
        System.out.println(pStats.stddev());
        System.out.println("[" + pStats.confidenceLo() + ", " + pStats.confidenceHi() +"]");
    }
}
