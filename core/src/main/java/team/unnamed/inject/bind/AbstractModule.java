package team.unnamed.inject.bind;

import team.unnamed.inject.identity.Key;
import static team.unnamed.inject.internal.Preconditions.checkNotNull;
import static team.unnamed.inject.internal.Preconditions.checkState;

public abstract class AbstractModule implements Module {

    Binder binder;

    @Override
    public final void configure(Binder binder) {

        checkNotNull(binder);
        checkState(this.binder == null, "Cannot configure linker while other linker is being configured");

        this.binder = binder;
        this.configure();
        this.binder = null;

    }

    public final Binder getBinder() {
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

}
