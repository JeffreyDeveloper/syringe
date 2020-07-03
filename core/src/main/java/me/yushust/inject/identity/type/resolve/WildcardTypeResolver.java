package me.yushust.inject.identity.type.resolve;

import me.yushust.inject.identity.type.ContextualTypes;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.identity.type.Types;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import static me.yushust.inject.internal.Preconditions.checkArgument;

public class WildcardTypeResolver implements TypeResolver {

    @Override
    public Type resolveType(TypeReference<?> context, Type type) {

        checkArgument(type instanceof WildcardType, "Type isn't instance of WildcardType!");

        WildcardType wildcard = (WildcardType) type;
        Type[] lowerBounds = wildcard.getLowerBounds();
        Type[] upperBounds = wildcard.getUpperBounds();

        if (lowerBounds.length == 1) {
            Type lowerBound = lowerBounds[0];
            Type resolvedLowerBound = ContextualTypes.resolveContextually(context, lowerBound);
            if (lowerBound == resolvedLowerBound) {
                return type;
            }
            return Types.wildcardSuperTypeOf(resolvedLowerBound);
        }

        if (upperBounds.length == 1) {
            Type upperBound = upperBounds[0];
            Type resolvedUpperBound = ContextualTypes.resolveContextually(context, upperBound);
            if (resolvedUpperBound != upperBound) {
                return Types.wildcardSubTypeOf(resolvedUpperBound);
            }
        }

        return type;
    }

}
