package me.yushust.inject.link;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.util.Objects;

public interface Linker {

    <T> LinkBuilder.Qualified<T> link(Class<T> type);

    <T> LinkBuilder.Qualified<T> link(Token<T> token);

    <T> LinkBuilder.Linkable<T> link(Key<T> key);

    default void install(Module module) {
        Objects.requireNonNull(module);
        module.configure(this);
    }

}
