package me.yushust.inject.internal;

import me.yushust.inject.bind.Binder;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

public interface InternalBinder extends Binder {

    <T> void removeBinding(Token<T> token);

    <T> void removeBinding(Key<T> key);

    <T> void setBinding(Binding<T> binding);

    <T> Binding<T> findBinding(Key<T> key);

}
