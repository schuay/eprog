public class CircularBlockGenerator extends BlockGenerator {

    public CircularBlockGenerator(int radius) {
        super(radius);
    }

    public int[] getBlock(AsciiImage img, int x, int y) {

        /* method exists only because it is required by instructions */

        initBlock(img, x, y);

        return block;

    }

    protected char getEdgeChar(AsciiImage img, int x, int y, int dx, int dy) {

       return img.getPixel(mod((x + dx),img.getWidth()),
                           mod((y + dy), img.getHeight()));

    }

}
