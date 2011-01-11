import java.util.Scanner;

public class FloodfillFactory implements Factory {

    public FloodfillFactory() { }

    public Operation create(Scanner scanner) throws FactoryException {

        String[] args = scanner.nextLine().trim().split(" ");
        if (args.length != 3) {
            throw new FactoryException("Insufficient parameter");
        }
        /* third arg not a char */
        if (args[2].length() != 1) {
            throw new FactoryException(AsciiConstants.errInp);
        }

        return new FloodfillOperation(parseInt(args[0]), parseInt(args[1]),
                    args[2].charAt(0));

    }

    private static int parseInt(String s) throws FactoryException {
        /* safe int parsing */
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new FactoryException(AsciiConstants.errInp);
        }
    }

}
