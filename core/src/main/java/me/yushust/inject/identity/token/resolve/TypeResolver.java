package me.yushust.inject.identity.token.resolve;

import me.yushust.inject.identity.token.TypeReference;

import java.lang.reflect.Type;

public interface TypeResolver {

    Type resolveType(TypeReference<?> context, Type type);

}
