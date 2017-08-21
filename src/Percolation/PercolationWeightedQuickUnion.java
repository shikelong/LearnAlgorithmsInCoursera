package Percolation;

public class PercolationWeightedQuickUnion {

    //region private
    private class Site {
        private int Value;

        private int SiteStatus;

        private int Size;

        public Site(int value, int siteStatus) {
            Value = value;
            SiteStatus = siteStatus;
        }

        public int getSiteStatus() {
            return SiteStatus;
        }

        public void setSiteStatus(int siteStatus) {
            SiteStatus = siteStatus;
        }

        public int getValue() {
            return Value;
        }

        public void setValue(int value) {
            Value = value;
        }

        public int getSize() {
            return Size;
        }

        public void setSize(int size) {
            Size = size;
        }
    }

    private class Point {
        private int X;
        private int Y;

        public Point(int x, int y) {
            X = x;
            Y = y;
        }


        public int getX() {
            return X;
        }

        public void setX(int x) {
            X = x;
        }

        public int getY() {
            return Y;
        }

        public void setY(int y) {
            Y = y;
        }
    }

    private Site[][] sites;

    private static int CloseSite = 0;
    private static int OpenSite = 1;

    private int OpenSiteCounts = 0;

    private Site _virtualTopSite = new Site(-1, OpenSite);
    private Site _virtualBottomSite = new Site(-2, OpenSite);

    //endregion

    public PercolationWeightedQuickUnion(int n) {
        if (n <= 0) throw new IllegalArgumentException("the n should large than 0");
        sites = new Site[n][n];
        int number = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Site site = new Site(number, CloseSite);
                sites[i][j] = site;
                number++;
            }
        }
    }

    private Point root(Point point) {
        while ()
    }

    private boolean connected(Point p1, Point p2) {
        return root(p1) == root(p2);
    }

    private void union(Point p1, Point p2) {
        int p1Size = sites[p1.X][p1.X].Size, p2Size = sites[p2.X][p2.Y].Size;

        Point rootP1 = root(p1);
        Point rootP2 = root(p2);

        if (p1Size < p2Size) {
            sites[rootP1.X][rootP1.Y].Value = sites[rootP2.X][rootP2.Y].Value;
        } else {
            sites[rootP2.X][rootP2.Y].Value = sites[rootP1.X][rootP1.Y].Value;
        }
    }

}
