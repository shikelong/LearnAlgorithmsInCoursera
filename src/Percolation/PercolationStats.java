package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double _openSitesPercentWhenPercolates[];
    private int _trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        _trials = trials;
        for (int i = 0; i < trials; i++) {
            PercolationQuickFind percolationQuickFind = new PercolationQuickFind(n);

            do {
                percolationQuickFind.open(StdRandom.uniform(0, n), StdRandom.uniform(0, n));
            } while (!percolationQuickFind.percolates());

            int openSites = percolationQuickFind.numberOfOpenSites();
            _openSitesPercentWhenPercolates[i] = openSites / (n * n);
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
        PercolationStats percolationStats = new PercolationStats(10, 3);
        int a = 1;
    }
}