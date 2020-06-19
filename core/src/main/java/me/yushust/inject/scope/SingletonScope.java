package me.yushust.inject.scope;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

class SingletonScope implements Scope {

    @Override
    public <T> Provider<T> wrap(Key<T> key, Provider<T> unscoped) {

        // the provider is already scoped
        if (unscoped instanceof SingletonProvider) {
            return unscoped;
        }

        return new SingletonProvider<>(unscoped);

    }

    static class SingletonProvider<T> implements Provider<T> {

        private final Lock instanceLock = new ReentrantLock();
        private final Provider<T> unscoped;
        private volatile T instance;

        SingletonProvider(Provider<T> unscoped) {
            this.unscoped = checkNotNull(unscoped);
        }

        @Inject
        public void injectMembersToProvider(Injector injector) {
            injector.injectMembers(unscoped);
        }

        @Override
        public T get() {

            if (instance == null) {
                instanceLock.lock();
                try {
                    if (instance == null) {
                        instance = unscoped.get();
                    }
                } finally {
                    instanceLock.unlock();
                }
            }

            return instance;

        }

    }

}
