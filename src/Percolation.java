
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] opened;
	private final int n;
	private int count = 0;
	private final WeightedQuickUnionUF wqu;
	private final int virtualTop, virtualBottom;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("invalid grid dimension");
		}
		this.n = n;
		int gridSize = (int) Math.pow(n, 2);
		wqu = new WeightedQuickUnionUF(gridSize + 2);
		opened = new boolean[gridSize];
		virtualTop = gridSize;
		virtualBottom = gridSize + 1;
	}

	// transforms from 2D to 1D
	private int getIndex(int row, int col) {
		return (row - 1) * n + (col - 1);
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (row > n || row < 1 || col > n || col < 1) {
			throw new IllegalArgumentException("can not open invalid site");
		}
		int index = getIndex(row, col);
		if (!opened[index]) {
			count++;
			opened[index] = true;
			int boundary = (int) (Math.pow(n, 2) - 1);
			if (index % n != n - 1 && opened[index + 1])
				wqu.union(index, index + 1);
			if (index % n != 0 && opened[index - 1])
				wqu.union(index, index - 1);
			if (index <= (boundary - n) && opened[index + n])
				wqu.union(index, index + n);
			if (index >= n && opened[index - n])
				wqu.union(index, index - n);
			// connects virtual sites to open valid sites
			if (index < n)
				wqu.union(index, virtualTop);
			if (index > boundary - n)
				wqu.union(index, virtualBottom);
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row > n || row < 1 || col > n || col < 1) {
			throw new IllegalArgumentException("checking for an invalid site");
		}
		int index = getIndex(row, col);
		if (opened[index])
			return true;
		return false;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (row > n || row < 1 || col > n || col < 1) {
			throw new IllegalArgumentException("checking for an invalid site");
		}
		int index = getIndex(row, col);
		if (opened[index] && wqu.find(index) == wqu.find(virtualTop))
			return true;
		return false;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return count;
	}

	// does the system percolate?
	public boolean percolates() {
		if (wqu.find(virtualBottom) == wqu.find(virtualTop))
			return true;
		else
			return false;
	}
}
