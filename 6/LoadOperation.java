import java.util.ArrayList;

public class LoadOperation implements Operation {

    private final String data;
    private final String[] lines;

	public LoadOperation(String data) {
		this.data = data;
        this.lines = data.split("\n");
	}

	public AsciiImage execute(AsciiImage img) throws OperationException {

		if (containsInvalidChars(data, img.getCharset())) {
			throw new OperationException();
		}

		AsciiImage result = new AsciiImage(img);

        if (!hasValidDimensions(result))
            throw new OperationException();

        int y = 0;
        for (String line : lines) {
            for (int x = 0; x < line.length(); x++) {
                result.setPixel(x, y, line.charAt(x));
            }
            y++;
        }

		return result;

	}

    private boolean hasValidDimensions(AsciiImage img) {
        int width = img.getWidth();
        if (lines.length != img.getHeight())
            return false;
        for (String line : lines)
            if (line.length() != width)
                return false;
        return true;
    }

    private boolean containsInvalidChars(String data, String charset) {
        String tmp = new String(data);
        for (int i = 0; i < charset.length(); i++)
            tmp = tmp.replace(charset.substring(i, i+1), "");
        tmp = tmp.replace("\n", "");
        return tmp.length() > 0;
    }

}
