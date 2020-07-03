package me.yushust.inject.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Recursion {

    private Recursion() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static Set<Field> getFieldsRecursively(Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        Class<?> current = clazz;
        while (current != Object.class) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    public static Set<Method> getMethodsRecursively(Class<?> clazz) {
        Set<Method> methods = new HashSet<>();
        Class<?> current = clazz;
        while (current != Object.class) {
            methods.addAll(Arrays.asList(current.getDeclaredMethods()));
            current = current.getSuperclass();
        }
        return methods;
    }

}
