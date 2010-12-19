import java.util.ArrayList;

public class AsciiImage {
    private char[][] data;
    private int h, w;
    private final String chset;
    private final char cclear;

    public AsciiImage(int width, int height, String charset) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
           throw new IllegalArgumentException(AsciiConstants.errInp);
        } else if (charset.length() == 0 || containsDuplicateChars(charset)) {
           throw new IllegalArgumentException(AsciiConstants.errInp);
        }
        /* properly initialize all variables and clear the img area */
        h = height;
        w = width;
        chset = charset;
        cclear = chset.charAt(chset.length() - 1);
        data = new char[w][h];
        clear();
    }

    private void clear() {
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                setPixel(x, y, cclear);
    }
    private boolean containsDuplicateChars(String str) {
        String tmp = new String(str);
        int len = tmp.length();
        int newlen;
        while (tmp.length() > 0) {
            tmp = tmp.replace(tmp.substring(0,1), "");
            newlen = tmp.length();
            if (newlen < len - 1)
                return true;
            len = newlen;
        }
        return false;
    }

    public AsciiImage(AsciiImage img) {
        /* copy vars, deep copy array */
        h = img.h;
        w = img.w;
        chset = img.chset;
        cclear = img.cclear;
        data = new char[w][];
        for (int i = 0; i < w; i++) {
            data[i] = new char[h];
            System.arraycopy(img.data[i], 0, data[i], 0, h);
        }
    }

    public String getCharset() {
        return new String(chset);
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
    private String getLine(int y) throws AsciiException {
        /* construct single line string and return it */
        if (y < 0 || y >= h)
            throw new AsciiException("Index out of range");
        StringBuffer buf = new StringBuffer();
        for (int x = 0; x < w; x++)
            buf.append(getPixel(x, y));
        return buf.toString();
    }
    public char getPixel(AsciiPoint p) {
        return getPixel(p.getX(), p.getY());
    }
    public char getPixel(int x, int y) {
        if (x < 0 || x >= w || y < 0 || y >= h)
            throw new IndexOutOfBoundsException();
        return data[x][y];
    }
    public void setPixel(AsciiPoint p, char c) {
        setPixel(p.getX(), p.getY(), c);
    }
    public void setPixel(int x, int y, char c) {
        if (x < 0 || x >= w || y < 0 || y >= h || chset.indexOf(c) == -1)
            throw new IndexOutOfBoundsException();
        data[x][y] = c;
    }
    public ArrayList<AsciiPoint> getPointList(char c) {
        /* construct list of all points containing char c */
        ArrayList<AsciiPoint> list = new ArrayList<AsciiPoint>();
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (getPixel(x, y) == c)
                    list.add(new AsciiPoint(x, y));
        return list;
    }
    public ArrayList<AsciiPoint> getNeighborList(int x, int y) {
        /* returns all neighbors around (x,y) *including* (x,y) */
        ArrayList<AsciiPoint> list = new ArrayList<AsciiPoint>();
        int curx, cury;
        for (int dx = -1; dx < 2; dx++) {
            curx = x + dx;
            if (curx < 0 || curx >= w) continue;
            for (int dy = -1; dy < 2; dy++) {
                cury = y + dy;
                if (cury < 0 || cury >= h) continue;
                list.add(new AsciiPoint(curx, cury));
            }
        }
        return list;
    }
}
