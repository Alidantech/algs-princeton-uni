import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null");
        }

        // Create a copy of the points array to work with
        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        int n = sortedPoints.length;

        for (int i = 0; i < n; i++) {
            Point p = sortedPoints[i];

            // Sort the points array based on slopes with respect to point p
            Arrays.sort(sortedPoints);

            // Sort the points array based on slopes with respect to point p
            Arrays.sort(sortedPoints, p.slopeOrder());

            // Calculate the collinear segments
            double currentSlope = Double.NEGATIVE_INFINITY;
            int count = 1;

            for (int j = 1; j < n; j++) {
                double slope = p.slopeTo(sortedPoints[j]);

                if (slope == currentSlope) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        Point minPoint = sortedPoints[j - count];
                        Point maxPoint = sortedPoints[j - 1];

                        if (p.compareTo(minPoint) < 0) {
                            segments.add(new LineSegment(p, maxPoint));
                        }
                    }

                    count = 1;
                    currentSlope = slope;
                }
            }

            if (count >= 3) {
                Point minPoint = sortedPoints[n - count];
                Point maxPoint = sortedPoints[n - 1];

                if (p.compareTo(minPoint) < 0) {
                    segments.add(new LineSegment(p, maxPoint));
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // Read the number of points
        int n = StdIn.readInt();

        // Read the points from standard input
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        // Create FastCollinearPoints object with the points array
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        // Print the number of segments
        StdOut.println("Number of segments: " + collinear.numberOfSegments());

        // Print and draw the line segments
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
