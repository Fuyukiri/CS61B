package test;

import hw2.PercolationFactory;
import hw2.PercolationStats;
import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStatsTest {

    private static int N = 100;
    private static int T = 300;
    @Test
    public void testPercolationStats() {
        timeCheck(N, T);
        timeCheck(N * 2, T);
        timeCheck(N, T * 2);
        timeCheck(N * 2, T * 2);
    }

    private void timeCheck(int N, int T) {
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(N, T, new PercolationFactory());
        percolationStats.stddev();
        percolationStats.confidenceHigh();
        percolationStats.confidenceLow();
        double time = stopwatch.elapsedTime();
        System.out.println("N: " + N + " T: " + T);
        System.out.println("Time: " + time);
    }
}
