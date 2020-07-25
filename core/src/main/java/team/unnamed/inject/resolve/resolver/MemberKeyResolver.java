package team.unnamed.inject.resolve.resolver;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface MemberKeyResolver {

    Key<?> keyOf(TypeReference<?> declaringClass, Field field);

    Key<?> keyOf(TypeReference<?> declaringClass, Parameter parameter);

}
