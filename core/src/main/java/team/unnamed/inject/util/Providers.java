package team.unnamed.inject.util;

import team.unnamed.inject.Provider;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

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
