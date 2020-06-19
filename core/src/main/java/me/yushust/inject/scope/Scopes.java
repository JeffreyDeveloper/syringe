package me.yushust.inject.scope;

public final class Scopes {

    public static final Scope SINGLETON = new SingletonScope();

    private Scopes() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

}
