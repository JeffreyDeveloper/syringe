package me.yushust.inject.exception;

public class InjectionException extends RuntimeException {

    private static final long serialVersionUID = 4684384689687435L;

    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(Throwable cause) {
        super(cause);
    }

    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InjectionException() {

    }

}
