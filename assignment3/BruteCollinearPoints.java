import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null");
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i] == null || points[j] == null || points[k] == null
                                || points[l] == null) {
                            throw new IllegalArgumentException("Null point detected");
                        }
                        if (points[i].compareTo(points[j]) == 0
                                || points[i].compareTo(points[k]) == 0 ||
                                points[i].compareTo(points[l]) == 0
                                || points[j].compareTo(points[k]) == 0 ||
                                points[j].compareTo(points[l]) == 0
                                || points[k].compareTo(points[l]) == 0) {
                            throw new IllegalArgumentException("Repeated points detected");
                        }
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            Point[] segmentPoints = { points[i], points[j], points[k], points[l] };
                            Arrays.sort(segmentPoints);
                            segments.add(new LineSegment(segmentPoints[0], segmentPoints[3]));
                        }
                    }
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

        // Create BruteCollinearPoints object with the points array
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        // Print the number of segments
        StdOut.println("Number of segments: " + collinear.numberOfSegments());

        // Print and draw the line segments
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
