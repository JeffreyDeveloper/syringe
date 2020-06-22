package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import static me.yushust.inject.internal.Preconditions.checkState;

public abstract class PrivateModule extends AbstractModule {

    public final PrivateBinder getPrivateBinder() {
        Binder binder = getBinder();
        checkState(binder instanceof PrivateBinder, "Binder isn't a Private Binder!");
        return (PrivateBinder) binder;
    }

    public final void expose(Class<?> key) {
        getPrivateBinder().expose(key);
    }

    public final void expose(Key<?> key) {
        getPrivateBinder().expose(key);
    }

    public final void expose(Token<?> token) {
        getPrivateBinder().expose(token);
    }

}
