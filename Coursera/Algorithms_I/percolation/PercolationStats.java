/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    private final int tr;                  // number of trials
    private final double[] sum;
    private static final double CONF_I95 = 1.96;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException(
                "grid size should be greater than 0, trials should greater than 0");
        tr = trials;
        sum = new double[tr];


        for (int t = 0; t < tr; t++) {

            Percolation per = new Percolation(n);

            while (!per.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                per.open(row, col);

            }
            sum[t] = ((double) per.numberOfOpenSites() / ((double) (n * n)));


        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sum);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sum);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (this.mean() - ((CONF_I95 * this.stddev()) / Math.sqrt(tr)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (this.mean() + ((CONF_I95 * this.stddev()) / Math.sqrt(tr)));
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);


        PercolationStats perst = new PercolationStats(n, t);

        StdOut.println("mean                    = " + perst.mean());
        StdOut.println("stddev                  = " + perst.stddev());
        StdOut.println("95% confidence interval = " + "[" + perst.confidenceLo() + ", " + perst
                .confidenceHi() + "]");

    }
}
