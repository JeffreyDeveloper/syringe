package me.yushust.inject.exception;

public class NoInjectableConstructorException extends Exception {

    private static final long serialVersionUID = 63543873543L;

    public NoInjectableConstructorException(String message) {
        super(message);
    }

    public NoInjectableConstructorException(Throwable cause) {
        super(cause);
    }

    public NoInjectableConstructorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoInjectableConstructorException() {

    }

}
