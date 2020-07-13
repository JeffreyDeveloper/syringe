package me.yushust.inject.bind;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;
import me.yushust.inject.scope.Scope;

import java.lang.annotation.Annotation;

public interface BindingBuilder<T> {

    void scope(Scope scope);

    void singleton();

    interface Linkable<T> extends BindingBuilder<T> {

        BindingBuilder<T> to(Class<? extends T> target);

        BindingBuilder<T> to(Key<? extends T> target);

        BindingBuilder<T> toProvider(Provider<? extends T> provider);

        BindingBuilder<T> toProvider(Class<? extends Provider<? extends T>> provider);

        void toInstance(T instance);

    }

    interface Qualified<T> extends Linkable<T> {

        Linkable<T> qualified(Class<? extends Annotation> qualifierType);

        Linkable<T> qualified(Annotation qualifier);

        Linkable<T> named(String name);

    }

}
