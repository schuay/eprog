public class AsciiCoord {
    private int xcoord, ycoord;

    public AsciiCoord(int x, int y) {
        xcoord = x;
        ycoord = y;
    }
    public int getX() {
        return xcoord;
    }
    public int getY() {
        return ycoord;
    }
    public void swap() {
        int temp = xcoord;
        xcoord = ycoord;
        ycoord = temp;
    }
    public AsciiCoord getDistance(AsciiCoord rhs) {
        return new AsciiCoord(rhs.xcoord - xcoord,
                              rhs.ycoord - ycoord);
    }
}
