/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();

    private int linesegcnt = 0;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {


        if (points == null) throw new IllegalArgumentException("argument is null");
        int n = points.length;
        if (n == 0) throw new IllegalArgumentException("array is of length 0");

        Point[] a = new Point[n];

        // Check if any points are null and copying to another array
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points[" + i + "] is null");
            a[i] = points[i];
        }

        Arrays.sort(a);

        // Checking for duplicates
        for (int i = 0; i < n - 1; i++) {
            if (a[i].compareTo(a[i + 1]) == 0.0)
                throw new IllegalArgumentException("points[" + i + "] is having a duplicate");
        }

        int cnt = 0;
        ArrayList<Point> p = new ArrayList<Point>();
        while (cnt < n) {

            Arrays.sort(a, 0, n, points[cnt].slopeOrder());
            p.clear();

            for (int j = 1; j < n - 1; j++) {
                if (a[0].slopeTo(a[j]) == a[0].slopeTo(a[j + 1])) {
                    if (!p.contains(a[j])) p.add(a[j]);
                    p.add(a[j + 1]);
                }
                else {
                    if (p.size() >= 3) {
                      addlineSeg(a[0],  p);
                    }
                    p.clear();
                }
            }
            if (p.size() >= 3) {
                 addlineSeg(a[0],  p);
            }
            cnt++;

        }
    }

    private void addlineSeg(Point origin, ArrayList<Point> pt){
        Point[] pnt = new Point[pt.size()];
        pnt = pt.toArray(pnt);
        Arrays.sort(pnt);
        if(origin.compareTo(pnt[0]) == -1){
            LineSegment x = new LineSegment(origin, pnt[pt.size() - 1]);
            ls.add(x);
        }
    }

    // the number of line segments0.0
    public int numberOfSegments() {
        return ls.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] l = new LineSegment[linesegcnt];
        l = ls.toArray(l);
        return l;
    }



    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.002);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
