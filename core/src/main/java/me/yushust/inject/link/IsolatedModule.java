package me.yushust.inject.link;

public interface IsolatedModule extends Module {

    @Override
    default void configure(Linker linker) {
        if (!(linker instanceof IsolatedLinker)) {
            throw new IllegalArgumentException("The supplied linker isn't a IsolatedLinker");
        }
        configure((IsolatedLinker) linker);
    }

    void configure(IsolatedLinker linker);

}
