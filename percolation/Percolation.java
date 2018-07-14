import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int length;
    private boolean[] openState;
    private int openNumber;
    private int vTopID;
    private int vBottomID;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF fullState;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        length = n;
        openState = new boolean[n * n + 2];
        openNumber = 0;
        vTopID = n * n;
        vBottomID = n * n + 1;
        grid = new WeightedQuickUnionUF(n * n + 2);
        fullState = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n * n + 2; i++) {
            openState[i] = i == vTopID || i == vBottomID;
        }
    }

    private int xyToID(int row, int col) {
        validateXy(row, col);
        return length * (row - 1) + (col - 1);
    }

    private void validateXy(int row, int col) {
        if ((row < 1 || row > length) || (col < 1 || col > length)) {
            throw new IllegalArgumentException("Site indices are out of bounds!");
        }
    }

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
            if (col < length) {
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
            if (row < length) {
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
            if (row == length) {
                grid.union(ID, vBottomID);
            }

        }
    }

    public boolean isOpen(int row, int col) {
        validateXy(row, col);
        int ID = xyToID(row, col);
        return openState[ID];
    }

    public boolean isFull(int row, int col) {
        validateXy(row, col);
        int ID = xyToID(row, col);
        return fullState.connected(ID, vTopID);
    }

    public int numberOfOpenSites() {
        return openNumber;
    }

    public boolean percolates() {
        return grid.connected(vTopID, vBottomID);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 2);
        p.open(4, 2);
        System.out.println(p.percolates());
        System.out.println(p.isFull(3, 2));

    }

}
