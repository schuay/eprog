import java.util.Scanner;

public class ClearFactory implements Factory {

	public Operation create(Scanner scanner) throws FactoryException {

        if (scanner.hasNext()) {
            throw new FactoryException("Insufficient parameter");
        }

		return new ClearOperation();

	}

}
