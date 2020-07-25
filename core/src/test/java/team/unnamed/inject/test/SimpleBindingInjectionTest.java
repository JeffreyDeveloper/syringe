package team.unnamed.inject.test;

import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleBindingInjectionTest {

    @Test
    public void testInjection() {

        Injector injector = InjectorFactory.create(linker -> {
            linker.bind(Foo.class).to(Bar.class);
        });

        Foo foo = injector.getInstance(Foo.class);
        assertNotNull(foo);

    }

    public interface Foo {}

    public static class Bar implements Foo {}

}
