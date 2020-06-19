package me.yushust.inject.internal.bind;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InstanceBinding<T> extends SimpleBinding<T> {

    public InstanceBinding(Key<T> key, T instance) {
        super(key, new InstanceProvider<>(instance));
    }

    public static class InstanceProvider<T> implements Provider<T> {

        private final T instance;

        public InstanceProvider(T instance) {
            this.instance = checkNotNull(instance);
        }

        @Inject
        public void injectMembersToInstance(Injector injector) {
            injector.injectMembers(instance);
        }

        @Override
        public T get() {
            return instance;
        }
    }

}
