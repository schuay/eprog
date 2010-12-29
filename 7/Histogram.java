public class Histogram {
    private static int height = 16;

    public static getHistogram(AsciiImage img) {
        AsciiImage result;
        String origCharset = img.getCharset();
        String charset = makeCharset(origCharset);

        result = new AsciiImage(origCharset.length() + 3,
                height, charset);

        /* count char occurences */
        int[] cCounts = new int[origCharset.length()];
        for (int i = 0; i < origCharset.length(); i++)
            cCounts[i] = img.getPointList(origCharset.charAt(i)).size();

        /* construct image */
        int charCount = img.getHeight() * img.getWidth();

        /* get max */
        
        for (int i = 0; i < height - 1; i++) {
            /* draw bottom up (simpler) */
        }

        return result;
    }
    private static String makeCharset(String original) {
        String histChars = "0123456789.#";
        String newCharset = original;
        for (int i = 0; i < histChars.length(); i++)
            if (newCharset.indexOf(histChars.charAt(i)) == -1)
                    newCharset += histChars.charAt(i);

        return newCharset;
    }
}
