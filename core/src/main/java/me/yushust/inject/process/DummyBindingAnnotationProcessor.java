package me.yushust.inject.process;

import me.yushust.inject.internal.InternalBinder;

public class DummyBindingAnnotationProcessor implements BindingAnnotationProcessor {

    // a lazy-initialized instance
    public static final DummyBindingAnnotationProcessor INSTANCE = new DummyBindingAnnotationProcessor();

    private DummyBindingAnnotationProcessor() {}

    @Override
    public <T> boolean bind(InternalBinder binder, Class<T> clazz) {
        // returns false, it doesn't check if clazz
        // is annotated with ImplementedBy or ProvidedBy
        // use DefaultBindingAnnotationProcessor for
        // a real binding annotation processor.
        // enable it using
        // InjectorOptionsBuilder#enableBindingAnnotations
        return false;
    }

}
