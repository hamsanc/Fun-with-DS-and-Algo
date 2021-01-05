/**
 * **************************************************************************
 * Name:
 * Date:
 * Description:
 *****************************************************************************/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private ST<String, Integer> validwords;
    private ST<Integer, Integer> score = new ST<Integer, Integer>();
    private TrieSET set = new TrieSET();
    private int m; // number of rows in the board
    private int n; // number of cols in the baord
    private char[] bboard;
    private boolean[] marked;
    private Bag<Integer>[] adj;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        // Construct trie with the given dictionary
        for (int i = 0; i < dictionary.length; i++) {
            set.add(dictionary[i]);
        }
        fillScoretable();
    }

    /**
     * word length 	   	points
     * 3–4 		    1
     * 5 		        2
     * 6 		        3
     * 7 		        5
     * 8+ 		        11
     */
    private void fillScoretable() {
        score.put(3, 1);
        score.put(4, 1);
        score.put(5, 2);
        score.put(6, 3);
        score.put(7, 5);
        score.put(8, 11);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        m = board.rows();
        n = board.cols();
        bboard = new char[m * n];
        marked = new boolean[m * n];
        validwords = new ST<String, Integer>();
        adj = (Bag<Integer>[]) new Bag[m * n];

        for (int v = 0; v < m * n; v++) {
            adj[v] = new Bag<Integer>();
        }

        preprocessb_board(board);

        for (int i = 0; i < m * n; i++) {
            StringBuilder str
                    = new StringBuilder();
            marked[i] = true;
            char c = bboard[i];
            str.append(c);
            if (c == 'Q') {
                str.append('U');
            }
            set.clearNodestack();
            board_DFS(i, str, 0);
            marked[i] = false;
        }
        return validwords.keys();
    }

    private void board_DFS(int i, StringBuilder prefix, int d) {


        String s = prefix.toString();
        int len = prefix.length();

        if (d == 0) {
            if (!set.wordWithPrefix(s)) return;
        }
        else {
            if (!set.nextwordWithPrefix(s, d)) return;
            if ((len > 2) && set.wordisString(s)) {
                if (!validwords.contains(s))
                    validwords.put(s, 6); // score.get(len)
            }
        }

        for (int w : adj[i]) {
            if (!marked[w]) {
                marked[w] = true;
                char c = bboard[w];
                prefix.append(c);
                if (c == 'Q') {
                    prefix.append('U');
                }
                board_DFS(w, prefix, prefix.length() - 1);

                if (prefix.charAt(prefix.length() - 1) == 'U'
                        && prefix.charAt(prefix.length() - 2) == 'Q') {
                    prefix.delete((prefix.length() - 2), (prefix.length()));
                    while (set.Nodestacklevel() - 1 > prefix.length())
                        set.moveNodeleveldown();
                }
                else {
                    prefix.deleteCharAt(prefix.length() - 1);
                    while (set.Nodestacklevel() - 1 > prefix.length())
                        set.moveNodeleveldown();

                }
                marked[w] = false;
            }

        }

    }


    private void preprocessb_board(BoggleBoard board) {

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                bboard[i * n + j] = board.getLetter(i, j);
              //  System.out.println(((i * n)  + j) + " ");
                fill_adj(i, j);
            }
           // System.out.println();
        }
    }

    private void fill_adj(int row, int col) {
        int indx = row * n + col;

        // Check if there is element up
        if (row - 1 >= 0) {
            adj[indx].add((row - 1) * n + col);
            // Check if there is element left
            if (col - 1 >= 0) {
                adj[indx].add((row - 1) * n + col - 1);
            }

            // Check if there is element right
            if (col + 1 < n) {
                adj[indx].add((row - 1) * n + col + 1);
            }
        }

        // Check if there is element down
        if (row + 1 < m) {
            adj[indx].add((row + 1) * n + col);
            // Check if there is element left
            if (col - 1 >= 0) {
                adj[indx].add((row + 1) * n + col - 1);
            }
            // Check if there is element right
            if (col + 1 < n) {
                adj[indx].add((row + 1) * n + col + 1);
            }
        }
        // Check if there is element left
        if (col - 1 >= 0) {
            adj[indx].add((row) * n + col - 1);
        }
        // Check if there is element right
        if (col + 1 < n) {
            adj[indx].add((row) * n + col + 1);
        }


     /* for (int w : adj[indx]) {
            System.out.print(w + " ");
        }
        System.out.println();*/
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int len = word.length();
        if (len < 3) return 0;
        if (!set.contains(word)) return 0;
        if (len > 8) len = 8;
        return score.get(len);
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(1,10);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
