public class PercolationQuickFind {

    //region private props
    private class Site {
        private int Value;

        public int getSiteStatus() {
            return SiteStatus;
        }

        public void setSiteStatus(int siteStatus) {
            SiteStatus = siteStatus;
        }

        private int SiteStatus;


        public int getValue() {
            return Value;
        }

        public void setValue(int value) {
            Value = value;
        }
    }

    private Site[][] sites;

    private static int CloseSite = 0;
    private static int OpenSite = 1;

    //endregion

    //region private methods

    //the find method in quick-find algorithms
    private boolean connected(int p1x, int p1y, int p2x, int p2y) {
        return sites[p1x][p1y].Value == sites[p2x][p2y].Value;
    }

    //connect sites
    private void union(int p1x, int p1y, int p2x, int p2y) {
        int p1Value = sites[p1x][p1y].Value, p2Value = sites[p2x][p2y].Value;

        if (p1Value == p2Value || sites[p2x][p2y].SiteStatus == CloseSite) return;
        for (int i = 0; i < sites.length; i++) {
            for (int j = 0; j < sites.length; j++) {
                if (sites[i][j].Value == p1Value) {
                    sites[i][j].Value = p2Value;
                }
            }
        }
    }

    //endregion


    //region Public Functions
    // create n-by-n grid, with all sites blocked
    public PercolationQuickFind(int n) {
        if (n <= 0) throw new IllegalArgumentException("the n should large than 0");
        int number = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                Site site = new Site();
                site.setValue(number);
                site.setSiteStatus(CloseSite);
                sites[i][j] = site;
            }
        }
    }

    // open site (row, col) if it is not open already
    //connect to neighbor open site
    public void open(int row, int col) {
        boolean isHasBeenOpen = sites[row][col].SiteStatus == OpenSite;
        if (isHasBeenOpen) return;

        sites[row][col].SiteStatus = OpenSite;

        if (row != 0) {
            connected(row, col, row - 1, col);
        }

        if (row != sites.length - 1) {
            connected(row, col, row + 1, col);
        }

        if (col != 0) {
            connected(row, col, row, col - 1);
        }

        if (col != sites.length - 1) {
            connected(row, col, row, col + 1);
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[row][col].SiteStatus == OpenSite;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    //test client (optional)
    public static void main(String[] args) {
        PercolationQuickFind percolationQuickFind = new PercolationQuickFind(10);
        int a = 1;
    }

    //endregion
}