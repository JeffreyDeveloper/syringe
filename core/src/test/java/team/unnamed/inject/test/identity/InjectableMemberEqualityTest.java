package team.unnamed.inject.test.identity;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.name.Named;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InjectableMemberEqualityTest {

    @Test
    public void testEquality() {

        Injector injector = InjectorFactory.create(binder -> binder.bind(String.class).named("bar").toInstance("asd"));
        Foo foo = injector.getInstance(Foo.class);
        assertEquals(foo.value, "asd");

    }

    public static class Foo {

        @Inject
        @Named("bar")
        private String value;

    }

}
