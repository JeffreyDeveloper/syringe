package team.unnamed.inject.internal;

import team.unnamed.inject.bind.Binder;
import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.identity.Key;

import java.util.Collection;

public interface InternalBinder extends Binder {

    <T> void removeBinding(Key<T> key);

    <T> void setBinding(Binding<T> binding);

    <T> Binding<T> findBinding(Key<T> key);

    Collection<Binding<?>> getBindings();

}