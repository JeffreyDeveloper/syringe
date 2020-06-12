package me.yushust.inject.internal;

public final class Preconditions {

    private Preconditions() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static <T> T checkNotNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

}
