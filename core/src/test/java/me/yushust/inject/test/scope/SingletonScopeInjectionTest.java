package me.yushust.inject.test.scope;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.scope.Scopes;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class SingletonScopeInjectionTest {

    @Test
    public void testInjection() {

        Injector injector = InjectorFactory.create(linker -> {
            linker.link(Foo.class).toProvider(Foo::new).scope(Scopes.SINGLETON);
        });

        Bar bar = injector.getInstance(Bar.class);
        Assert.assertEquals(bar.foo1, bar.foo2);

    }

    public static class Bar {

        @Inject private Foo foo1;
        @Inject private Foo foo2;

    }

    public static class Foo {

        private double random = Math.random();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Foo)) {
                return false;
            }
            Foo foo = (Foo) o;
            return Double.compare(foo.random, random) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(random);
        }
    }

}
