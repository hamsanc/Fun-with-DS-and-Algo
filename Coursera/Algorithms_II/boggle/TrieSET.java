/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class TrieSET implements Iterable<String>{
    private static final int R = 26;   // Number of Alphabets A to Z
    private static final int ASC = 65; // ASCII number A == 65

    private Node root;                // root of the trie
    private int n;                    // Number of Keys in the trie
    private Stack<Node> st = new Stack<Node>();

    // R -Way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    /* Initialise an Empty set of strings */
    public TrieSET() {

    }

    /* does the set contain the given key */
    public boolean contains(String key){
        if(key == null) throw new IllegalArgumentException("argument to the contains() is null");
        Node x = get(root, key, 0);
        if(x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d){
        if(x == null) return null;
        st.push(x);
        if(d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c-ASC], key,d+1);
    }

    /* Adds the key to the set if it is not already present */
    public void add(String key){
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d){
        if(x == null) x = new Node();
        if(d == key.length()){
            if(!x.isString) n++;
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c-ASC] = add(x.next[c-ASC], key, d+1);
        }
        return x;
    }

       /* return the number of string in set */
    public int size() {
        return n;
    }

    /* is set empty */
    public boolean isEmpty() {
        return size() == 0;
    }
    /**
     * Returns all of the keys in the set, as an iterator.
     * To iterate over all of the keys in a set named {@code set}, use the
     * foreach notation: {@code for (Key key : set)}.
     * @return an iterator to all of the keys in the set
     */
    public Iterator<String> iterator() {
        return keysWithPrefix("").iterator();
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    public boolean wordWithPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        if (x == null) return false;
        return true;
    }

    public boolean nextwordWithPrefix(String prefix,int d) {
        char c = prefix.charAt(d);
        if (c == 'U' && prefix.charAt(d-1) == 'Q') {
            c = 'Q';
            Node x = st.peek().next[c-ASC];
            if (x == null) return false;
            st.push(x);
            c = 'U';
            x = st.peek().next[c-ASC];
            if (x == null) {
                st.pop();
                return false;
            }
            st.push(x);
            return true;
        }else {
            Node x = st.peek().next[c-ASC];
            if (x == null) return false;
            st.push(x);
            return true;
        }

    }


    public int Nodestacklevel(){
        return st.size();
    }

    public void clearNodestack(){
        while(!st.isEmpty())st.pop();
    }

    public void moveNodeleveldown(){
        st.pop();
    }


    public boolean wordisString(String prefix) {
        return st.peek().isString;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.isString) results.enqueue(prefix.toString());
        for (char c = ASC; c < R+ASC; c++) {
            prefix.append(c);
            collect(x.next[c-ASC], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns the string in the set that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the set that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of length length
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.isString) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c-ASC], query, d+1, length);
    }

    /**
     * Removes the key from the set if the key is present.
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.isString) n--;
            x.isString = false;
        }
        else {
            char c = key.charAt(d);
            x.next[c-ASC] = delete(x.next[c-ASC], key, d+1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.isString) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }


    public static void main(String[] args) {
        TrieSET set = new TrieSET();


       while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            set.add(key);
        }

        // print results
        if (set.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : set) {
                StdOut.println(key);
            }
            StdOut.println();
        }

        StdOut.println("keysWithPrefix(\"SH\"):");
        for (String s : set.keysWithPrefix("SH"))
            StdOut.println(s);
        StdOut.println();

    }
}
