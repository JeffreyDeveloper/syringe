package me.yushust.inject.link;

import me.yushust.inject.identity.Key;
import static me.yushust.inject.internal.Preconditions.*;

public abstract class AbstractModule implements Module {

    private Linker linker;

    @Override
    public final void configure(Linker linker) {

        checkNotNull(linker);
        checkState(this.linker == null, "Cannot configure linker while other linker is being configured");

        this.linker = linker;
        this.configure();
        this.linker = null;

    }

    public final Linker getLinker() {
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

}
