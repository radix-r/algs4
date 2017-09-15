/*------------------------------------------------------------------------------
 * Author:        Ross Wagner
 * Written:       09/08/2016
 * 
 * what the code does goes here
------------------------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import package.Percolation;
// import java.util.*;
    
public class PercolationStats
{
    // system to percolate
    private Percolation perc;
    // contains a list of open sites devided by total sites 
    private double[] fractionList;
    // number of trials done;
    private int t = 0;
    // average of percolation threshholds
    private double avg = 0.0;
    // standard deviation of the distribution of percolation threshholds
    private double std = 0.0;
    // high an low 95% confidence values
    private double confHi = 0.0;
    private double confLo = 0.0;
    
    // preform trials independent experements on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException("Both inputs must be > 0 ");
        }
        // holds fraction values untill they can be bassed to more permenent storage
        double[] tempFractions = new double[trials];
        // do Monte Carlo simulation to estimate perc threshold
        for (int k = 0; k < trials; k++)
        {
            // put in more permenent location
            t = trials;
             
            // initalize new system
            perc = new Percolation(n);
            // holds number of open sites in system
            int openSites = 0;
            int totalSites = n*n;
            // open random sites untill system percolates
            while (!perc.percolates())
            {
                // pick random coordinates
                int i = StdRandom.uniform(1, n+1); // row
                int j = StdRandom.uniform(1, n+1); // column
                // if site is closed open it
                if (!perc.isOpen(i, j))
                {
                    perc.open(i, j);
                    openSites++;
                }
            }
            // system percolates. Add fraction open/total to list
            tempFractions[k] = (double) openSites/totalSites;
        }
        // move to more permenent location
        fractionList = tempFractions;
        // compute statistical data
        mean();
        stddev();
        confidenceLo();
        confidenceHi();
    }
    // sample mean of percolation threshhold
    public double mean()
    {
        // put average in more permenent location
        avg = StdStats.mean(fractionList);
        return avg;
    }
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        std = StdStats.stddev(fractionList);
        return std;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
         if (avg > 0 && std > 0)
        {
            confLo = avg-(1.96*std)/(Math.sqrt(t));
        }
         return confLo;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        if (avg > 0 && std > 0)
        {
            confHi = avg+(1.96*std)/(Math.sqrt(t));
        }
        return confHi;
    }
    
    public static void main(String[] args)
    {
        // user input variables
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        // ask for user input
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = "+stats.avg);
        System.out.println("stddev                  = "+stats.std);
        System.out.println("95% confidence interval = "+stats.confLo +", "+stats.confHi);
        
    }
}