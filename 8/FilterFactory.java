import java.util.Scanner;

public class FilterFactory implements Factory {

	public Operation create(Scanner scanner) throws FactoryException {

        String[] args = scanner.nextLine().trim().split(" ");
        if (args.length != 1) {
            throw new FactoryException("Insufficient parameter");
        }

        if (args[0].equals("median")) {
            return new MedianOperation();
        } else if (args[0].equals("average")) {
            return new AverageOperation();
        } else {
            throw new FactoryException(AsciiConstants.errInp);
        }

	}

}
