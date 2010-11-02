class AsciiImage {
	private String data;
	private int	h, w;

	public AsciiImage() {
		h = w = 0;
		data = "";
	}

	public boolean addLine(String line) {
        /* if this is our first line, save input width */
        if (h == 0) {
            w = line.length();
            if (w == 0) {
                return false;
            }
        /* otherwise make sure width stays constant. if it doesn't,
         * throw an exception to bail out */
        } else if (line.length() != w) {
           return false;
        }
        /* increment our line counter */
        h++;
        /* save for decoding (newlines stripped by Scanner.nextLine */
        data += line;

        return true;
	}
	public int getWidth() {
        return w;
	}
	public int getHeight() {
        return h;
	}
	public String toString() {
        String s = "";
        for (int i = 0; i < h; i++) {
            try {
                s += String.format("%s", getLine(i));
            /* return empty string on error */
            } catch (Exception x) {
                return "";
            }
            /* add newline on all rows except last */
            if (i != h -1) {
                s += String.format("%n");
            }
        }
        return s;
	}
	public int getUniqueChars() {
        String s = data;
        int num = 0;
        /* strip string while counting chars until none are left */
        while (s.length() > 0) {
            num++;
            s = s.replace(s.substring(0,1), "");
        }
        return num;
	}
	public void flipV() throws Exception {
        String s = "";
        /* reconstruct image in reverse row order */
        for (int i = 0; i < h; i++) {
            s += getLine(h - i - 1);
        }
        data = s;
	}
	public void transpose() {
        String s = "";
        int x, y;
        /* transpose image by having outer loop = rows
         * and inner loop = cols */
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                s += data.charAt(i * w + j);
            }
        }
        /* adjust width, height and save altered img */
        x = w;
        w = h;
        h = x;
        data = s;
	}
    public boolean isSymmetric() throws Exception {
        String line;
        for (int i = 0; i < h; i++) {
            line = getLine(i);
            for (int j = 0; j <= w / 2; j++) {
                if(line.charAt(j) != line.charAt(w - j - 1)) {
                    return false;
                }
            }
        }
        return true;
    }
    private String getLine(int x) throws Exception {
        if (x < 0 || x >= h) {
            throw new Exception("Index out of range");
        }
        return data.substring(x * w, x * w + w);
    }
}
