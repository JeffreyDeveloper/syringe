package me.yushust.inject.scope;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;
import me.yushust.inject.Injector;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SingletonScope implements Scope {

    @Override
    public <T> Provider<T> wrap(Key<T> key, Provider<T> unscoped) {

        return new Provider<T>() {

            private final Lock instanceLock = new ReentrantLock();
            private volatile T instance;

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
        };

    }

}
