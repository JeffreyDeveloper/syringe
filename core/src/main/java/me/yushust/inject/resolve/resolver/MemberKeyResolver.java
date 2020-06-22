package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface MemberKeyResolver {

    Key<?> keyOf(Token<?> declaringClass, Field field);

    Key<?> keyOf(Token<?> declaringClass, Parameter parameter);

}
