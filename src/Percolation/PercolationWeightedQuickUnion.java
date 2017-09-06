package Percolation;

public class PercolationWeightedQuickUnion {

    //region private
    private class Site {
        //use x and y to identify the parent relationship
        private Point NodePoint;

        //close or open
        private int SiteStatus;

        //weight
        private int Size;

        private boolean IsVirtualSite;

        public Site(Point point, int siteStatus, boolean isVirtualSite) {
            NodePoint = point;
            SiteStatus = siteStatus;
            IsVirtualSite = isVirtualSite;
        }

        public int getSiteStatus() {
            return SiteStatus;
        }

        public void setSiteStatus(int siteStatus) {
            SiteStatus = siteStatus;
        }

        public Point getNodePoint() {
            return NodePoint;
        }

        public void setNodePoint(Point point) {
            NodePoint.setX(point.getX());
            NodePoint.setY(point.getY());
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

        public boolean IsEqual(Point point){
            return X == point.getX() && Y == point.getY();
        }

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

    private Site _virtualTopSite;
    private Site _virtualBottomSite;

    //endregion

    public PercolationWeightedQuickUnion(int n) {
        if (n <= 0) throw new IllegalArgumentException("the n should large than 0");

        Point topSitePoint = new Point(-1, -1);
        Point bottomSitePoint = new Point(-2, -2);
        _virtualTopSite = new Site(topSitePoint, OpenSite, true);
        _virtualBottomSite = new Site(bottomSitePoint, OpenSite, true);

        sites = new Site[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Point nodePoint = new Point(i, j);
                Site site = new Site(nodePoint, CloseSite, false);
                sites[i][j] = site;
            }
        }
    }

    private Point root(Point point) {
        Site currentSite = sites[point.getX()][point.getY()];
        Point sitePoint = currentSite.getNodePoint();
        while(sitePoint.getX() != point.getX() || sitePoint.getY() != point.getY()){
            return root(sitePoint);
        }
        return point;
    }

    private boolean connected(Point p1, Point p2) {
        return root(p1) == root(p2);
    }

    //p1是本次操作Open的Site p2是相邻的Site
    private void union(Point p1, Point p2) {

        int p1Size = sites[p1.X][p1.X].Size, p2Size = sites[p2.X][p2.Y].Size;

        Point rootP1 = root(p1);
        Point rootP2 = root(p2);

        //if had unioned or p2 is closesite, return directly
        if (rootP1.IsEqual(rootP2) || sites[p2.X][p2.Y].SiteStatus == CloseSite){
            return;
        }

        if (p1Size < p2Size) {
            sites[rootP1.X][rootP1.Y].setNodePoint(sites[rootP2.X][rootP2.Y].getNodePoint());
        } else {
            sites[rootP2.X][rootP2.Y].setNodePoint(sites[rootP1.X][rootP1.Y].getNodePoint());
        }
    }


    private void union(Site virtualSite, Point p1){

        Point rootP1 = root(p1);

        virtualSite.setNodePoint(sites[rootP1.X][rootP1.Y].getNodePoint());
    }

    //region  Start Public Methods

    public void open(int row, int col) {
        boolean isHasBeenOpen = sites[row][col].SiteStatus == OpenSite;
        if (isHasBeenOpen) return;

        Site currentSite = sites[row][col];
        currentSite.SiteStatus = OpenSite;
        OpenSiteCounts++;

        Point thisNode = new Point(row, col);
        Point belowNode = new Point(row + 1, col);
        Point aboveNode = new Point(row - 1, col);
        Point leftNode = new Point(row, col - 1);
        Point rightNode = new Point(row, col + 1);

        //考虑QuickUnion此处的逻辑 还需要Union附近的4个节点吗
        if (row != 0) {
            union(thisNode, aboveNode);
        } else {
            //connect to virtual top site
            union(_virtualTopSite, thisNode);
        }

        if (row != sites.length - 1) {
            union(thisNode, belowNode);
        } else {
            //connect to virtual bottom site
            union(_virtualBottomSite, thisNode);
        }

        if (col != 0) {
            union(thisNode, leftNode);
        }

        if (col != sites.length - 1) {
            union(thisNode, rightNode);
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[row][col].SiteStatus == OpenSite;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return _virtualTopSite.getNodePoint().IsEqual(sites[row][col].getNodePoint());
    }

    // number of open sites
    public int numberOfOpenSites() {
        return OpenSiteCounts;
    }

    // does the system percolate?
    public boolean percolates() {
        //isTheVisualTopConnectedWithTheBottom
        return _virtualTopSite.getNodePoint().IsEqual(_virtualBottomSite.getNodePoint());
    }

    //endregion

}
