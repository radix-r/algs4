/*************************************************************************
 *  
 *
 * takes an array of point objects as input then outputs an array of line 
 * segments
 *
 *  
 *
 *************************************************************************/
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints{
    // a bag to put line segments into
    private Stack<LineSegment> lines = new Stack<LineSegment>();
    
    /**
     * 
     * go through points and put line segments into a stack
     * 
     */
    public FastCollinearPoints(Point[] points)
    {
        // check for null points 
        checkNull(points);
        // sort based on coordinates for easy duplicate check
        Arrays.sort(points);
        checkDuplicate(points);
        // 
        Point[] forSlope = points;
        int n = points.length;
        
        // go through each point
        for (int i = 0; i < n; i++)
        {
            // 
            Arrays.sort(forSlope);
            // sort forSlope based on slope with respect to the current point.
            Arrays.sort(forSlope, points[i].slopeOrder());

            // go through eack values in forSlope.  
            for (int j = 0; j < n-1; j++)
            {
                // stack to hold collinear points
                Stack<Point> collinearStack = new Stack<Point>();
               
                collinearStack.push(points[i]); // start with first 2 points
                // collinearStack.push(forSlope[0]);
                
                collinearStack.push(forSlope[j]);
                // check slope with relation to points[i] if adjacent values 
                // share slope continue looking to next value untill slopes dont 
                // match or end of array is reached
                while ( (j < n-1) && (points[j].compareTo(forSlope[j+1]) < 0) && ((points[i].slopeTo(forSlope[j])) == (points[i].slopeTo(forSlope[j+1]))) )
                {
                    collinearStack.push(forSlope[++j]);
                }
                // number of collinear points found
                int collinearPoints = collinearStack.size();
                
                
                // if chain is 4 or bigger add to stack "lines"    
                if (collinearPoints >= 4)
                {
                    // array to dump colinear points into for sorting 
                    Point[] collinearArray = new Point[collinearPoints];
                    // dump collinear points into an array
                    int z = 0; // index for array
                    for (Point p: collinearStack)
                     { 
                         collinearArray[z] = p;
                         z++;
                     }
                    
                    // sort array of colinear points 
                    Arrays.sort(collinearArray);
                    // first and last points define line segment
                    Point start = collinearArray[0];
                    Point end = collinearArray[collinearArray.length-1]; 
                    // create a line 
                    LineSegment potentalLine = new LineSegment(start, end);
                    // test uniqueness
                    if (uniqueSegment(potentalLine))
                    {
                        // add to stack if unique
                        lines.push(potentalLine);
                    }
                }
            }
        }
    }
    
    
    /**
     * returns number of line segments in bag
     */
    public int numberOfSegments()
    {
        return lines.size();
    }
    
    /**
     * returns an array of line segments in stack
     */
    public LineSegment[] segments()
    {
        // should be just like in the brute alg
        int size = numberOfSegments();
        int i = 0;
        LineSegment[] segments = new LineSegment[size];
        // iterate through stack and put item into array
        for (LineSegment s : lines)
        {
            segments[i] = s;
            i++;
        }
        return segments;
    }
    
    /**
     * 
     * go through array and throws an exeption if a null value is found
     * 
     */
    private void checkNull(Point[] p)
    {
        for (int i = 0; i < p.length; i++)
        {
            if (p[i] == null)
            { throw new NullPointerException("argument is null"); }
        }
    }

    /** 
     * 
     * takes in a sorted point array
     * check array for duplicate values
     * throws exeption if duplicate found
     */
    private void checkDuplicate(Point[] p)
    {
        int len = p.length;
        for(int i = 0; i < len-1; i++)
        {
             if (p[i].compareTo(p[i+1]) == 0)
             { throw new java.lang.IllegalArgumentException("argument is a duplicate"); }
         }
    }
    
    /**
     * 
     * returns true if line segment is not already in the bag
     * 
     */
    private boolean uniqueSegment(LineSegment seg)
    {
        for (LineSegment s : lines)
        {
            // bad. relies on toString()
            if (s.toString().compareTo(seg.toString()) == 0){ return false; }
            // if (s.p == seg.p) {return false;}
        }
        return true;
    }

    
    // testing
    public static void main(String[] args)
    {
        
//         StdOut.println("Line slope 1 test");
//        Point[] line = {new Point(2682, 14118), new Point (5067, 14118), new Point (7453, 14118), new Point (7821, 14118)};
//        FastCollinearPoints test5 = new FastCollinearPoints(line);
//        int n = test5.numberOfSegments();
//        // error say 3 lines in sted of 2 or not?--------------------------------
//        LineSegment[] e = test5.segments(); 
//        for (int i = 0; i < n; i++)
//        {
//            StdOut.println(e[i].toString());
//        }
//        
//        StdOut.println("Horizontal test");
//        Point[] horizontal = {new Point(0,0), new Point(2,0), new Point (1,0), new Point (3,0)};
//        FastCollinearPoints test1 = new FastCollinearPoints(horizontal);
//        StdOut.println(test1.numberOfSegments());
        
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