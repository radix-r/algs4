/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       
 * 
 * what the code does goes here
------------------------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;


public class Rec
{
    public static int g(int n)
    {
        if(n == 0) return 0;
        int x = ( n - g ( g ( n - 1 ) ) ) ;
        // StdOut.println(x);
        return x;
    }
       
    public static void main(String[] args)
    {
       StdOut.println(g(9)); 
    }
}