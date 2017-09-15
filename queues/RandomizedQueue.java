/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       09/16/2016
 * 
 * a generic data type that supports adding and removing items form queue in 
 * random lications. uses a resizing array implementation. 
------------------------------------------------------------------------------*/
import java.util.Iterator;
import java.util.Arrays;
// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked") 
public class RandomizedQueue<Item> implements Iterable<Item>
{
    // start, end, and size of queue inside of the array
    private int head, tail, n;
    // the array itself
    private Item[] q;
    // construct an empty randomized queue
    
    public RandomizedQueue()                 
    {
        // create a new item array with a capacity of two 
        q = (Item[]) new Object[1]; // throws warning, its ok
        // initalize the head and tail postitons
        head = 0;
        tail = 0; 
        // initalize size of queue
        n = 0;
        
    }
    // is the queue empty?
    public boolean isEmpty()                 
    {   return (n == 0);   }
    // return the number of items on the queue
    public int size()                        
    {   return n;   }
    // add the item
    public void enqueue(Item item)           
    {
        // make sure item isnt null
        if (item == null)
        {   
            throw new java.lang.NullPointerException("Can not add null item");
        }
        // fill frount to back 
        q[tail++] = item;
        // increment size of queue
        n++;
        // if the array is full resize 
        if (tail == q.length)
        {   resize(2 * q.length);   }
    }
    // remove and return a random item
    public Item dequeue()                    
    {
        // make sure queue isnt empty
        if (isEmpty())
        {   
            throw new java.util.NoSuchElementException("queque is empty");
        }
        // generate a rand int [head,tail)
        int randIndex = StdRandom.uniform(head, tail);
        Item item = q[randIndex];       
        // concatinate q before rand index + q after index + null 
        // the empty slot left after removing the item
        Item[] blank = (Item[]) new Object[1]; 
        Item[] frount = Arrays.copyOfRange(q, 0, randIndex);
        Item[] back = Arrays.copyOfRange(q, randIndex + 1, q.length);
        // merge blank space with frount 
        Item[] temp = merge(frount, back);
        // merge frount with back
        Item[] removed = merge(temp, blank);
        // overwrite old array
        q = removed;
        // null added to back, decrement tail  
        tail--;
        // decrement size of queue
        n--;
        // half size if at or below 1/4 capacity
        if (n*4 <= q.length) // done to avoid casting to float
        {
            resize(q.length/2); // deviding two ints, may cause bug
        } 
        return item;
    }
    // return (but do not remove) a random item
    public Item sample()                     
    {
        // make sure queue isnt empty
        if (isEmpty())
        {   
            throw new java.util.NoSuchElementException("queque is empty");
        }
        // generate a rand int [head,tail)
        int randIndex = StdRandom.uniform(head, tail);
        // return item at that index 
        return q[randIndex];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator()         
    {   return new ArrayIterator();   }
    // i still dont understand Iterators
    private class ArrayIterator implements Iterator<Item>
    {
        // starts at begining of queue
        private int i = head;
        
        public boolean hasNext()
        {   return i != tail;   }
        public void remove()
        {
            // not supported
            throw new UnsupportedOperationException(
                 "remove() is not a supported operation");
        }
        public Item next()
        {  
            if (hasNext())
            {   return q[i++];   }
            // no next. throw error
            throw new java.util.NoSuchElementException(
                 "No more items in iteration");
        } 
    }
    // resize the array if it becomes to full or empty
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity]; // throws warning, is ok
        // move copy values form q to copy 
        for (int i = 0; i < n; i++)
        {   copy[i] = q[i];   }
        // change where head and tail pointers are
        head = 0;
        tail = n;        
        // overwrite old array
        q = copy;
    }
    // merge two arryas together
    private Item[] merge(Item[] first, Item[]last)
    {   // may need to re write this. can be done with 2 for loops
        // new array to hold both
        Item[] both = (Item[]) new Object[first.length + last.length];
        // copy firt array into the first bit of "both" 
        System.arraycopy(first, 0, both, 0, first.length);
        // copy final array starting where the first one ended 
        System.arraycopy(last, 0, both, first.length, last.length);
        return both;
    }
    // unit testing------------------------------------------------------------
//    public static void main(String[] args)   
//    {
////        RandomizedQueue<String> randoQ = new RandomizedQueue<String>();
////        // add to queue
////        randoQ.enqueue("TeSt1");
////        randoQ.enqueue("TeSt2"); // should cause resize
////        randoQ.enqueue("TeSt3");
////        randoQ.enqueue("TeSt4"); // should cause resize
////        randoQ.enqueue("TeSt5");
////        // hopefuly print
////        StdOut.println(randoQ.dequeue());
////        StdOut.println(randoQ.dequeue());
////        StdOut.println(randoQ.dequeue());
////        StdOut.println(randoQ.dequeue());
//////        StdOut.println(randoQ.head);
//////        StdOut.println(randoQ.tail);
////        StdOut.println(randoQ.dequeue());
////        // how test Iterator?
//    }

}