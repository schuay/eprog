import java.util.ArrayList;

public abstract class FilterOperation implements Operation {

    protected final int neighborCount = 9;
    protected String charset;

	public FilterOperation() { }

	public AsciiImage execute(AsciiImage img) throws OperationException {

		AsciiImage result = new AsciiImage(img);
        charset = result.getCharset();

        /* calculate and set median for each coordinate in img */
        for (int x = 0; x < result.getWidth(); x++)
            for (int y = 0; y < result.getHeight(); y++)
                result.setPixel(x, y, getFilteredPixel(img, x, y));

		return result;

	}

    private char getFilteredPixel(AsciiImage img, int x, int y) {

        ArrayList<AsciiPoint> neighbors = img.getNeighborList(x, y);
        int[] indices = new int[neighborCount];
        int i = 0;
        /* fill with existing neighbor values */
        for (AsciiPoint p : neighbors)
            indices[i++] = charset.indexOf(img.getPixel(p));
        /* fill up with bg value for edge cases */
        for (; i < neighborCount ; i++)
            indices[i] = charset.length() - 1;

        /* perform filter and return appropriate char */
        return charset.charAt(filter(indices));

    }

    public abstract int filter(int[] values);

}
