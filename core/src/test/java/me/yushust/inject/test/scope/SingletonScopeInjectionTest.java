package me.yushust.inject.test.scope;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.scope.Scopes;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingletonScopeInjectionTest {

    @Test
    public void testInjection() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Foo.class).toProvider(Foo::new).scope(Scopes.SINGLETON);
        });

        Foo foo1 = injector.getInstance(Foo.class);
        Foo foo2 = injector.getInstance(Foo.class);

        assertEquals(foo1, foo2);

    }

    public static class Foo {

        private final double random = Math.random();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Foo)) {
                return false;
            }
            Foo foo = (Foo) o;
            return random == foo.random;
        }

        @Override
        public int hashCode() {
            return Objects.hash(random);
        }
    }

}
