package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.identity.type.TypeReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericInjectionTest {

    @Test
    public void injectGenerics() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(new TypeReference<Bar<String>>() {}).toInstance(new Bar<>("123"));
        });

        Foo<String> foo = injector.getInstance(new TypeReference<Foo<String>>() {});
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
