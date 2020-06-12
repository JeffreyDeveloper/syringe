package me.yushust.inject.scope;

public final class Scopes {

    private Scopes() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static final Scope SINGLETON = new SingletonScope();

}
