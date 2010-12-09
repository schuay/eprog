public class AsciiPoint {
    private final int xcoord, ycoord;

    public AsciiPoint(int x, int y) {
        xcoord = x;
        ycoord = y;
    }
    public int getX() {
        return xcoord;
    }
    public int getY() {
        return ycoord;
    }
    public AsciiPoint swap() {
        return new AsciiPoint(ycoord, xcoord);
    }
    public AsciiPoint getDistance(AsciiPoint rhs) {
        /* return difference of 2 points */
        return new AsciiPoint(rhs.xcoord - xcoord,
                              rhs.ycoord - ycoord);
    }
    public String toString() {
        return String.format("(%d,%d)", xcoord, ycoord);
    }
}
