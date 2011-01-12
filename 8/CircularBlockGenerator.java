public class CircularBlockGenerator extends BlockGenerator {

    public CircularBlockGenerator(int radius) {
        super(radius);
    }

    public int[] getBlock(AsciiImage img, int x, int y) {

        initBlock(img, x, y);

        /* set all uninitialized to lightest */
        for (int i = 0; i < radius * radius; i++)
            if (block[i] == notinit)
                block[i] = charset.length() - 1;

        return block;

    }

}
