import java.util.Scanner;

public interface Factory {
    public Operation create(Scanner scanner) throws FactoryException;
}
