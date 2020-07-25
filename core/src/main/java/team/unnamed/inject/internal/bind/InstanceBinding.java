package team.unnamed.inject.internal.bind;

import team.unnamed.inject.Provider;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.internal.Preconditions;

public class InstanceBinding<T> extends SimpleBinding<T> {

    public InstanceBinding(Key<T> key, T instance) {
        super(key, new InstanceProvider<>(instance));
    }

    public static class InstanceProvider<T> implements Provider<T> {

        private final T instance;

        public InstanceProvider(T instance) {
            this.instance = Preconditions.checkNotNull(instance);
        }

        @Override
        public T get() {
            return instance;
        }
    }

}
