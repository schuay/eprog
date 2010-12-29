import java.util.Scanner;

public class LoadFactory implements Factory {

	public Operation create(Scanner scanner) throws FactoryException {

        String[] args = scanner.nextLine().trim().split(" ");

        if (args.length != 1) {
            throw new FactoryException("Insufficient parameter");
        }
        String eof = args[0].trim();

        String data = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(eof)) {
                break;
            }
            data += line + "\n";
        }

		return new LoadOperation(data);

	}

}
