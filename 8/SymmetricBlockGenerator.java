public class SymmetricBlockGenerator extends BlockGenerator {

    public SymmetricBlockGenerator(int radius) {
        super(radius);
    }

    public int[] getBlock(AsciiImage img, int x, int y) {

        /* method exists only because it is required by instructions */

        initBlock(img, x, y);

        return block;

    }

    protected char getEdgeChar(AsciiImage img, int x, int y, int dx, int dy) {

        return img.getPixel(mirror(img.getWidth(), x + dx),
                            mirror(img.getHeight(), y + dy));

    }

    private int mirror(int ceil, int val) {

        int mod;    /* offset within bounds */
        int count;  /* count of transversed images */

        /* nothing to do */
        if (val >= 0 && val < ceil) return val;

        /* get some basic metrics */
        mod = mod(val, ceil);
        if (val < 0)
            /* adjust for neg. index and make first reflection have count == -1 */
            count = (val + 1 - ceil) / ceil;
        else
            /* first reflection has count == 1 */
            count = val / ceil;

        if (count % 2 == 0) {
            /* even reflections, offset from original image */
            return 0 + mod;
        } else {
            /* odd reflections, flipped offset */
            return ceil - 1 - mod;
        }
    }

}
