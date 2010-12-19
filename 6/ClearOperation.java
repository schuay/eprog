import java.util.ArrayList;

public class ClearOperation implements Operation {

	public ClearOperation() { }

	public AsciiImage execute(AsciiImage img) throws OperationException {

        String charset = img.getCharset();
        char clearChar = charset.charAt(charset.length() - 1);

		AsciiImage result = new AsciiImage(img);

        /* reset all pixels to default value */
        for (int x = 0; x < result.getWidth(); x++)
            for (int y = 0; y < result.getHeight(); y++)
                result.setPixel(x, y, clearChar);

		return result;

	}

}
