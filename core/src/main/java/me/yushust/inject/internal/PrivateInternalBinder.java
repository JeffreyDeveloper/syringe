package me.yushust.inject.internal;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.exception.InvalidBindingException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.bind.PrivateBinder;

public class PrivateInternalBinder extends BasicInternalBinder implements PrivateBinder, InternalBinder {

    private final InternalBinder parentLinker;

    public PrivateInternalBinder(InternalBinder parentLinker) {
        this.parentLinker = Preconditions.checkNotNull(parentLinker);
    }

    @Override
    public <T> void expose(Class<T> key) {
        expose(new Token<>(key));
    }

    @Override
    public <T> void expose(Token<T> key) {
        expose(Key.of(key));
    }

    @Override
    public <T> void expose(Key<T> key) {
        Binding<T> binding = this.findBinding(key);
        if (binding == null) {
            throw new InvalidBindingException("The exposing link isn't present!");
        }
        removeBinding(key);
        parentLinker.setBinding(binding);
    }

    @Override
    public <T> Binding<T> findBinding(Key<T> key) {
        Binding<T> binding = super.findBinding(key);
        if (binding == null) {
            return parentLinker.findBinding(key);
        }
        return binding;
    }
}
