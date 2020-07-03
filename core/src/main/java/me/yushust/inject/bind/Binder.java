package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public interface Binder {

    <T> BindingBuilder.Qualified<T> bind(Class<T> type);

    <T> BindingBuilder.Qualified<T> bind(TypeReference<T> typeReference);

    <T> BindingBuilder.Linkable<T> bind(Key<T> key);

    default void install(Module module) {
        checkNotNull(module);
        module.configure(this);
    }

}
