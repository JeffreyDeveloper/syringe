package me.yushust.inject.test;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.scope.Scopes;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ConstructorInjectionTest {

    @Test
    public void test() {

        Injector injector = InjectorFactory.create(linker -> {
        });
        Foo foo = injector.getInstance(Foo.class);

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
