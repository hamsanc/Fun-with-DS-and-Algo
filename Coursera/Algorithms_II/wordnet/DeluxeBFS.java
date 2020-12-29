/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;

public class DeluxeBFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int VER_V = 1;
    private static final int VER_W = 2;

    //Symbol table instead of array Vertices A
    ST<Integer, Integer> markedA = new ST<Integer, Integer>();
    ST<Integer, Integer> edgeToA = new ST<Integer, Integer>();
    ST<Integer, Integer> distToA = new ST<Integer, Integer>();

    //Symbol table instead of array Vertices b
    ST<Integer, Integer> markedB = new ST<Integer, Integer>();
    ST<Integer, Integer> edgeToB = new ST<Integer, Integer>();
    ST<Integer, Integer> distToB = new ST<Integer, Integer>();


    private int commonanestor;
    private int sl;
    private int graphsize;

    public DeluxeBFS(Digraph G, Iterable<Integer> v, Iterable<Integer> w) {

        graphsize = G.V();

        validateVertices(v);
        validateVertices(w);

        bfs(G, v, w);
    }

    // BFS from multiple sources
    private void bfs(Digraph G, Iterable<Integer> v, Iterable<Integer> w) {
        Queue<Integer> qv = new Queue<Integer>();
        Queue<Integer> qw = new Queue<Integer>();

        // Initialise commonanestor and shortest length
        commonanestor = -1;
        sl = -1;

        // initialise and enque V --- A
        for (int s : v) {
            markedA.put(s, VER_V);
            distToA.put(s, 0);
            qv.enqueue(s);
        }

        // initialise and enque W ----- B
        for (int s : w) {

            if (markedA.contains(s)) {
                commonanestor = s;
                sl = 0;
                return;
            }
            markedB.put(s, VER_W);
            distToB.put(s, 0);
            qw.enqueue(s);
        }

        while (true) {

            //System.out.println("Elements in the queue v  " + qv);
            //System.out.println("Elements in the queue w  " + qw);

            if (!qv.isEmpty()) {
                int vv = qv.dequeue();

                //System.out.println("Dequeued element v " + vv);
                //System.out.print("Adjacent elements ");

                for (int ad : G.adj(vv)) {
                    //System.out.print(ad + " ");
                    if (!markedA.contains(ad)) {
                        edgeToA.put(ad, vv);
                        distToA.put(ad, distToA.get(vv) + 1);
                        markedA.put(ad, VER_V);

                        //System.out.println("Distance V--A " + distToA.get(ad) + " ");

                        if (sl != -1 && distToA.get(ad) > sl) break;
                        qv.enqueue(ad);
                    }

                    if (markedB.contains(ad)) {
                        //System.out.println();
                        //System.out.println(distToB.get(ad)+" "+(distToA.get(vv)+1));

                        if (commonanestor == -1) {

                            //System.out.println("found something common first time in VV");

                            commonanestor = ad;
                            sl = distToB.get(ad) + distToA.get(vv) + 1;

                            //System.out.println("CA " + commonanestor);
                            //System.out.println("SL " + sl);
                        }
                        else {
                            //System.out.println("found something again in VV");

                            int len = distToB.get(ad) + distToA.get(vv) + 1;

                            //System.out.println(distToB.get(ad) + " " + distToA.get(vv) + " " + len);

                            if (len < sl) {
                                commonanestor = ad;
                                sl = len;

                                //System.out.println("CA " + commonanestor);
                                //System.out.println("SL " + sl);
                            }
                        }
                    }
                }

            }

            if (!qw.isEmpty()) {
                int ww = qw.dequeue();

                //System.out.println("Dequeued element ww " + ww);
                //System.out.print("Adjacent elements ");

                for (int ad : G.adj(ww)) {
                    //System.out.print(ad + " ");
                    if (!markedB.contains(ad)) {
                        edgeToB.put(ad, ww);
                        distToB.put(ad, distToB.get(ww) + 1);
                        markedB.put(ad, VER_W);

                        //System.out.println("Distance W--B" + distToB.get(ad) + " ");

                        if (sl != -1 && distToB.get(ad) > sl) break;
                        qw.enqueue(ad);
                    }

                    if (markedA.contains(ad)) {
                        //System.out.println();
                        //System.out.println(distToA.get(ad)+" "+(distToB.get(ww)+1));
                        if (commonanestor == -1) {

                            //System.out.println("found something common first time in WW");

                            commonanestor = ad;
                            sl = distToA.get(ad) + distToB.get(ww) + 1;

                            //System.out.println("CA " + commonanestor);
                            //System.out.println("SL " + sl);
                        }
                        else {
                            //System.out.println("found something again in WW");

                            int len = distToA.get(ad) + distToB.get(ww) + 1;

                            //System.out.println(distToA.get(ad) + " " + distToB.get(ww) + " " + len);

                            if (len < sl) {
                                commonanestor = ad;
                                sl = len;

                                //System.out.println("CA " + commonanestor);
                                //System.out.println("SL " + sl);
                            }
                        }
                    }
                }
            }

            //System.out.println();

            if (qv.isEmpty() && qw.isEmpty()) break;
        }
    }

    public int distTo(int v) {
        validateVertex(v);
        if (distToA.contains(v)) return distToA.get(v);
        else if (distToB.contains(v)) return distToB.get(v);
        else return -1;

    }


    public int Commonanestor() {

        return commonanestor;
    }

    public int shortestlength() {

        return sl;
    }


    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = graphsize;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
    }


    public static void main(String[] args) {

    }
}
