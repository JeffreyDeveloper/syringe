package team.unnamed.inject.bind;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

import static team.unnamed.inject.internal.Preconditions.checkNotNull;

public interface Binder {

    <T> BindingBuilder.Qualified<T> bind(Class<T> type);

    <T> BindingBuilder.Qualified<T> bind(TypeReference<T> typeReference);

    <T> BindingBuilder.Linkable<T> bind(Key<T> key);

    default void install(Module module) {
        checkNotNull(module);
        module.configure(this);
    }

}
