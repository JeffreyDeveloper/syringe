package me.yushust.inject.identity.type.resolve;

import me.yushust.inject.identity.type.TypeReference;

import java.lang.reflect.Type;

public interface TypeResolver {

    Type resolveType(TypeReference<?> context, Type type);

}
