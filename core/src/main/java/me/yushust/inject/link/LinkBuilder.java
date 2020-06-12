package me.yushust.inject.link;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;
import me.yushust.inject.scope.Scope;

import java.lang.annotation.Annotation;

public interface LinkBuilder<T> {

    void scope(Scope scope);

    interface Linkable<T> extends LinkBuilder<T> {

        LinkBuilder<T> to(Class<? extends T> target);

        LinkBuilder<T> to(Key<? extends T> target);

        LinkBuilder<T> toProvider(Provider<? extends T> provider);

        void toInstance(T instance);

    }

    interface Qualified<T> extends Linkable<T> {

        Linkable<T> qualified(Class<? extends Annotation> qualifierType);

        Linkable<T> qualified(Annotation qualifier);

        Linkable<T> named(String name);

    }

}
