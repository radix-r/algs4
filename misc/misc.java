/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       
 * 
 * what the code does goes here
------------------------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;


public class Misc
{
    public int g(int n)
    {
        if(n == 0) return 0;
        else return ( n - g ( g ( n - 1 ) ) ) ;
    }
       
    public static void main(String[] args)
    {
       StdOut.println(g(5)); 
    }
}