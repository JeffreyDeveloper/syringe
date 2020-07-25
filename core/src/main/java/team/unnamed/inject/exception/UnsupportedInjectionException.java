package team.unnamed.inject.exception;

public class UnsupportedInjectionException extends Exception {

    private static final long serialVersionUID = 6354387683743543L;

    public UnsupportedInjectionException(String message) {
        super(message);
    }

    public UnsupportedInjectionException(Throwable cause) {
        super(cause);
    }

    public UnsupportedInjectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedInjectionException() {

    }

}
