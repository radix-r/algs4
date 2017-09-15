/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       09/13/2016
 * 
 * a generic data type that supports adding and removing items form either the 
 * front or back. uses a doubly linked list implementation. may not meet memory
 * requirements 
------------------------------------------------------------------------------*/
import java.util.Iterator;
// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;

// @SuppressWarnings("unchecked") 
public class Deque<Item> implements Iterable<Item>
{
    // seninal node for begining and end of deque
    private Node header, trailer;
    // size of deque  
    private int n;
    
    // inner class, what the linked list is made out of 
    private class Node
    {
        // value of node
        private Item item;
        // reff to next node in list
        private Node next;
        // reff to previous node
        private Node prev;
    }
    // construct an empty deque
    public Deque()
    {
        // initalize sentinal nodes
        trailer = new Node();
        header = new Node();
        header.next = trailer;
        trailer.prev = header;
        // initalize size
        n = 0;
      }
    // is the deque empty
    public boolean isEmpty()
    {   return header.next == trailer;   }
    // return the number of items in deque
    public int size()
    {   return n;   }
    // add item to the frount
    public void addFirst(Item item)
    {
        // make sure item isnt null
        if (item == null)
        {
            throw new java.lang.NullPointerException("Item value cant be null");
        }
        // create new node for new begining
        Node first = new Node();
        // set instance variables of new node
        first.item = item;
        first.next = header.next;
        header.next.prev = first;
        // link seninal node to new starting point
        first.prev = header;
        header.next = first;
        // increment the size
        n++;
    }
    // add item to back
    public void addLast(Item item)
    {
        // make sure item isnt null
        if (item == null)
        {
            throw new java.lang.NullPointerException("Item value cant be null");
        }
        // create new node for end
        Node last = new Node();
        // asign walue to the new item in the list
        last.item = item;
         // link new node to end of list
        trailer.prev.next = last;
        last.prev = trailer.prev;        
        // reff to sentenal node
        last.next = trailer;
        trailer.prev = last;
        // increment the size
        n++;
    }
    // remove and return the item form the frount
    public Item removeFirst()
    {
        // if empty throw exeption
        if (isEmpty())
        {   
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        // save item to return
        Item item = header.next.item;
        // loitering problem maybe?
        // sentinel node points to item two steps ahead 
        header.next = header.next.next;
        // set that items prev pointer to sentinel node   
        header.next.prev = header;
        // decrement the size
        n--;
        // return saved item
        return item;
    }
    // remove and return item from the back
    public Item removeLast()
    {
        // make sure the deque isnt epmty
        if (isEmpty())
        {   
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        // save item to be returned
        Item item = trailer.prev.item;
        // link new last to sentinel node
        trailer.prev = trailer.prev.prev;
        trailer.prev.next = trailer;
        // decrement the size
        n--;
        // return saved item
        return item;        
    }
    // return an iterator over items in order from frount to end
    public Iterator<Item> iterator()
    {   return new ListIterator();   }
    // Object that facilitates iteration
    private class ListIterator implements Iterator<Item>
    {
        // starts at the first item in list
        private Node current = header.next;       
        // returns false if there are no more items
        public boolean hasNext() 
        {   return current == trailer;   } 
        public void remove()
        {
            // not supported
            throw new UnsupportedOperationException(
                 "remove() is not a supported operation");
        }
        public Item next()
        {
            if (hasNext())
            {   
                Item item = current.item;
                current = current.next;
                return item;   
            }
            // next has been calld whem the deque is empty
            throw new java.util.NoSuchElementException(
                 "No more items in iteration");
        }
    }
    
    // unit testing-----------------------------------------------------------
//    public static void main (String[] args)
//    {
//        // so tests. how do i test this program?
//        Deque<String> deque = new Deque<String>();
//        deque.addFirst("First");        
//        //deque.removeFirst();
//        deque.addLast("Last");
//        //make sure there can be more than one deque
//        Deque<Integer> numbers = new Deque<Integer>();
//        numbers.addFirst(5);
//        numbers.addLast(10);
//        numbers.addFirst(4);
//        numbers.addLast(11);
//        Iterator itr = deque.iterator();
//        // how test iterator?
//        for (Integer num : itr)
//        {
//            StdOut.println("??");
//            for (String s : deque)
//            {
//                StdOut.println(num + s);
//            }
//        }
//    }
}