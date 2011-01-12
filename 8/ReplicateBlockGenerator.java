public class ReplicateBlockGenerator extends BlockGenerator {

    public ReplicateBlockGenerator(int radius) {
        super(radius);
    }

    public int[] getBlock(AsciiImage img, int x, int y) {

        /* method exists only because it is required by instructions */

        initBlock(img, x, y);

        return block;

    }


    protected char getEdgeChar(AsciiImage img, int x, int y, int dx, int dy) {

        return img.getPixel(reduce(img.getWidth() - 1, x + dx),
                            reduce(img.getHeight() - 1, y + dy));

    }

    private int reduce(int ceil, int val) {
        if (val < 0) return 0;
        else if (val > ceil) return ceil;
        else return val;
    }

}
