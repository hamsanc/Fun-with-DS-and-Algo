// *****************************************************************************

//*  Name:              Alan Turing

//*  Coursera User ID:  123456

//*  Last modified:     1/1/2019

//****************************************************************************

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {

    private int[][] p;
    private final int gz;
    private int countopensite;
    private final WeightedQuickUnionUF wquf;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("grid size should be greater than 0");

        gz = n;
        countopensite = 0;
        p = new int[n + 1][n + 1];
        percolates = false;

        // Create a Union find data type
        wquf = new WeightedQuickUnionUF((n * n) + 1);
    }

    private int xyTo1D(int i, int j) {

        validateindex(i, j);
        int oneD;
        oneD = (i - 1) * gz + j;
        return oneD;
    }

    private int oneDtoxyRow(int oneD) {
        int i = oneD / gz;
        if (oneD % gz == 0) return (i);
        else return (i + 1);

    }

    private int oneDtoxyCol(int oneD) {
        if (oneD % gz == 0) return gz;
        else return (oneD % gz);

    }

    private int getrootstatus(int root) {

        int r = oneDtoxyRow(root);
        int c = oneDtoxyCol(root);
        return (p[r][c]);

    }


    private void validateindex(int row, int col) {
        if (row < 1 || row > gz)
            throw new IllegalArgumentException("Row Index should be between 1 and n");
        if (col < 1 || col > gz)
            throw new IllegalArgumentException("Col Index should be between 1 and n");
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validateindex(row, col);
        if (isOpen(row, col)) return;

        countopensite++;
        // Store default info
        p[row][col] = 1;
        if (row == 1) p[row][col] |= 2;
        else if (row == gz) p[row][col] |= 4;

        int p1 = xyTo1D(row, col);

        int sup = 0;
        int sdn = 0;
        int slf = 0;
        int srg = 0;


        // check if up site is open and connect
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                int pup = xyTo1D(row - 1, col);
                int root = wquf.find(pup);

                sup = getrootstatus(root);
                wquf.union(p1, pup);
            }
        }


        // Check if down site is open and connect
        if (row < gz) {
            if (isOpen(row + 1, col)) {
                int pdn = xyTo1D(row + 1, col);
                int root = wquf.find(pdn);

                sdn = getrootstatus(root);
                wquf.union(p1, pdn);
            }
        }

        // check left site is open and then connect
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                int plf = xyTo1D(row, col - 1);
                int root = wquf.find(plf);

                slf = getrootstatus(root);
                wquf.union(p1, plf);
            }
        }

        // Check right site is open and connect|
        if (col < gz) {
            if (isOpen(row, col + 1)) {
                int prt = xyTo1D(row, col + 1);
                int root = wquf.find(prt);

                srg = getrootstatus(root);
                wquf.union(p1, prt);
            }
        }

        int root = wquf.find(p1);

        int r = oneDtoxyRow(root);
        int c = oneDtoxyCol(root);
        p[r][c] |= sup | sdn | slf | srg | p[row][col];
        p[row][col] |= p[r][c];

     /*   if(sup > 0) p[row-1][col] |= sup | sdn | slf | srg | p[row][col];
        if(sdn > 0) p[row+1][col] |= sup | sdn | slf | srg | p[row][col];
        if(slf > 0) p[row][col-1] |= sup | sdn | slf | srg | p[row][col];
        if(srg > 0) p[row][col+1] |= sup | sdn | slf | srg | p[row][col];*/

        if (gz > 1) {
            if ((p[row][col] & 6) == 6) percolates = true;
        } else {

            percolates = true;
        }


    /*  // if(p[row][col] )
        // FOR TESTING - print p
        for (int i = 1; i <= gz; i++) {
            for (int j = 1; j <= gz; j++) {
                System.out.print(String.format("%x",p[i][j]) + "    ");
            }
            StdOut.println();
        }

        StdOut.println(percolates);*/

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        validateindex(row, col);

        return (p[row][col] > 0);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        validateindex(row, col);
        if (!isOpen(row, col)) return false;

        int root = wquf.find(xyTo1D(row, col));

        return ((getrootstatus(root) & 2) == 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countopensite;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }


    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        int count = 0;

        for (int t = 0; t < 100; t++) {
            Percolation per = new Percolation(n);
            // random opening of the sites
            for (int i = 0; i < (60); i++) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                per.open(row, col);
            }
            if (per.percolates()) count++;

        }

        StdOut.println("No of time system percolates in 100 trial: " + count);

        // Check the number of open sites
        // StdOut.println("No of open sites: " + per.numberOfOpenSites());

        // check if the system percolates
        //  StdOut.println("Does the system percolate: " + per.percolates());

    }
}
