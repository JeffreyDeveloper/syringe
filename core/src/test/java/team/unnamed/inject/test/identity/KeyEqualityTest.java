package team.unnamed.inject.test.identity;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.name.Names;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class KeyEqualityTest {

    @Test
    public void compare() {

        TypeReference<ArrayList<?>> arrayListTypeReference = new TypeReference<ArrayList<?>>() {};
        TypeReference<? extends List<?>> listTypeReference = new TypeReference<ArrayList<?>>() {};

        assertEquals(arrayListTypeReference, listTypeReference);

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
