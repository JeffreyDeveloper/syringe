package team.unnamed.inject.identity.type.resolve;

import team.unnamed.inject.identity.type.TypeReference;

import java.lang.reflect.Type;

public interface TypeResolver {

    Type resolveType(TypeReference<?> context, Type type);

}
