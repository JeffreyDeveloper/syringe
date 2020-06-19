package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.util.Objects;

public interface Binder {

    <T> BindingBuilder.Qualified<T> bind(Class<T> type);

    <T> BindingBuilder.Qualified<T> bind(Token<T> token);

    <T> BindingBuilder.Linkable<T> bind(Key<T> key);

    default void install(Module module) {
        Objects.requireNonNull(module);
        module.configure(this);
    }

}
