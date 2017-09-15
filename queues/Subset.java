/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       09/16/2016
 * 
 * takes a command-line integer k; reads in a sequence of N strings from 
 * standard input using StdIn.readString(); and prints out exactly k of them, 
 * uniformly at random. Each item from the sequence can be printed out at most 
 * once
 * 
------------------------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset
{
    public static void main(String[] args)
    {
        // number of inputs to print
        // StdOut.println("Enter number of items to print");
        int prints = Integer.parseInt(args[0]);
        // StdOut.println(prints);
        // new RandomizedQueue object
        RandomizedQueue<String> randoQ = new RandomizedQueue<String>();
        // get the first input
        // StdOut.println("Enter first item: ");
        String input; // = StdIn.readString();
        // boolean empty = false;
        while (!StdIn.isEmpty())
        {            
            // get the next input
            input = StdIn.readString();
            // add inputs to queue
            randoQ.enqueue(input);
            // StdOut.println(input+ " added to queue");
            // empty = StdIn.isEmpty();
        }
        // print "prints" random items from queue
        for (int i = 0; i < prints; i++)
        {
            StdOut.println(randoQ.dequeue());
        }
    }
}