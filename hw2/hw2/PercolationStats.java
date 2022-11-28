package hw2;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private final Percolation[] percolations;
    private final int size;

    private int[] results;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        int times;
        results = new int[T];
        percolations = new Percolation[T];
        size = N;

        for (int i = 0; i < T; i++) {
            times = 0;
            percolations[i] = pf.make(N);
            while (!percolations[i].percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                if (!percolations[i].isOpen(x, y)) {
                    percolations[i].open(x, y);
                    times++;
                }
            }
            results[i] = times;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
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
}
