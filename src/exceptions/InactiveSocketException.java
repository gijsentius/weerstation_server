package exceptions;

public class InactiveSocketException extends Exception {

    public InactiveSocketException() {
        super("Socket is inactive and therefore closed.");
    }
}
