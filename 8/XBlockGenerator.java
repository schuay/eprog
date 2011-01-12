public class XBlockGenerator extends BlockGenerator {

    public XBlockGenerator(int radius) {
        super(radius);
    }

    public int[] getBlock(AsciiImage img, int x, int y) {

        /* method exists only because it is required by instructions */

        initBlock(img, x, y);

        return block;

    }

    protected char getEdgeChar(AsciiImage img, int x, int y, int dx, int dy) {

        return charset.charAt(charset.length() - 1);

    }

}
