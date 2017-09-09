package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double _openSitesPercentWhenPercolates[];

    private int _trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        _trials = trials;
        _openSitesPercentWhenPercolates = new double[trials];
        for (int i = 0; i < trials; i++) {
            //PercolationQuickFind percolationIns = new PercolationQuickFind(n);
            PercolationWeightedQuickUnion percolationIns = new PercolationWeightedQuickUnion(n);
            do {
                int x = StdRandom.uniform(0, n), y = StdRandom.uniform(0, n);
                percolationIns.open(x, y);
            } while ((!percolationIns.percolates()) && (percolationIns.numberOfOpenSites() < n * n));


            int openSites = percolationIns.numberOfOpenSites();
            _openSitesPercentWhenPercolates[i] = openSites / ((double) n * n);
        }

    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(_openSitesPercentWhenPercolates);
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(_openSitesPercentWhenPercolates);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(_trials);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(_trials);
    }
    // test client (described below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(15, 15);
        System.out.println("mean: " + percolationStats.mean());
        System.out.println("stddev: " + percolationStats.stddev());
        System.out.println("Lo: " + percolationStats.confidenceLo());
        System.out.println("Hi: " + percolationStats.confidenceHi());
    }
}