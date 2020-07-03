package me.yushust.inject.internal;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.exception.ExceptionFactory;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.bind.PrivateBinder;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class PrivateInternalBinder extends SimpleBinder implements PrivateBinder, InternalBinder {

    private final InternalBinder parentBinder;

    public PrivateInternalBinder(InternalBinder parentBinder) {
        this.parentBinder = checkNotNull(parentBinder);
    }

    @Override
    public <T> void expose(Class<T> key) {
        expose(TypeReference.of(key));
    }

    @Override
    public <T> void expose(TypeReference<T> key) {
        expose(Key.of(key));
    }

    @Override
    public <T> void expose(Key<T> key) {
        Binding<T> binding = this.findBinding(key);
        if (binding == null) {
            throw ExceptionFactory.cannotExposeBinding(key, "Key isn't bound");
        }
        removeBinding(key);
        parentBinder.setBinding(binding);
    }

    @Override
    public <T> Binding<T> findBinding(Key<T> key) {
        Binding<T> binding = super.findBinding(key);
        if (binding == null) {
            return parentBinder.findBinding(key);
        }
        return binding;
    }

}