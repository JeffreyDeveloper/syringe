package me.yushust.inject.exception;

public class InvalidLinkingException extends RuntimeException {

    private static final long serialVersionUID = 6354387683743543L;

    public InvalidLinkingException(String message) {
        super(message);
    }

    public InvalidLinkingException(Throwable cause) {
        super(cause);
    }

    public InvalidLinkingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLinkingException() {

    }

}
