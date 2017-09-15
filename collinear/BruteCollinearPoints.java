/*************************************************************************
 *  
 *
 * takes an array of point objects as input then outputs an array of line 
 * segments
 *
 *  
 *
 *************************************************************************/
import edu.princeton.cs.algs4.Bag;
import java.util.Arrays;
// debuging
import edu.princeton.cs.algs4.StdOut;

    
public class BruteCollinearPoints {
    
    // bag of line segments maybe
    private Bag<LineSegment> lines = new Bag<LineSegment>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {  // may be ligic error in here 
       // check for null and duplicate points
       checkNull(points);
       checkDuplicate(points);
       // sort based on coordinates
       Arrays.sort(points);
       int n = points.length;
       for (int i = 0; i < n; i++) // first point
           for(int j = i+1; j < n; j++) // second point
           {
               double goalSlope = points[i].slopeTo(points[j]);
               for (int k = j+1; k < n; k++) // third point
               {
                   if (points[j].slopeTo(points[k]) == goalSlope)
                   {
                       for (int l = k+1; l < n; l++) // fourth point
                       {
                           if(points[k].slopeTo(points[l]) == goalSlope)
                           {
                               // makes sure line segment is unique
                               LineSegment seg = new LineSegment(points[i],points[l]);
                               if (uniqueSegment(seg))                                  
                               { lines.add(seg); } // add to bag of lines
                           }
                       }                          
                   }
               }
           }
    }
    // the number of line segments
    public int numberOfSegments()        
    {
        return lines.size();
    }
    // the line segments
    public LineSegment[] segments()                
    {
        int size = numberOfSegments();
        int i = 0;
        LineSegment[] segments = new LineSegment[size];
        // iterate through bag and put item into array
        for (LineSegment s : lines)
        {
            segments[i] = s;
            i++;
        }
        return segments;
    }
    
    // go through array check for null values
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
     * check array for duplicate values
     * 
     * count optimize b/c sorting
     */
    private void checkDuplicate(Point[] p)
    {
        int len = p.length;
        for(int i = 0; i < len; i++)
            for (int j = i + 1; j < len; j++)
            { 
                if (p[i].compareTo(p[j]) == 0)
                { throw new java.lang.IllegalArgumentException("argument is a duplicate"); }
            }
        
    }
    
    // returns true if line segment is not already in the bag
    private boolean uniqueSegment(LineSegment seg)
    {
        for (LineSegment s : lines)
        {
            if (s.toString().compareTo(seg.toString()) == 0){ return false; }
        }
        return true;
    }
    
    
    // unit testing
    public static void main (String[] args)
    {
        StdOut.println("Horizontal test");
        Point[] horizontal = {new Point(0,0), new Point(2,0), new Point (1,0), new Point (3,0)};
        BruteCollinearPoints test1 = new BruteCollinearPoints(horizontal);
        StdOut.println(test1.numberOfSegments());
        
        StdOut.println("Vertical test");
        Point[] vertical = {new Point(0,0), new Point(0,2), new Point (0,1), new Point (0,3)};
        BruteCollinearPoints test2 = new BruteCollinearPoints(vertical);
        StdOut.println(test2.numberOfSegments());
        
        StdOut.println("Control");
        Point[] random = {new Point(0,0), new Point(3,12), new Point (80,12), new Point (6,3)};
        BruteCollinearPoints test3 = new BruteCollinearPoints(random);
        StdOut.println(test3.numberOfSegments());
        
        StdOut.println("Mixed test");
        Point[] mix = {new Point(3,12), new Point (80,12), new Point (6,3),new Point(2,0),new Point(0,0), new Point(0,2), new Point (0,1), new Point (0,3), new Point (1,0), new Point (3,0)};
        BruteCollinearPoints test4 = new BruteCollinearPoints(mix);
        StdOut.println(test4.numberOfSegments());
        
        StdOut.println("Line slope 1 test");
        Point[] line = {new Point(5,5), new Point(1,1), new Point (2,2), new Point (3,3),new Point(4,4)};
        BruteCollinearPoints test5 = new BruteCollinearPoints(line);
        int n = test5.numberOfSegments();
        // error say 3 lines in sted of 2 or not?--------------------------------
        LineSegment[] e = test5.segments(); 
        for (int i = 0; i < n; i++)
        {
            StdOut.println(e[i].toString());
        }
        
        
    }
}