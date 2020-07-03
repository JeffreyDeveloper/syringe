package me.yushust.inject.test.resolve;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.resolve.AnnotationTypeHandler;
import me.yushust.inject.resolve.resolver.MemberKeyResolver;
import me.yushust.inject.resolve.resolver.ReflectionMemberKeyResolver;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldKeyResolveTest {

    @Test
    public void resolveFieldKey() {

        MemberKeyResolver keyResolver = new ReflectionMemberKeyResolver(new AnnotationTypeHandler());
        TypeReference<Foo<String>> typeReference = new TypeReference<Foo<String>>() {};

        Key<?>[] expectedKeys = {
                new Key<String>() {},
                new Key<List<String>>() {},
                new Key<Map<String, String>>() {}
        };
        Field[] fields = typeReference.getRawType().getDeclaredFields();

        for (int i = 0; i < fields.length; i ++) {
            Key<?> expectedKey = expectedKeys[i];
            Field field = fields[i];
            Key<?> key = keyResolver.keyOf(typeReference, field);

            assertEquals(expectedKey, key);
        }

    }

    public static class Foo<T> {

        private String string;
        private List<T> list;
        private Map<String, T> map;

    }

}
