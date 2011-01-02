import java.util.Arrays;

public class Histogram {
    private static int height = 16;

    public static AsciiImage getHistogram(AsciiImage img) {
        AsciiImage result;
        String origCharset = img.getCharset();
        String charset = makeCharset(origCharset);
        int width = origCharset.length() + 3;

        result = new AsciiImage(width,
                height, charset);

        /* count char occurences */
        int[] cCounts = new int[origCharset.length()];
        for (int i = 0; i < origCharset.length(); i++)
            cCounts[i] = img.getPointList(origCharset.charAt(i)).size();

        /* construct image */
        int charCount = img.getHeight() * img.getWidth();

        /* get max */
        int[] cCountsSorted = cCounts.clone();
        Arrays.sort(cCountsSorted);
        int maxVal = (cCountsSorted[cCountsSorted.length - 1] * 100) / charCount;

        /* draw legend */
        for (int i = 0; i < origCharset.length(); i++)
            result.setPixel(3 + i, height - 1, origCharset.charAt(i));

        for (int i = 0; i < height - 1; i++) {
            int curScale = (i * maxVal) / height;
            int curLine = height - 2 - i;
            /* draw scale */
            if (i % 2 == 0) {
                result.setPixel(2, curLine, Character.forDigit(curScale % 10, 10));
                if (curScale >= 10)
                result.setPixel(1, curLine, Character.forDigit(curScale / 10, 10));
            }
            /* draw bottom up (simpler) */
            for (int j = 0; j < cCounts.length; j++)
                if (cCounts[j] >= cCountsSorted[cCountsSorted.length - 1] * curScale / 100)
                    result.setPixel(3 + j, curLine, '#');
        }

        return result;
    }
    private static String makeCharset(String original) {
        String histChars = "0123456789.#";
        String newCharset = ""; /* original charset last to preserve lightest char */
        for (int i = 0; i < histChars.length(); i++)
            if (original.indexOf(histChars.charAt(i)) == -1)
                    newCharset += histChars.charAt(i);
        newCharset += original;

        return newCharset;
    }
}
