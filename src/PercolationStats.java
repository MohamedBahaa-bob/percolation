
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final int trials;
	private final double[] values;
	private final static double confidence = 1.96;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("invalid specifications");
		}
		this.trials = trials;
		values = new double[trials];
		int row, col;
		int gridSize = (int) Math.pow(n, 2);
		Percolation p;
		for (int i = 0; i < trials; i++) {
			p = new Percolation(n);
			row = StdRandom.uniform(1, n + 1);
			col = StdRandom.uniform(1, n + 1);
			while (!p.percolates()) {
				p.open(row, col);
				row = StdRandom.uniform(1, n + 1);
				col = StdRandom.uniform(1, n + 1);
			}
			values[i] = ((p.numberOfOpenSites() * 1.0) / (gridSize * 1.0));
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(values);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(values);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((confidence * stddev()) / Math.sqrt(trials));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((confidence * stddev()) / Math.sqrt(trials));
	}

	// test client (see below)
	public static void main(String[] args) {
		System.out.println("Please enter side of grid followed by number of required trials:\n");
		int side = StdIn.readInt();
		int trials = StdIn.readInt();
		PercolationStats stats = new PercolationStats(side, trials);
		StdOut.println("mean\t= " + stats.mean());
		StdOut.println("stddev\t= " + stats.stddev());
		StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]\n");
	}
}
