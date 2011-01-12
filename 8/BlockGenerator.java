public abstract class BlockGenerator {

    protected final int radius;
    protected int[] block;
    protected String charset;

    public BlockGenerator(int radius) {

        this.radius = radius;
        block = new int[radius * radius];

    }

    protected void initBlock(AsciiImage img, int x, int y) {

        /* save charset */
        charset = img.getCharset();

        /* and write region from img */
        int curx, cury;
        final int lowerbound = radius / 2 * -1;
        final int upperbound = Math.abs(lowerbound); /* also central coord of block */
        for (int dx = lowerbound; dx <= upperbound; dx++) {
            curx = x + dx;
            for (int dy = lowerbound; dy <= upperbound; dy++) {
                cury = y + dy;
                /* edge case, use special handling */
                if (!img.isInBounds(curx, cury)) {
                    block[_(upperbound + dx, upperbound + dy)] = 
                        charset.indexOf(getEdgeChar(img, x, y, dx, dy));
                /* default case */
                } else {
                    block[_(upperbound + dx, upperbound + dy)] = 
                        charset.indexOf(img.getPixel(curx, cury));
                }
            }
        }
        

    }

    protected int _(int x, int y) {
        return y * radius + x;
    }
    protected int mod(int dividend, int divisor) {
        /* get some usable results from #*$^%#$ java module */
        return (dividend % divisor + divisor) % divisor;
    }

    public abstract int[] getBlock(AsciiImage img, int x, int y);
    protected abstract char getEdgeChar(AsciiImage img, int x, int y, int dx, int dy);

}
