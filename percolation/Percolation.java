/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       09/07/2016
 * 
 * what the code does goes here
------------------------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    // uninon find data type, represents the system that can percolate
    private WeightedQuickUnionUF uf;
    // index for virtual top. will be used in percolation method
    private int virtualTopIndex = 0;
    // index for virtual bottem. will be used in percolation method
    private int virtualBottemIndex;
    // size of system
    private int size;
    // true if site is open false if site is full
    private boolean[] openSites;
    /*
     * create n-by-n grid with all sites blocked
     */
    public Percolation(int n)          
    {   // constructor
        if (n <= 0)
        {
            throw new IllegalArgumentException(n+" is not a vaild value");
        }
        // put n into more permenent location
        size = n;
        // default boolean is false
        openSites = new boolean[n*n+1]; // +1 b/c numbering starts at 1 not 0
        // row and column indices i and j are integers between 1 and n
        uf = new WeightedQuickUnionUF(n*n+2); // instatiate the system
        virtualBottemIndex = n*n+1; // set virtual bottem to last index
//        // connect top and bottem rows to virtual top and bottem
//        for (int i = 1; i <= n; i++)
//        {
//            // connect top row to virtual top
//            uf.union(virtualTopIndex, i);
//            // sonnect bottem row to virtual bottem
//            uf.union(virtualBottemIndex, coordinatesToIndex(n, i)); 
//        }
    }
    // open site (row i, column j) if not done already
    public void open(int i, int j)       
    {
        // validate coordinates
        validate(i);
        validate(j);
        // connect to adjcent open sites
        if (!isOpen(i, j))
        {
            int start = coordinatesToIndex(i, j);
            int up = start;
            int down = start;
            int left = start;
            int right = start;
            // set current site to open
            openSites[start] = true;
            // make sure dosent go out of bounds
            // connect if adjcent objs also open
            if (i == 1) // top row to virtual top
            {
                uf.union(start, virtualTopIndex);
            }
            if (j > 1) // left
            {   
                left = coordinatesToIndex(i, j-1);
                if (isOpen(i, j-1))
                {   uf.union(start, left);   }
            }
            if (j < size) // down
            {   
                right = coordinatesToIndex(i, j+1);
                if (isOpen(i, j+1))
                {    uf.union(start, right);   }
            }
            if (i > 1) // up
            {   
                up = coordinatesToIndex(i-1, j);
                if (isOpen(i-1, j))
                {   uf.union(start, up);   }
            }
            if (i < size) // down
            {   
                down = coordinatesToIndex(i+1, j);
                if (isOpen(i+1, j))
                {   uf.union(start, down);   }
            }
             if (i == size) // bottem row to virtual bottem
            {
                uf.union(start, virtualBottemIndex);
            }
        }
    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) 
    {   
        // validate coordinates
        validate(i);
        validate(j); 
        // check boolean array at location i,j
        return openSites[coordinatesToIndex(i, j)];
    }
    // is site (row i, column j) full? connected to virtual top?
    public boolean isFull(int i, int j) 
    {   // issue with backwash-------------------------------------------------
        // validate coordinates
        validate(i);
        validate(j);
        int index = coordinatesToIndex(i, j);
        return uf.connected(virtualTopIndex, index); // may need fix here  
    }
    // dose the system percolate?
    public boolean percolates()         
    {
        return uf.connected(virtualTopIndex, virtualBottemIndex);
    }
    // converts i, j coordinates int index number of uf
    private int coordinatesToIndex(int i, int j)
    {
        return ((i-1)*size+j);
    }
    // confirms that int k is in bounds
    private void validate(int k)
    {
        if (k <= 0 || k > size)
        {
            throw new IndexOutOfBoundsException("coordinate "+k+" is not between 0 and "+size);
        }
    }   
//    public static void main(String[] args)
//    {
//        // test code here
//        Percolation percolation = new Percolation(6); // works so far
////        for (int i = 1; i <= 5; i++)
////        {
////            percolation.open(i, 1); // opens entire left column. for testing reasons
////        }
//        percolation.open(1, 1);
//        boolean test = percolation.percolates();
//        System.out.println(test);
//    }
}