package team.unnamed.inject.internal.bind;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.Provider;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.internal.Preconditions;

public class LinkedBinding<T> extends SimpleBinding<T> {

    public LinkedBinding(Key<T> key, Key<? extends T> linkedKey) {
        super(key, new LinkedKeyProvider<>(linkedKey, key.equals(linkedKey)));
    }

    public static class LinkedKeyProvider<T> implements Provider<T> {

        private final Key<? extends T> linkedKey;
        private final boolean ignoreExplicitBindings; // avoid StackOverflowError
        @Inject
        private Injector injector;

        public LinkedKeyProvider(Key<? extends T> linkedKey, boolean ignoreExplicitBindings) {
            this.linkedKey = Preconditions.checkNotNull(linkedKey);
            this.ignoreExplicitBindings = ignoreExplicitBindings;
        }

        @Override
        public T get() {
            return injector.getInstance(linkedKey, ignoreExplicitBindings);
        }
    }

}
