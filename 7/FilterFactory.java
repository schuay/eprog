import java.util.Scanner;

public class FilterFactory implements Factory {

	public Operation create(Scanner scanner) throws FactoryException {

        String type;
        if (!scanner.hasNext()) {
            throw new FactoryException("Insufficient parameter");
        } else {
            type = scanner.next();
        }

        if (!type.equals("median")) {
            throw new FactoryException(AsciiConstants.errInp);
        }

        return new MedianOperation();

	}

}
