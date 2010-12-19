import java.util.ArrayList;
import java.util.Arrays;

public class MedianOperation implements Operation {

    private final int neighborCount = 9;

	public MedianOperation() { }

	public AsciiImage execute(AsciiImage img) throws OperationException {

		AsciiImage result = new AsciiImage(img);

        /* calculate and set median for each coordinate in img */
        for (int x = 0; x < result.getWidth(); x++)
            for (int y = 0; y < result.getHeight(); y++)
                result.setPixel(x, y, getMedian(img, x, y));

		return result;

	}

    private char getMedian(AsciiImage img, int x, int y) {
        String charset = img.getCharset();

        ArrayList<AsciiPoint> neighbors = img.getNeighborList(x, y);
        int[] indices = new int[neighborCount];
        int i = 0;
        /* fill with existing neighbor values */
        for (AsciiPoint p : neighbors)
            indices[i++] = charset.indexOf(img.getPixel(p));
        /* fill up with bg value for edge cases */
        for (; i < neighborCount ; i++)
            indices[i] = charset.length() - 1;
        /* sort and return median */
        Arrays.sort(indices);

        return charset.charAt(indices[neighborCount / 2]);
    }

}
