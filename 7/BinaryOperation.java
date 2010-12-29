import java.util.ArrayList;

public class BinaryOperation implements Operation {

    char threshold, brightest, darkest;
    int thresholdIndex;
    String charset;

	public BinaryOperation(char threshold) {
        this.threshold = threshold;
    }

	public AsciiImage execute(AsciiImage img) throws OperationException {

        charset = img.getCharset();
        thresholdIndex = charset.indexOf(threshold);
        if (thresholdIndex == -1)
            throw new OperationException();

        darkest = charset.charAt(0);
        brightest = charset.charAt(charset.length() - 1);

        AsciiImage result = new AsciiImage(img);
        for (int x = 0; x < img.getWidth(); x++)
            for (int y = 0; y < img.getHeight(); y++)
                result.setPixel(x, y, toBinary(result.getPixel(x, y)));

		return result;

	}

    private char toBinary(char from) {
        if (charset.indexOf(from) < thresholdIndex)
            return darkest;
        else return brightest;
    }

}
