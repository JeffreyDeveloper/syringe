package team.unnamed.inject.bind;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

import static team.unnamed.inject.internal.Preconditions.checkState;

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

    public final void expose(TypeReference<?> typeReference) {
        getPrivateBinder().expose(typeReference);
    }

}
