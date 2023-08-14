import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        else if (this.y > that.y || this.x > that.x) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY; // Degenerate line segment
            }
            else {
                return Double.POSITIVE_INFINITY; // Vertical line segment
            }
        }
        else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            return Double.compare(slope1, slope2);
        }
    }

    public static void main(String[] args) {
        // Set up the canvas
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);

        // Sample client code to test the Point class
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(5, 6);

        // Draw points
        p1.draw();
        p2.draw();
        p3.draw();

        // Draw line segments
        p1.drawTo(p2);
        p2.drawTo(p3);

        // Pause to view the drawing
        StdDraw.show();
    }
}
