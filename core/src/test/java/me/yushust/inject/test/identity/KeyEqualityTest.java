package me.yushust.inject.test.identity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.name.Named;
import me.yushust.inject.name.Names;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class KeyEqualityTest {

    @Test
    public void compare() {

        Token<ArrayList<?>> arrayListToken = new Token<ArrayList<?>>() {};
        Token<? extends List<?>> listToken = new Token<ArrayList<?>>() {};

        assertEquals(arrayListToken, listToken);

        Key<ArrayList<?>> arrayListKey = new Key<ArrayList<?>>() {};
        Key<List<?>> listKey = new Key<List<?>>() {};

        assertNotEquals(arrayListKey, listKey);

        Key<ArrayList<?>> annotatedArrayListKey = new Key<ArrayList<?>>(Named.class, Names.named("hey")) {};
        Key<? extends List<?>> annotatedListKey = new Key<ArrayList<?>>(Named.class, Names.named("hey")) {};

        assertEquals(annotatedArrayListKey, annotatedListKey);

        Key<ArrayList<?>> annotatedArrayList = new Key<ArrayList<?>>(null, Names.named("hey")) {};
        Key<ArrayList<?>> annotatedArrayList2 = new Key<ArrayList<?>>(Named.class, Names.named("hey")) {};

        assertEquals(annotatedArrayList, annotatedArrayList2);

    }

}
