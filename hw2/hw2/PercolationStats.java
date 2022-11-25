package hw2;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private final Percolation[] percolations;
    private final int size;

    private final double percentage = .6;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        percolations = new Percolation[T];
        size = N;

        for (int i = 0; i < T; i++) {
            percolations[i] = pf.make(N);
            open(i);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(Arrays.stream(percolations).mapToDouble(Percolation::numberOfOpenSites).toArray());
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(Arrays.stream(percolations).mapToDouble(Percolation::numberOfOpenSites).toArray());
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double sqrtT = Math.sqrt(size);
        return mean() - (1.96 * stddev()) / sqrtT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double sqrtT = Math.sqrt(size);
        return mean() + (1.96 * stddev()) / sqrtT;
    }

    private void open(int index) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double val = StdRandom.uniform();
                if (val <= percentage) {
                    percolations[index].open(i, j);
                }
            }
        }
    }
}
