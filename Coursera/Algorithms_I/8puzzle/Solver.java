/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final int movecnt;
    private final boolean is_solvable;

    private Stack<Board> solution = new Stack<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Argument Board is null");

        MinPQ<SearchNode> sn = new MinPQ<>();
        MinPQ<SearchNode> tn = new MinPQ<>();

        int snmoves = 0;
        int tnmoves = 0;
        boolean solvable = false;

        sn.insert(new SearchNode(initial, null, snmoves));
        tn.insert(new SearchNode(initial.twin(), null, snmoves));

        while (!sn.isEmpty()) {

            SearchNode currentsn = sn.delMin();
            SearchNode currenttn = tn.delMin();

            if (currentsn.getBoard().isGoal()) {
                solvable = true;
                SearchNode current = currentsn;
                solution.push(current.getBoard());
                while (current.getPrenode() != null) {
                    current = current.getPrenode();
                    solution.push(current.getBoard());
                }
                break;
            }

            if (currenttn.getBoard().isGoal()) {
                break;
            }

            snmoves = currentsn.getMoves() + 1;
            tnmoves = currenttn.getMoves() + 1;

            /* add Search node neighbors */
            for (Board b : currentsn.getBoard().neighbors()) {
                if (currentsn.getPrenode() != null) {
                    if (b.equals(currentsn.getPrenode().getBoard())) continue;
                    sn.insert(new SearchNode(b, currentsn, snmoves));
                }
                else {
                    sn.insert(new SearchNode(b, currentsn, snmoves));
                }
            }
            /* add twin board neighbors */
            for (Board b : currenttn.getBoard().neighbors()) {
                if (currenttn.getPrenode() != null) {
                    if (b.equals(currenttn.getPrenode().getBoard())) continue;
                    tn.insert(new SearchNode(b, currenttn, tnmoves));
                }
                else {
                    tn.insert(new SearchNode(b, currenttn, tnmoves));
                }
            }


        }
        if (solvable == false) {
            snmoves = -1;
        }

        is_solvable = solvable;
        movecnt = snmoves;


    }


    private static class SearchNode implements Comparable<SearchNode> {

        private Board b;
        private SearchNode pre;
        private int manhatpri;
        private int manhat;
        private int mv;


        public SearchNode(Board present, SearchNode previous, int moves) {
            b = present;
            pre = previous;
            manhat = b.manhattan();
            mv = moves;
            manhatpri = manhat + moves;
        }

        public int getManhatpri() {
            return this.manhatpri;
        }

        public int getManhat() {
            return this.manhat;
        }

        public Board getBoard() {
            return b;
        }

        public int getMoves() {
            return this.mv;
        }

        public SearchNode getPrenode() {
            return pre;
        }

        public int compareTo(SearchNode that) {
            if (this.getManhatpri() < that.getManhatpri()) return -1;
            if (this.getManhatpri() > that.getManhatpri()) return +1;
            if (this.getManhat() < that.getManhat()) return -1;
            if (this.getManhat() > that.getManhat()) return +1;
            return 0;
        }

    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return is_solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return movecnt;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (is_solvable == true)
            return solution;
        else
            return null;
    }


    // test client (see below)
    public static void main(String[] args) {


        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible " + solver.solution());
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


    }

}