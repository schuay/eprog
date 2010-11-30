import java.util.ArrayList;

public class AsciiImage {
    private char[][] data;
    private int h, w;
    private static final char cclear = '.';

    public AsciiImage(int width, int height) throws AsciiException {
        if (width <= 0 || height <= 0) {
           throw new AsciiException(AsciiConstants.errInp);
        }
        /* properly initialize all variables and clear the img area */
        h = height;
        w = width;
        data = new char[w][h];
        clear();
    }
    public AsciiImage(AsciiImage img) {
        /* copy vars, deep copy array */
        h = img.h;
        w = img.w;
        data = new char[w][];
        for (int i = 0; i < w; i++) {
            data[i] = new char[h];
            System.arraycopy(img.data[i], 0, data[i], 0, h);
        }
    }

    public void load(String[] image) throws AsciiException {
        /* load line by line, make sure line count matches given height */
        int linecount = 0;
        for (int i = 0; i < image.length; i++) {
            addLine(linecount, image[i]);
            linecount++;
        }
        if (linecount != h) {
            throw new AsciiException(AsciiConstants.errInp);
        }
    }
    private void addLine(int linecount, String line) throws AsciiException {
        /* make sure width stays constant. if it doesn't,
         * throw an exception to bail out */
        if (line.length() != w) {
           throw new AsciiException(AsciiConstants.errInp);
        } else if (linecount >= h) {
           throw new AsciiException(AsciiConstants.errInp);
        }

        for (int i = 0; i < w; i++)
            setPixel(i, linecount, line.charAt(i));
    }
    public int getWidth() {
        return w;
    }
    public int getHeight() {
        return h;
    }
    public String toString() {
        /* reconstruct img string */
        StringBuffer s = new StringBuffer();
        for (int y = 0; y < h; y++) {
            try {
                s.append(String.format("%s%n", getLine(y)));
            /* return empty string on error */
            } catch (AsciiException x) {
                return "";
            }
        }
        return s.toString();
    }
    public int getUniqueChars() {
        String s = data.toString();
        int num = 0;
        /* strip string while counting chars until none are left */
        while (s.length() > 0) {
            num++;
            s = s.replace(s.substring(0,1), "");
        }
        return num;
    }
    public void flipV() throws AsciiException {
        /* create and fill new data structure */
        char[][] flipped = new char[w][h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                flipped[x][y] = getPixel(x, w - y - 1);
            }
        }
        data = flipped;
    }
    public void transpose() {
        int tmp = w;
        /* adjust width, height and save altered img */
        w = h;
        h = tmp;
        /* create and fill new data structure */
        char[][] transposed = new char[w][h];
        /* transpose image by having outer loop = rows
         * and inner loop = cols */
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                transposed[x][y] = getPixel(y, x);
            }
        }
        data = transposed;
    }
    public boolean isSymmetric() {
        /* compare row by row */
        for (int y = 0; y < h; y++)
            for (int x = 0; x <= w / 2; x++)
                if(getPixel(x, y) != getPixel(w - x - 1, y))
                    return false;
        return true;
    }
    private String getLine(int y) throws AsciiException {
        /* construct single line string and return it */
        if (y < 0 || y >= h)
            throw new AsciiException("Index out of range");
        StringBuffer buf = new StringBuffer();
        for (int x = 0; x < w; x++)
            buf.append(getPixel(x, y));
        return buf.toString();
    }
    public void clear() {
        /* set all chars to cclear */
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                setPixel(x, y, cclear);
    }
    public void drawLine(int x0, int y0, int x1, int y1, char c) {
        AsciiPoint pointA = new AsciiPoint(x0, y0);
        AsciiPoint pointB = new AsciiPoint(x1, y1);
        AsciiPoint dist = pointA.getDistance(pointB);
        boolean inverted = false;

        /* special cases */
        if (Math.abs(dist.getY()) > Math.abs(dist.getX())) {
            pointA.swap();
            pointB.swap();
            inverted = true;
        }
        if (pointB.getX() < pointA.getX()) {
            AsciiPoint temp = pointA;
            pointA = pointB;
            pointB = temp;
        }
        dist = pointA.getDistance(pointB);

        /* draw the line by stepping through x values and
         * rounding to nearest y */
        final double ydelta = (double)dist.getY()/dist.getX();
        double ydouble = pointA.getY();
        int x = pointA.getX();
        for (; x <= pointB.getX(); x++, ydouble += ydelta) {
            if (inverted) {
                setPixel((int)Math.round(ydouble), x, c);
            } else {
                setPixel(x, (int)Math.round(ydouble), c);
            }
        }
    }
    public char getPixel(AsciiPoint p) {
        return getPixel(p.getX(), p.getY());
    }
    public char getPixel(int x, int y) {
        return data[x][y];
    }
    public void replace(char oldChar, char newChar) {
        for (AsciiPoint p : getPointList(oldChar))
            setPixel(p, newChar);
    }
    public void setPixel(AsciiPoint p, char c) {
        setPixel(p.getX(), p.getY(), c);
    }
    public void setPixel(int x, int y, char c) {
        data[x][y] = c;
    }
    ArrayList<AsciiPoint> getPointList(char c) {
        /* construct list of all points containing char c */
        ArrayList<AsciiPoint> list = new ArrayList<AsciiPoint>();
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (getPixel(x, y) == c)
                    list.add(new AsciiPoint(x, y));
        return list;
    }
    ArrayList<AsciiPoint> getNeighborList(AsciiPoint p) {
        /* returns all neighbors of point p (N,E,S,W) */
        ArrayList<AsciiPoint> list = new ArrayList<AsciiPoint>();
        int x = p.getX();
        int y = p.getY();
        if (x > 0)
            list.add(new AsciiPoint(x - 1, y));
        if (y > 0)
            list.add(new AsciiPoint(x, y - 1));
        if (x < w - 1)
            list.add(new AsciiPoint(x + 1, y));
        if (y < h - 1)
            list.add(new AsciiPoint(x, y + 1));
        return list;
    }
    public AsciiPoint getCentroid(char c) {
        /* sum up coords */
        int sumX = 0, sumY = 0;
        ArrayList<AsciiPoint> l = getPointList(c);
        if (l.size() == 0) {
            return null;
        }
        for (AsciiPoint p : l) {
            sumX += p.getX();
            sumY += p.getY();
        }
        /* and divide by point count */
        return new AsciiPoint(Math.round((float)sumX/l.size()),
                              Math.round((float)sumY/l.size()));
    }
    public void straightenRegion(char c) {
        /* delete lone points */
        boolean didsomething;
        do {
            didsomething = false;
            ArrayList<AsciiPoint> l = getPointList(c);
            for (AsciiPoint p : l) {
                int sum = 0;
                for (AsciiPoint q : getNeighborList(p)) {
                    if (getPixel(q) == c) {
                        sum++;
                    }
                }
                if (sum < 2) {
                    setPixel(p, cclear);
                    didsomething = true;
                }
            }
        } while (didsomething);
    }
    public void growRegion(char c) {
        /* expand areas of char c to neighboring points */
        ArrayList<AsciiPoint> l = getPointList(c);
        for (AsciiPoint p : l) {
            for (AsciiPoint q : getNeighborList(p)) {
                if (getPixel(q) == cclear)
                    setPixel(q, c);
            }
        }
    }
}
