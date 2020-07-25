package team.unnamed.inject.exception;

public class InvalidBindingException extends RuntimeException {

    private static final long serialVersionUID = 6354387683743543L;

    public InvalidBindingException(String message) {
        super(message);
    }

    public InvalidBindingException(Throwable cause) {
        super(cause);
    }

    public InvalidBindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBindingException() {

    }

}
