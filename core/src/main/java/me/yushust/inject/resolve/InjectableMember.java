package me.yushust.inject.resolve;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;
import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InjectableMember {

    private final Class<?> declaringClass;
    private final Member member;
    private final List<ResolvableKey<?>> keys;

    public InjectableMember(Class<?> declaringClass, Member member, List<ResolvableKey<?>> keys) {
        this.declaringClass = checkNotNull(declaringClass);
        this.member = checkNotNull(member);
        this.keys = unmodifiableList(keys);
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public Member getMember() {
        return member;
    }

    public List<ResolvableKey<?>> getKeys() {
        return keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InjectableMember that = (InjectableMember) o;
        return Objects.equals(declaringClass, that.declaringClass) &&
                Objects.equals(member, that.member) &&
                Objects.equals(keys, that.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaringClass, member, keys);
    }

}
