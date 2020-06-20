package me.yushust.inject.test.identity;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.name.Named;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InjectableMemberEqualityTest {

    @Test
    public void testEquality() {

        Injector injector = InjectorFactory.create(binder -> binder.bind(String.class).named("bar").toInstance("asd"));
        Foo foo = injector.getInstance(Foo.class);
        assertEquals(foo.value, "asd");

    }

    public static class Foo {

        @Inject @Named("bar")
        private String value;

    }

}
