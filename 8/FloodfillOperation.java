public class FloodfillOperation implements Operation {

    private final int x, y;
    private final char toChar;
    private char fromChar;
    private AsciiImage result;

    public FloodfillOperation(int x, int y, char c) {

        this.x = x;
        this.y = y;
        this.toChar = c;

    }

    public AsciiImage execute(AsciiImage img) throws OperationException {

        /* invalid char */
        if (img.getCharset().indexOf(toChar) == -1 ) {
            throw new OperationException();
        /* out of bounds */
        } else if (!img.isInBounds(x, y)) {
            throw new OperationException();
        }

        result = new AsciiImage(img);
        fromChar = img.getPixel(x, y);

        /* nothing to do */
        if (fromChar == toChar)
            return result;

        recursiveFill(x, y);

        return result;

    }

    private void recursiveFill(int x, int y) {

        /* check if we are on valid tile */

        if (result.getPixel(x, y) != fromChar)
            return;

        /* we are, change color */

        result.setPixel(x, y, toChar);

        /* then recurse over all neighbors */

        for (AsciiPoint p : result.getFourNeighborList(x, y))
            recursiveFill(p.getX(), p.getY());

    }

}
