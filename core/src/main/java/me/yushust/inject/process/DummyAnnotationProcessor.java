package me.yushust.inject.process;

import me.yushust.inject.internal.InternalBinder;

public class DummyAnnotationProcessor implements BindingAnnotationProcessor, ScopeAnnotationProcessor {

    // a lazy-initialized instance
    public static final DummyAnnotationProcessor INSTANCE = new DummyAnnotationProcessor();

    private DummyAnnotationProcessor() {}

    @Override
    public <T> boolean bind(InternalBinder binder, Class<T> clazz) {
        // returns false, it doesn't check if clazz
        // is annotated with ImplementedBy, ProvidedBy or Exposed
        // use DefaultBindingAnnotationProcessor for
        // a real binding annotation processor.
        // enable it using
        // InjectorOptionsBuilder#enableBindingAnnotations
        return false;
    }

    @Override
    public <T> boolean scope(InternalBinder binder, Class<T> clazz) {
        return false;
    }

}
