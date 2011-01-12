import java.util.ArrayList;

public abstract class FilterOperation implements Operation {

    protected String charset;
    protected final BlockGenerator generator;

	public FilterOperation(BlockGenerator generator) {
        this.generator = generator;
    }

	public AsciiImage execute(AsciiImage img) throws OperationException {

		AsciiImage result = new AsciiImage(img);
        charset = result.getCharset();

        /* calculate and set median for each coordinate in img */
        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                char c = charset.charAt(filter(generator.getBlock(img, x, y)));
                result.setPixel(x, y, c);
            }
        }

		return result;

	}

    public abstract int filter(int[] values);

}
