package me.yushust.inject.test;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.identity.token.Token;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenericInjectionTest {

    @Test
    public void injectGenerics() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(new Token<Bar<String>>() {}).toInstance(new Bar<>("123"));
        });

        Foo<String> foo = injector.getInstance(new Token<Foo<String>>() {});
        assertEquals(foo.bar.value, "123");

    }

    public static class Foo<T> {

        @Inject
        private Bar<T> bar;

    }

    public static class Bar<T> {

        private final T value;

        public Bar(T value) {
            this.value = value;
        }
    }

}
