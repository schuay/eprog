import java.util.Scanner;

public class FilterFactory implements Factory {

    private static final int defaultRadius = 3;
    private static final String defaultType = "x";

	public Operation create(Scanner scanner) throws FactoryException {

        String[] args = scanner.nextLine().trim().split(" ");
        if (args.length < 1 || args.length > 3) {
            throw new FactoryException("Insufficient parameter");
        }

        int radius = defaultRadius;
        String type = defaultType;

        /* parse args */
        if (args.length > 1) {
            try {
                radius = AsciiShop.parseInt(args[1]);
                if (args.length == 3)
                    type = args[2];
            } catch (AsciiException e) {
                /* parsing radius failed, which means we need to have exactly 2 args
                 * with 2nd arg == type */
                if (args.length != 2) throw new FactoryException(AsciiConstants.errInp);
                type = args[1];
            }
        }

        /* check radius */
        if (radius <= 1 || radius % 2 != 1) {
            throw new FactoryException(AsciiConstants.errInp);
        }

        /* create block generator */
        BlockGenerator generator;
        if (type.equals("x")) {
            generator = new XBlockGenerator(radius);
        } else if (type.equals("circular")) {
            generator = new CircularBlockGenerator(radius);
        } else if (type.equals("replicate")) {
            generator = new ReplicateBlockGenerator(radius);
        } else if (type.equals("symmetric")) {
            generator = new SymmetricBlockGenerator(radius);
        } else {
            throw new FactoryException(AsciiConstants.errInp);
        }


        /* return operation */
        if (args[0].equals("median")) {
            return new MedianOperation(generator);
        } else if (args[0].equals("average")) {
            return new AverageOperation(generator);
        } else {
            throw new FactoryException(AsciiConstants.errInp);
        }

	}

}
