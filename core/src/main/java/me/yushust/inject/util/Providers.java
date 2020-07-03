package me.yushust.inject.util;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class Providers {

    private Providers() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static <T> Key<T> keyOfProvider(Key<Provider<T>> providerKey) {
        Type type = providerKey.getType().getType();

        if (!(type instanceof ParameterizedType)) { // it's raw-type provider
            return null;
        }

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type parameterType = parameterizedType.getActualTypeArguments()[0];

        return Key.of(TypeReference.of(parameterType));
    }

}
