import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF fullCheck;
    private int virtualTop;
    private int virtualBottom;
    private int openSites;

    // Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Invalid grid size");

        this.n = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        fullCheck = new WeightedQuickUnionUF(n * n + 1);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        openSites = 0;
    }

    // Validate if the indices are within the grid boundaries
    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Invalid indices");
    }

    // Convert 2D indices to 1D for union-find data structure
    private int convertTo1D(int row, int col) {
        return (row - 1) * n + col;
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++;

            int currentSite = convertTo1D(row, col);

            if (row == 1) {
                uf.union(virtualTop, currentSite);
                fullCheck.union(virtualTop, currentSite);
            }

            if (row == n)
                uf.union(virtualBottom, currentSite);

            // Check and connect to open neighboring sites
            int[] dx = {0, 0, -1, 1};
            int[] dy = {-1, 1, 0, 0};
            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];

                if (newRow >= 1 && newRow <= n && newCol >= 1 && newCol <= n && isOpen(newRow, newCol)) {
                    int neighborSite = convertTo1D(newRow, newCol);
                    uf.union(currentSite, neighborSite);
                    fullCheck.union(currentSite, neighborSite);
                }
            }
        }
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return grid[row - 1][col - 1];
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return isOpen(row, col) && fullCheck.find(virtualTop) == fullCheck.find(convertTo1D(row, col));
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }
}
