import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    private int size;
    private boolean[] openState;
    private int openNumber;
    private int vTopID;
    private int vBottomID;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF fullState;

    /**
     * Creates a new Percolation object with an n-by-n gird
     * @param n the size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        openState = new boolean[n * n + 2];
        openNumber = 0;
        vTopID = n * n;
        vBottomID = n * n + 1;
        grid = new WeightedQuickUnionUF(n * n + 2);
        /* a seperate WeightedQuickUnionUF without bottom node to sovle backwash */
        fullState = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n * n + 2; i++) {
            openState[i] = i == vTopID || i == vBottomID;
        }
    }

    /**
     * Convert two-dimensional cooridinates to a one-dimensional ID
     * @param row x coordinate
     * @param col y coordinate
     * @return the corresponding one-dimensional ID
     */
    private int xyToID(int row, int col) {
        validateXy(row, col);
        return size * (row - 1) + (col - 1);
    }

    /**
     * Check whether the site's coordinates are within the valid range
     * @param row x coordinate
     * @param col y coordinate
     */
    private void validateXy(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Open the site if it is not opened already
     * @param row x coordinate
     * @param col y coordinate
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int ID = xyToID(row, col);
            openState[ID] = true;
            openNumber++;
            if (col > 1) {
                if (isOpen(row, col - 1)) {
                    int leftID = xyToID(row, col - 1);
                    grid.union(ID, leftID);
                    fullState.union(ID, leftID);
                }
            }
            if (col < size) {
                if (isOpen(row, col + 1)) {
                    int rightID = xyToID(row, col + 1);
                    grid.union(ID, rightID);
                    fullState.union(ID, rightID);
                }
            }
            if (row > 1) {
                if (isOpen(row - 1, col)) {
                    int upID = xyToID(row - 1, col);
                    grid.union(ID, upID);
                    fullState.union(ID, upID);
                }
            }
            if (row < size) {
                if (isOpen(row + 1, col)) {
                    int downID = xyToID(row + 1, col);
                    grid.union(ID, downID);
                    fullState.union(ID, downID);
                }
            }
            if (row == 1) {
                grid.union(ID, vTopID);
                fullState.union(ID, vTopID);
            }
            if (row == size) {
                grid.union(ID, vBottomID);
            }

        }
    }

    /**
     * Check whether the site is open
     * @param row x coordinate
     * @param col y coordinate
     * @return whther the site is open
     */
    public boolean isOpen(int row, int col) {
        validateXy(row, col);
        int ID = xyToID(row, col);
        return openState[ID];
    }

    /**
     * Check whether the site is full
     * @param row x coordinate
     * @param col y coordinate
     * @return whether the site is full
     */
    public boolean isFull(int row, int col) {
        validateXy(row, col);
        int ID = xyToID(row, col);
        return fullState.connected(ID, vTopID);
    }

    /**
     * Return the number of open sites
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openNumber;
    }

    /**
     * Check whether the grid system percolates
     * @return whther the grid system percolates
     */
    public boolean percolates() {
        return grid.connected(vTopID, vBottomID);
    }

    public static void main(String[] args) {
        int[] input = {62, 83, 18, 53, 07, 17, 95, 86, 47, 69, 25, 28};
        for (int i = 5; i < input.length; i++) {
            for (int j = i; j >= 5 && input[j] < input[j - 5]; j -= 5) {
                int swap = input[j];
                input[j] = input[j - 5];
                input[j - 5] = swap;
            }
        }
        System.out.println(Arrays.toString(input));
    }

}
