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

        try {
            return new FloodfillOperation(AsciiShop.parseInt(args[0]), 
                        AsciiShop.parseInt(args[1]),
                        args[2].charAt(0));
        } catch (AsciiException e) {
            throw new FactoryException();
        }

    }

}
