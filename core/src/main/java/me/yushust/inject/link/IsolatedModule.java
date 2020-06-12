package me.yushust.inject.link;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import static me.yushust.inject.internal.Preconditions.*;

public abstract class IsolatedModule implements Module {

    private IsolatedLinker linker;

    @Override
    public final void configure(Linker linker) {

        checkNotNull(linker);
        checkState(this.linker == null, "Cannot configure linker while other linker is being configured");
        checkArgument(linker instanceof IsolatedLinker, "The supplied linker isn't a IsolatedLinker");

        this.linker = (IsolatedLinker) linker;
        this.configure();
        this.linker = null;

    }

    public final IsolatedLinker getLinker() {
        checkState(this.linker != null, "Linker is not defined yet!");
        return linker;
    }

    protected abstract void configure();

    public final void install(Module module) {
        getLinker().install(module);
    }

    public final <T> LinkBuilder.Linkable<T> link(Key<T> key) {
        return getLinker().link(key);
    }

    public final <T> LinkBuilder.Qualified<T> link(Class<T> key) {
        return getLinker().link(key);
    }

    public final void expose(Class<?> key) {
        getLinker().expose(key);
    }

    public final void expose(Key<?> key) {
        getLinker().expose(key);
    }

    public final void expose(Token<?> token) {
        getLinker().expose(token);
    }

}
