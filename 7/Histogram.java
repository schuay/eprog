import java.util.Arrays;

public class Histogram {
    private static final int height = 16;

    public static AsciiImage getHistogram(AsciiImage img) {
        AsciiImage result;
        String origCharset = img.getCharset();
        String charset = makeCharset(origCharset);
        int width = origCharset.length() + 3;

        /* count char occurences */
        int charCount = img.getHeight() * img.getWidth();
        double[] charPercentages = new double[origCharset.length()];
        for (int i = 0; i < origCharset.length(); i++) {
            charPercentages[i] = img.getPointList(origCharset.charAt(i)).size() * 
                100.0d / charCount;
        }

        /* get max char percentage for correct histogram scale */
        double[] charPercentagesSorted = charPercentages.clone();
        Arrays.sort(charPercentagesSorted);
        double maxPercentage = charPercentagesSorted[charPercentagesSorted.length - 1];

        /* construct image */

        result = new AsciiImage(width,
                height, charset);

        initializeHistogram(result, maxPercentage, origCharset);
        drawHistogram(result, charPercentages, maxPercentage);

        return result;
    }
    private static void drawHistogram(AsciiImage hist, double[] charPercentages,
            double maxPercentage) {

        /* go through charcounts one by one */
        for (int i = 0; i < charPercentages.length; i++) {
            /* round up */
            double currentPercentage = Math.ceil(charPercentages[i]);

            for (int y = 1; (maxPercentage * (y - 1)) / (height - 1) < currentPercentage; y++) {
                if (y == height) continue;
                hist.setPixel(3 + i, height - 1 - y, '#');
            }
        }

    }
    private static void initializeHistogram(AsciiImage hist, double maxPercentage,
            String charset) {

        /* initialize background */
        for (int i = 0; i < height; i++)
            for (int j = 0; j < hist.getWidth(); j++)
                hist.setPixel(j, i, '.');

        /* draw legend */
        for (int i = 0; i < charset.length(); i++)
            hist.setPixel(3 + i, height - 1, charset.charAt(i));

        /* draw scale */
        for (int i = 1; i < height; i += 2) {
            double currentPercentage = maxPercentage * i / (height - 1);
            /* round to nearest */
            int currentPercentageInt = (int)Math.round(currentPercentage);
            int yCoord = height - 1 - i;

            /* draw scale */
            for (int j = 0; currentPercentageInt > 0; j++) {
                hist.setPixel(2 - j, yCoord,
                        Character.forDigit(currentPercentageInt % 10, 10));
                currentPercentageInt /= 10;
            }
        }

    }
    private static String makeCharset(String original) {
        String histChars = "0123456789.#";
        String newCharset = "";
        for (int i = 0; i < histChars.length(); i++)
            if (original.indexOf(histChars.charAt(i)) == -1)
                    newCharset += histChars.charAt(i);
        newCharset += original;

        return newCharset;
    }
}
