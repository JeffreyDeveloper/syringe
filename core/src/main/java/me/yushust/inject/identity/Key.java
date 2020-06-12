package me.yushust.inject.identity;

import me.yushust.inject.identity.token.Token;
import me.yushust.inject.identity.token.Types;
import static me.yushust.inject.internal.Preconditions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class Key<T> {

    private final Token<T> type;
    private final Class<? extends Annotation> qualifierType;
    private final Annotation qualifier;

    protected Key(Class<? extends Annotation> qualifierType, Annotation qualifier) {

        Type superClass = getClass().getGenericSuperclass();

        if (superClass instanceof Class) {
            throw new IllegalStateException("There're no type parameters!");
        }

        ParameterizedType parameterized = (ParameterizedType) superClass;

        this.type = new Token<>(parameterized.getActualTypeArguments()[0]);
        this.qualifierType = qualifierType;
        this.qualifier = qualifier;

    }

    protected Key() {
        this(null, null);
    }

    private Key(Token<T> token, Class<? extends Annotation> qualifierType, Annotation qualifier) {
        this.type = checkNotNull(token);
        this.qualifierType = qualifierType;
        this.qualifier = qualifier;
    }

    public final Token<T> getType() {
        return type;
    }

    public final Annotation getQualifier() {
        return qualifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key<?> key = (Key<?>) o;
        return type.equals(key.type) &&
                Objects.equals(qualifierType, key.qualifierType) &&
                Objects.equals(qualifier, key.qualifier);
    }

    @Override
    public int hashCode() {
        return type.hashCode() * 31 + (qualifierType == null ? 0 : qualifierType.hashCode())
                + (qualifier == null ? 0 : qualifier.hashCode());
    }

    @Override
    public String toString() {
        return Types.asString(type.getType());
    }

    public static <T> Key<T> of(Class<T> type) {
        return of(type, null, null);
    }

    public static <T> Key<T> of(Class<T> type, Annotation annotation) {
        return of(type, annotation.annotationType(), annotation);
    }

    public static <T> Key<T> of(Class<T> type, Class<? extends Annotation> qualifierType, Annotation qualifier) {
        return of(new Token<>(type), qualifierType, qualifier);
    }

    public static <T> Key<T> of(Token<T> type) {
        return of(type, null, null);
    }

    public static <T> Key<T> of(Token<T> type, Annotation annotation) {
        return of(type, annotation.annotationType(), annotation);
    }

    public static <T> Key<T> of(Token<T> type, Class<? extends Annotation> qualifierType, Annotation qualifier) {
        return new Key<>(type, qualifierType, qualifier);
    }

}
