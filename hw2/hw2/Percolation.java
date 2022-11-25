package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] isOpen;
    private final int size;
    private int numberOfOpenSites;

    private final int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        isOpen = new boolean[N * N];
        numberOfOpenSites = 0;

        // Add a dummy block to the top and bottom
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 2);

        // union the top level to full
        for (int i = 0; i < size; i++) {
            uf.union(i, size * size);
            uf2.union(i, size * size);
            uf2.union(size * (N - 1) + i, size * size + 1);
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int index = row * size + col;

        // if not open
        if (!isOpen(row, col)) {
            numberOfOpenSites++;

            for (int[] direction : directions) {
                int r = row + direction[0];
                int c = col + direction[1];
                if (isOutOfBound(r, c)) {
                    continue;
                }
                if (isOpen(r, c)) {
                    uf.union(r * size + c, index);
                    uf2.union(r * size + c, index);
                }
            }
            isOpen[index] = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen[row * size + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen[row * size + col]) {
            return uf.connected(row * size + col, size * size);
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf2.connected(size * size, size * size + 1);
    }

    private boolean isOutOfBound(int row, int col) {
        return row >= size || col >= size || row < 0 || col < 0;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println(p.isOpen(0, 0));
        p.open(0, 0);
        System.out.println(p.isOpen(0, 0));
        System.out.println(p.isFull(0, 0));
        p.open(1, 0);
        p.open(3, 0);
        p.open(4, 0);
        System.out.println(p.isFull(4, 0));
        System.out.println(p.percolates());
        p.open(2, 0);
        System.out.println(p.percolates());
    }
}
