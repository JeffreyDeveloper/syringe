package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConstructorInjectionTest {

    @Test
    public void test() {

        Injector injector = InjectorFactory.create();
        Foo foo = injector.getInstance(Foo.class);
        assertNotNull(foo);

    }

    public static class Foo {

        @Inject
        public Foo(Bar bar, @Nullable List<String> list) {
            assertNotNull(bar);
            assertNull(list);
        }

    }

    public static class Bar {

    }

}
