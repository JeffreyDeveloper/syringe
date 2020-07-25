package team.unnamed.inject.internal;

import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.exception.ExceptionFactory;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.bind.PrivateBinder;

import static team.unnamed.inject.internal.Preconditions.checkNotNull;

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