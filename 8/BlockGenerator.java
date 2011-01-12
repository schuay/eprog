public abstract class BlockGenerator {

    protected final int radius;
    protected int[] block;
    protected String charset;
    protected final int notinit = -1;

    public BlockGenerator(int radius) {

        this.radius = radius;
        block = new int[radius * radius];

    }

    protected void initBlock(AsciiImage img, int x, int y) {

        /* init to special value -1 */
        for (int i = 0; i < radius * radius; i++)
            block[i] = notinit;

        /* and write region from img */
        charset = img.getCharset();
        int curx, cury;
        final int lowerbound = radius / 2 * -1;
        final int upperbound = Math.abs(lowerbound); /* also central coord of block */
        for (int dx = lowerbound; dx <= upperbound; dx++) {
            curx = x + dx;
            for (int dy = lowerbound; dy <= upperbound; dy++) {
                cury = y + dy;
                if (!img.isInBounds(curx, cury)) continue;
                block[_(upperbound + dx, upperbound + dy)] = 
                    charset.indexOf(img.getPixel(curx, cury));
            }
        }
        

    }

    protected int _(int x, int y) {
        return y * radius + x;
    }

    public abstract int[] getBlock(AsciiImage img, int x, int y);

}
