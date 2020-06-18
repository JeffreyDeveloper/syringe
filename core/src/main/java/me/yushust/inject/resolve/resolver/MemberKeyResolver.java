package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface MemberKeyResolver {

    Key<?> keyOf(Field field);

    Key<?> keyOf(Parameter parameter);

}
