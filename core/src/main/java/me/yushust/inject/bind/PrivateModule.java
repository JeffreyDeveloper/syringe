package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import static me.yushust.inject.internal.Preconditions.checkNotNull;
import static me.yushust.inject.internal.Preconditions.checkState;
import static me.yushust.inject.internal.Preconditions.checkArgument;

public abstract class PrivateModule implements Module {

    private PrivateBinder binder;

    @Override
    public final void configure(Binder binder) {

        checkNotNull(binder);
        checkState(this.binder == null, "Cannot configure linker while other linker is being configured");
        checkArgument(binder instanceof PrivateBinder, "The supplied linker isn't a IsolatedLinker");

        this.binder = (PrivateBinder) binder;
        this.configure();
        this.binder = null;

    }

    public final PrivateBinder getBinder() {
        checkState(this.binder != null, "Linker is not defined yet!");
        return binder;
    }

    protected abstract void configure();

    public final void install(Module module) {
        getBinder().install(module);
    }

    public final <T> BindingBuilder.Linkable<T> bind(Key<T> key) {
        return getBinder().bind(key);
    }

    public final <T> BindingBuilder.Qualified<T> bind(Class<T> key) {
        return getBinder().bind(key);
    }

    public final void expose(Class<?> key) {
        getBinder().expose(key);
    }

    public final void expose(Key<?> key) {
        getBinder().expose(key);
    }

    public final void expose(Token<?> token) {
        getBinder().expose(token);
    }

}
