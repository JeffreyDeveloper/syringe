package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.TypeReference;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface MemberKeyResolver {

    Key<?> keyOf(TypeReference<?> declaringClass, Field field);

    Key<?> keyOf(TypeReference<?> declaringClass, Parameter parameter);

}
