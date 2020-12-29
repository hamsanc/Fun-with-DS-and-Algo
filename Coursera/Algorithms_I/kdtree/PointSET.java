/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> pset;

    // construct an empty set of points
    public PointSET() {
        pset = new TreeSet<Point2D>();
    }

    // is the set empty? 
    public boolean isEmpty() {
        return pset.isEmpty();
    }

    // number of points in the set 
    public int size() {
        return pset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        if (!contains(p))
            pset.add(p);
    }


    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        return pset.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : pset)
            StdDraw.point(p.x(), p.y());
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        ArrayList<Point2D> rp = new ArrayList<Point2D>();
        for (Point2D pt : pset) {
            if (rect.contains(pt)) {
                rp.add(pt);
            }
        }
        return rp;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");

        if (this.isEmpty()) return null;
        boolean first = true;
        Point2D champ_pt = null;
        double champ_r = 0.0;
        for (Point2D pt : pset) {

            if (first) {
                champ_r = p.distanceSquaredTo(pt);
                champ_pt = pt;
                first = false;
            }
            else {
                double r = p.distanceSquaredTo(pt);
                if (r < champ_r) {
                    champ_r = r;
                    champ_pt = pt;
                }
            }

        }
       //System.out.println("Brute Nearest: "+champ_pt);
        return champ_pt;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        brute.draw();
        StdDraw.show();
    }
}
