/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final char[] tile;
    private final int n;
    private final int manhattancnt;
    private final int hammingcnt;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        n = tiles.length;
        tile = new char[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tile[i * n + j] = (char) Integer.parseInt(String.valueOf(tiles[i][j]));
            }
        }

        /* compute manhattan */
        manhattancnt = this.manhat();

        /* Compute hamming */
        hammingcnt = this.hamm();


    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) tile[i * n + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    private int hamm() {
        int hamcnt = 0;
        for (int i = 0; i < (n * n) - 1; i++) {
            if ((int) this.tile[i] != (i + 1)) hamcnt++;
        }
        return hamcnt;
    }

    public int hamming() {

        return hammingcnt;
    }

    // sum of Manhattan distances between tiles and goal
    private int manhat() {
        int mancnt = 0;

        for (int i = 0; i < (n * n); i++) {
            int k = this.tile[i];
            if (k != 0) {
                k = k - 1;
                int ax = k / n;
                int ay = k % n;

                int bx = i / n;
                int by = i % n;

                mancnt += Math.abs(ax - bx) + Math.abs(ay - by);
            }
        }
        return mancnt;
    }

    public int manhattan() {

        return manhattancnt;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.equals(this.tile, that.tile);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int[][] t = new int[n][n];
        int zrow = 0;
        int zcol = 0;
        // find 0 position;
        for (int i = 0; i < (n * n); i++) {
            int k = this.tile[i];
            int p = i / n;
            int q = i % n;
            if (k == 0) {
                zrow = p;
                zcol = q;
            }
            t[p][q] = k;

        }

        // Check if there is element left of zero
        if (zrow - 1 >= 0) {
            t[zrow][zcol] = t[zrow - 1][zcol];
            t[zrow - 1][zcol] = 0;
            neighbors.add(new Board(t));
            t[zrow - 1][zcol] = t[zrow][zcol];
            t[zrow][zcol] = 0;
        }

        // Check if there is element right of zero
        if (zrow + 1 < n) {
            t[zrow][zcol] = t[zrow + 1][zcol];
            t[zrow + 1][zcol] = 0;
            neighbors.add(new Board(t));
            t[zrow + 1][zcol] = t[zrow][zcol];
            t[zrow][zcol] = 0;
        }

        // Check if there is element up of zero
        if (zcol - 1 >= 0) {
            t[zrow][zcol] = t[zrow][zcol - 1];
            t[zrow][zcol - 1] = 0;
            neighbors.add(new Board(t));
            t[zrow][zcol - 1] = t[zrow][zcol];
            t[zrow][zcol] = 0;
        }

        // Check if there is element down of zero
        if (zcol + 1 < n) {
            t[zrow][zcol] = t[zrow][zcol + 1];
            t[zrow][zcol + 1] = 0;
            neighbors.add(new Board(t));
            t[zrow][zcol + 1] = t[zrow][zcol];
            t[zrow][zcol] = 0;
        }


        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] t = new int[n][n];
        int[] a = new int[4];
        for (int i = 0; i < (n * n); i++) {
            int k = this.tile[i];
            int p = i / n;
            int q = i % n;
            t[p][q] = k;
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] != 0) {
                    a[cnt++] = i;
                    a[cnt++] = j;

                    if (cnt == 4) break;
                }
            }
            if (cnt == 4) break;
        }
        /* swap */
        int temp = t[a[0]][a[1]];
        t[a[0]][a[1]] = t[a[2]][a[3]];
        t[a[2]][a[3]] = temp;


        return new Board(t);
    }


    // unit testing (not graded)
    public static void main(String[] args) {

        Board[] initial = new Board[3];
        int cnt = 0;

        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            initial[cnt] = new Board(tiles);


            StdOut.println(initial[cnt]);
            //StdOut.println(initial[cnt].twin());
            StdOut.println(initial[cnt].hamming());
            StdOut.println(initial[cnt].manhattan());
            StdOut.println(initial[cnt].isGoal());

            cnt++;
        }

        //StdOut.println(initial[0].equals(initial[1]));
        /*for(Board b:initial[0].neighbors())
            StdOut.println(b);*/
    }
}
