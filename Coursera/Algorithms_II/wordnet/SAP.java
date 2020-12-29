/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 * Maintain an array of vectors- parents (storing parents of each node).

    Firstly do a bfs(keep storing parents of each vertex) and find all the ancestors of x (find parents of x and using parents,
    * find all the ancestors of x) and store them in a vector. Also, store the depth of each parent in the vector.

    Find the ancestors of y using same method and store them in another vector.
    * Now, you have two vectors storing the ancestors of x and y respectively along with their depth.

    LCA would be common ancestor with greatest depth. Depth is defined as longest distance from root(vertex with in_degree=0).
    * Now, we can sort the vectors in decreasing order of their depths and find out the LCA.
    * Using this method, we can even find multiple LCA's (if there).

 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SAP {

    private final Digraph Gp;
    private LRUcache cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("argument is null");

        // Deep copy of graph for making SAP immutable
        Gp = new Digraph(G);
        cache = new LRUcache();

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return findSA(v, w).get(1);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return findSA(v, w).get(0);
    }


    private ArrayList<Integer> findSA(int v, int w) {
        /* validate vertices */
        validateVertex(v);
        validateVertex(w);

        /* code to check if these paths are already cached */
        ArrayList<Integer> p = new ArrayList<Integer>();
        ArrayList<Integer> q = new ArrayList<Integer>();

        p.add(v);
        p.add(w);

        q.add(w);
        q.add(v);

        if (cache.containsKey(p)) return cache.get(p);
        if (cache.containsKey(q)) return cache.get(q);

        ArrayList<Integer> res = new ArrayList<Integer>();
        /* if the path is not cached perform bfs */
        Queue<Integer> sv = new Queue<Integer>();
        Queue<Integer> sw = new Queue<Integer>();
        sv.enqueue(v);
        sw.enqueue(w);

        DeluxeBFS bfs = new DeluxeBFS(Gp, sv, sw);

        int dist = bfs.shortestlength();
        int sa = bfs.Commonanestor();

        // System.out.println(sa + " " + dist);

        res.add(sa);
        res.add(dist);
        /* code to cache the Vertices, length and ancestor */
        cache.put(p, res);
        cache.put(q, res);

        //System.out.println(cache.entrySet());

        return res;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        DeluxeBFS bfs = new DeluxeBFS(Gp, v, w);
        int dist = bfs.shortestlength();
        int sa = bfs.Commonanestor();

        //System.out.println(sa + " " + dist);

        return dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        DeluxeBFS bfs = new DeluxeBFS(Gp, v, w);
        int dist = bfs.shortestlength();
        int sa = bfs.Commonanestor();

        // System.out.println(sa + " " + dist);

        return sa;

    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= Gp.V())
            throw new IllegalArgumentException(
                    "vertex " + v + " is not between 0 and " + (Gp.V() - 1));
    }


    /* Implementation of Least recently used cache for vertices, length and ancestor */
    private static class LRUcache extends LinkedHashMap<ArrayList<Integer>, ArrayList<Integer>>  {
        private static final int MAX_ENTRIES = 100;
        private static final long serialVersionUID = 1L;

        /* initialising with access-order  */
        public LRUcache() {
            super(MAX_ENTRIES, 0.75f, true);
        }

        /* remove the oldest entry when max size is reached */
        @Override
        public boolean removeEldestEntry(
                Map.Entry<ArrayList<Integer>, ArrayList<Integer>> eldest) {
            return size() > MAX_ENTRIES;
        }
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        SAP sap = new SAP(G);

        Queue<Integer> a = new Queue<Integer>();
        Queue<Integer> b = new Queue<Integer>();


        a.enqueue(23357);
        a.enqueue(48835);
        a.enqueue(60186);

        b.enqueue(13466);
        b.enqueue(15953);
        b.enqueue(33677);
        b.enqueue(40746);
        b.enqueue(44243);
        b.enqueue(46244);
        b.enqueue(48435);
        b.enqueue(51809);
        b.enqueue(75164);
        b.enqueue(79097);
        b.enqueue(81476);


        int length = sap.length(a, b);
        int ancestor = sap.ancestor(a, b);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

       /*while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();

            int length = sap.length(v, w);
            // System.out.println(length);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }*/


    }
}
