package me.yushust.inject.internal.bind;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class LinkedBinding<T> extends SimpleBinding<T> {

    public LinkedBinding(Key<T> key, Key<? extends T> linkedKey) {
        super(key, new LinkedKeyProvider<>(linkedKey));
    }

    public static class LinkedKeyProvider<T> implements Provider<T> {

        private final Key<? extends T> linkedKey;
        @Inject private Injector injector;

        public LinkedKeyProvider(Key<? extends T> linkedKey) {
            this.linkedKey = checkNotNull(linkedKey);
        }

        public Key<? extends T> getLinkedKey() {
            return linkedKey;
        }

        @Override
        public T get() {
            return injector.getInstance(linkedKey);
        }
    }

}
