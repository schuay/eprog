public class FactoryException extends Exception {
    public FactoryException() {
        super("INPUT MISMATCH");
    }
    public FactoryException(String msg) {
        super(msg);
    }
}
