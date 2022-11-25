package test;

import hw2.PercolationFactory;
import hw2.PercolationStats;
import org.junit.Test;

public class PercolationStatsTest {
    @Test
    public void testPercolationStats() {
        PercolationStats percolationStats = new PercolationStats(10, 30, new PercolationFactory());
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.confidenceHigh());
    }
}
