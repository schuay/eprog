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
        if (val < 0) return 0 + mod(Math.abs(val) - 1, ceil); /* -1 should result in 0 */
        else if (val >= ceil) return ceil - mod(val, ceil) - 1; /* ceil should result in ceil - 1 */
        else return val;
    }

}
