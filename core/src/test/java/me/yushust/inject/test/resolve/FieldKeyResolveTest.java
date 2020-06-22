package me.yushust.inject.test.resolve;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolve.AnnotationTypeHandler;
import me.yushust.inject.resolve.resolver.MemberKeyResolver;
import me.yushust.inject.resolve.resolver.ReflectionMemberKeyResolver;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FieldKeyResolveTest {

    @Test
    public void resolveFieldKey() {

        MemberKeyResolver keyResolver = new ReflectionMemberKeyResolver(new AnnotationTypeHandler());
        Token<Foo<String>> token = new Token<Foo<String>>() {};

        Key<?>[] expectedKeys = {
                new Key<String>() {},
                new Key<List<String>>() {},
                new Key<Map<String, String>>() {}
        };
        Field[] fields = token.getRawType().getDeclaredFields();

        for (int i = 0; i < fields.length; i ++) {
            Key<?> expectedKey = expectedKeys[i];
            Field field = fields[i];
            Key<?> key = keyResolver.keyOf(token, field);

            assertEquals(expectedKey, key);
        }

    }

    public static class Foo<T> {

        private String string;
        private List<T> list;
        private Map<String, T> map;

    }

}
