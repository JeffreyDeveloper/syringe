package me.yushust.inject.identity.token.resolve;

import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Type;

public interface TypeResolver {

    Type resolveType(Token<?> context, Type type);

}
