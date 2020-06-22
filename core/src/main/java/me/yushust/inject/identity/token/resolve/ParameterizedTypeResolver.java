package me.yushust.inject.identity.token.resolve;

import me.yushust.inject.identity.token.ContextualTypes;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.identity.token.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static me.yushust.inject.internal.Preconditions.checkState;

public class ParameterizedTypeResolver implements TypeResolver {

    @Override
    public Type resolveType(Token<?> context, Type type) {

        checkState(type instanceof ParameterizedType, "Type isn't instance of ParameterizedType!");

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type ownerType = parameterizedType.getOwnerType();
        Type resolvedOwnerType = ContextualTypes.resolveContextually(context, ownerType);
        boolean changed = resolvedOwnerType != ownerType;

        Type[] actualTypeArgs = parameterizedType.getActualTypeArguments();

        for (int i = 0; i < actualTypeArgs.length; i++) {

            Type argument = actualTypeArgs[i];
            Type resolvedTypeArgument = ContextualTypes.resolveContextually(context, argument);

            if (resolvedTypeArgument == argument) {
                continue;
            }

            if (!changed) {
                actualTypeArgs = actualTypeArgs.clone();
                changed = true;
            }

            actualTypeArgs[i] = resolvedTypeArgument;

        }

        if (changed) {
            return Types.parameterizedTypeOf(resolvedOwnerType, parameterizedType.getRawType(), actualTypeArgs);
        }

        return parameterizedType;

    }

}
