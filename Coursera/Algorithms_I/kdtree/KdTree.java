/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {

    private Node root;             // root of KdTree
    private int size;

    private class Node {

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left, right;    // left and right subtrees
        private int orientation;      // 0 = vertical 1 = horizontal

        public Node(Point2D p, int orient, double xmin, double ymin, double xmax, double ymax) {
            this.p = p;
            this.orientation = orient;
            this.rect = new RectHV(xmin, ymin, xmax, ymax);
        }

    }


    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        if (!contains(p)) {
            root = put(root, p, 0, 0.0, 0.0, 1.0, 1.0);
            size++;
        }
    }

    private Node put(Node x, Point2D p, int orient, double xmin, double ymin, double xmax,
                     double ymax) {
        if (x == null) return new Node(p, orient, xmin, ymin, xmax, ymax);
        if (orient == 0) {
            int cmpx = Point2D.X_ORDER.compare(p, x.p);
            if (cmpx < 0) x.left = put(x.left, p, 1, x.rect.xmin(), x.rect.ymin(), x.p.x(),
                                       x.rect.ymax());
            else x.right = put(x.right, p, 1, x.p.x(), x.rect.ymin(), x.rect.xmax(),
                               x.rect.ymax());

        }
        else {
            int cmpy = Point2D.Y_ORDER.compare(p, x.p);
            if (cmpy < 0) x.left = put(x.left, p, 0, x.rect.xmin(), x.rect.ymin(), x.rect.xmax(),
                                       x.p.y());
            else x.right = put(x.right, p, 0, x.rect.xmin(), x.p.y(), x.rect.xmax(),
                               x.rect.ymax());
        }

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(root, p, 0) != null;
    }

    private Point2D get(Node x, Point2D p, int orient) {
        if (x == null) return null;
        if (orient == 0) {
            int cmpx = Point2D.X_ORDER.compare(p, x.p);
            if (cmpx < 0) return get(x.left, p, 1);
            else if (cmpx > 0) return get(x.right, p, 1);
            else {
                if (x.p.equals(p)) return x.p;
                return get(x.right, p, 1);
            }
        }
        else {
            int cmpy = Point2D.Y_ORDER.compare(p, x.p);
            if (cmpy < 0) return get(x.left, p, 0);
            else if (cmpy > 0) return get(x.right, p, 0);
            else {
                if (x.p.equals(p)) return x.p;
                return get(x.right, p, 0);
            }
        }

    }


    private Iterable<Node> levelOrder() {
        Queue<Node> nd = new Queue<Node>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            nd.enqueue(x);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return nd;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.clear();
        boolean first = true;

        for (Node nd : this.levelOrder()) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(nd.p.x(), nd.p.y());
            StdDraw.setPenRadius(0.005);
            if (nd.orientation == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(nd.p.x(), nd.rect.ymin(), nd.p.x(), nd.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(nd.rect.xmin(), nd.p.y(), nd.rect.xmax(), nd.p.y());
            }

        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        Queue<Point2D> pt = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            if (rect.intersects(x.rect)) {
                if (rect.contains(x.p)) {
                    pt.enqueue(x.p);
                }
                queue.enqueue(x.left);
                queue.enqueue(x.right);
            }

        }
        return pt;
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Parameter passed to insert is null");
        //System.out.println("------------------Start-------------");
        //System.out.println("Query Pt: " + p);
        Point2D champ_pt = root.p;
        double champ_r = root.p.distanceSquaredTo(p);

        champ_pt = nearestpt(root, p, root.p, champ_r);
        //System.out.println(champ_pt);
        return champ_pt;

    }


    private Point2D nearestpt(Node x, Point2D p, Point2D chamPt, double champr) {
        if (x == null) return null;

        Point2D Cpt = chamPt;
        Double Cpr = champr;
        int cmp = 0;


        if (x.rect.distanceSquaredTo(p) > Cpr) {
            return Cpt;
        }

        //System.out.println(x.p + "  " + chamPt + "  " + champr);
        if (x.orientation == 0) cmp = Point2D.X_ORDER.compare(p, x.p);
        else cmp = Point2D.Y_ORDER.compare(p, x.p);


        if (cmp < 0) {

            if (x.left != null) {
                double leftdist = x.left.p.distanceSquaredTo(p);
                if (leftdist < champr) {
                    Cpr = leftdist;
                    Cpt = x.left.p;
                }
                Cpt = nearestpt(x.left, p, Cpt, Cpr);

            }
            if (x.right != null) {
                double rightdist = x.right.p.distanceSquaredTo(p);
                double cp = Cpt.distanceSquaredTo(p);
                if (rightdist < cp) {
                    Cpr = rightdist;
                    Cpt = x.right.p;
                }else Cpr = cp;

                Cpt = nearestpt(x.right, p, Cpt,Cpr );
            }

        }
        else {
            if (x.right != null) {
                double rightdist = x.right.p.distanceSquaredTo(p);
                if (rightdist < champr) {
                    Cpr = rightdist;
                    Cpt = x.right.p;
                }
                Cpt = nearestpt(x.right, p, Cpt, Cpr);

            }

            if (x.left != null){
                double leftdist = x.left.p.distanceSquaredTo(p);
                double cp = Cpt.distanceSquaredTo(p);
                if (leftdist < cp) {
                    Cpr = leftdist;
                    Cpt = x.left.p;
                }else Cpr = cp;

                Cpt = nearestpt(x.left, p, Cpt,Cpr );
            }
        }


        return Cpt;

    }

    public static void main(String[] args) {


        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);

        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();


        Point2D query = new Point2D(0.6875, 0.59375);
        

        // draw all of the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        // draw in red the nearest neighbor (using brute-force algorithm)
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        brute.nearest(query).draw();
        StdDraw.setPenRadius(0.02);

        // draw in blue the nearest neighbor (using kd-tree algorithm)
        StdDraw.setPenColor(StdDraw.BLUE);
        kdtree.nearest(query).draw();
        StdDraw.show();
        StdDraw.pause(400);


    }
}
