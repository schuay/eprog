public class AsciiImage {
	private char[][] data;
	private int	h, w;
	private static final char cclear = '.';

	public AsciiImage(int width, int height) throws AsciiException {
		if (width <= 0 || height <= 0) {
           throw new AsciiException(AsciiConstants.errInp);
		}
		h = height;
		w = width;
		data = new char[w][h];
		clear();
	}

	public void load(String[] image) throws AsciiException {
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

        /* save for decoding (newlines stripped by Scanner.nextLine */
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
        StringBuffer s = new StringBuffer();
        for (int y = 0; y < h; y++) {
            try {
                s.append(String.format("%s", getLine(y)));
            /* return empty string on error */
            } catch (AsciiException x) {
                return "";
            }
			s.append(String.format("%n"));
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
        for (int y = 0; y < h; y++)
            for (int x = 0; x <= w / 2; x++)
                if(getPixel(x, y) != getPixel(w - x - 1, y))
                    return false;
        return true;
    }
    private String getLine(int y) throws AsciiException {
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
		AsciiCoord pointA = new AsciiCoord(x0, y0);
		AsciiCoord pointB = new AsciiCoord(x1, y1);
		AsciiCoord dist = pointA.getDistance(pointB);
		boolean inverted = false;

		if (Math.abs(dist.getY()) > Math.abs(dist.getX())) {
			pointA.swap();
			pointB.swap();
			inverted = true;
		}
		if (pointB.getX() < pointA.getX()) {
			AsciiCoord temp = pointA;
			pointA = pointB;
			pointB = temp;
		}
		dist = pointA.getDistance(pointB);

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
	public char getPixel(int x, int y) {
		return data[x][y];
	}
	public void replace(char oldChar, char newChar) {
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				if (getPixel(x, y) == oldChar)
					setPixel(x, y, newChar);
	}
	public void setPixel(int x, int y, char c) {
		data[x][y] = c;
	}
}
