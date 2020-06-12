package me.yushust.inject.internal.link.provider;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;

import java.util.Objects;

public class ClassLinkProvider<T> implements Provider<T> {

    @Inject private Injector injector;
    private final Class<T> target;

    public ClassLinkProvider(Class<T> target) {
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public T get() {
        return injector.getInstance(target);
    }

}
