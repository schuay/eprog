import java.util.Scanner;

public class BinaryFactory implements Factory {

	public Operation create(Scanner scanner) throws FactoryException {

        String[] args = scanner.nextLine().trim().split(" ");
        if (args.length != 1 || args[0].length() != 1) {
            throw new FactoryException("Insufficient parameter");
        }

		return new BinaryOperation(args[0].charAt(0));

	}

}
