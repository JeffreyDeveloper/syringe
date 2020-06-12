package me.yushust.inject.link;

import me.yushust.inject.identity.Key;

public abstract class AbstractModule implements Module {

    private Linker linker;

    @Override
    public final void configure(Linker linker) {

        if (linker == null) {
            throw new NullPointerException();
        }

        if (this.linker != null) {
            throw new IllegalStateException();
        }

        this.linker = linker;
        this.configure();
    }

    public final Linker getLinker() {
        if (linker == null) {
            throw new IllegalStateException("Linker isn't defined yet!");
        }
        return linker;
    }

    protected void configure() {

    }

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
