package me.yushust.inject.test;

import me.yushust.inject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParentClassesInjectionTest {

    @Test
    public void inject() {

        Injector injector = InjectorFactory.create(
                InjectorOptions.builder()
                        .requireResolveAnnotation()
                        .build()
        );

        Bar bar = injector.getInstance(Bar.class);

        assertNotNull(bar.something2);
        assertNotNull(bar.something);

        BarButDoesntCheckParentClasses bar2 = injector.getInstance(BarButDoesntCheckParentClasses.class);

        assertNull(bar2.something);
        assertNotNull(bar2.something2);

    }

    public static class Something {

    }

    @Resolve(checkParentClasses = false)
    public static class Foo {

        @Inject protected Something something;

    }

    @Resolve
    public static class Bar extends Foo {

        @Inject private Something something2;

    }

    @Resolve(checkParentClasses = false)
    public static class BarButDoesntCheckParentClasses extends Foo {

        @Inject private Something something2;

    }

}
