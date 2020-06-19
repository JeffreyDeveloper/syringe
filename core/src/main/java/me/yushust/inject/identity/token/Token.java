package me.yushust.inject.identity.token;

import me.yushust.inject.internal.Preconditions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Token<T> {

    private final Class<? super T> rawType;
    private final Type type;

    @SuppressWarnings("unchecked")
    protected Token() {

        Type superClass = getClass().getGenericSuperclass();

        if (superClass instanceof Class) {
            throw new IllegalStateException("There're no type parameters!");
        }

        ParameterizedType parameterized = (ParameterizedType) superClass;

        this.type = Types.wrap(parameterized.getActualTypeArguments()[0]);
        this.rawType = (Class<? super T>) Types.getRawType(type);
    }

    @SuppressWarnings("unchecked")
    public Token(Type type) {
        Preconditions.checkNotNull(type);
        this.type = Types.wrap(type);
        this.rawType = (Class<? super T>) Types.getRawType(this.type);
    }

    public final Class<? super T> getRawType() {
        return rawType;
    }

    public final Type getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        return type.hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Token<?>)) {
            return false;
        }

        Token<?> other = (Token<?>) o;
        return Types.typeEquals(type, other.type);
    }

    @Override
    public final String toString() {
        return Types.asString(type);
    }

    public static Token<?> of(Type rawType, Type... typeArguments) {
        Preconditions.checkNotNull(rawType);
        return new Token<>(new ParameterizedToken(null, rawType, typeArguments));
    }

}