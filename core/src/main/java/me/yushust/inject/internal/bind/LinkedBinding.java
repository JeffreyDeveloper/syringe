package me.yushust.inject.internal.bind;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class LinkedBinding<T> extends SimpleBinding<T> {

    public LinkedBinding(Key<T> key, Key<? extends T> linkedKey) {
        super(key, new LinkedKeyProvider<>(linkedKey, key.equals(linkedKey)));
    }

    public static class LinkedKeyProvider<T> implements Provider<T> {

        private final Key<? extends T> linkedKey;
        private final boolean ignoreExplicitBindings; // avoid StackOverflowError
        @Inject private Injector injector;

        public LinkedKeyProvider(Key<? extends T> linkedKey, boolean ignoreExplicitBindings) {
            this.linkedKey = checkNotNull(linkedKey);
            this.ignoreExplicitBindings = ignoreExplicitBindings;
        }

        @Override
        public T get() {
            return injector.getInstance(linkedKey, ignoreExplicitBindings);
        }
    }

}
