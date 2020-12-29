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

public class BruteCollinearPoints {

    private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
    private int linesegcnt = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {



        if (points == null) throw new IllegalArgumentException("argument is null");
        int n = points.length;
        if (n == 0) throw new IllegalArgumentException("array is of length 0");

        Point[] a = new Point[n];

        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points[" + i + "] is null");
            a[i] = points[i];
        }

        Arrays.sort(a);

        for (int i = 0; i < n - 1; i++) {
            if (a[i].compareTo(a[i + 1]) == 0.0)
                throw new IllegalArgumentException("points[" + i + "] is having a duplicate");
        }

        Point Pend = null;
        for (int i = 0; i < n - 3; i++) {
            Point end = a[i];
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    if (a[i].slopeTo(a[j]) != a[i].slopeTo(a[k])) continue;
                    int cnt = 0;
                    for (int l = k + 1; l < n; l++) {
                        if (a[i].slopeTo(a[j]) == a[i].slopeTo(a[l])) {
                            end = a[l];
                            cnt++;
                        }
                    }
                    if (cnt > 0) {
                        if (end != Pend) {
                            ls.add(new LineSegment(a[i], end));
                            linesegcnt++;
                        }
                        Pend = end;
                    }
                }
            }

        }

    }

    // the number of line segments    
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
