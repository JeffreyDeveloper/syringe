package me.yushust.inject.resolve;

import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Member;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InjectableMember {

    private final Token<?> declaringClass;
    private final Member member;
    private final List<ResolvableKey<?>> keys;

    public InjectableMember(Token<?> declaringClass, Member member, List<ResolvableKey<?>> keys) {
        this.declaringClass = checkNotNull(declaringClass);
        this.member = checkNotNull(member);
        this.keys = unmodifiableList(keys);
    }

    public Token<?> getDeclaringClass() {
        return declaringClass;
    }

    public Member getMember() {
        return member;
    }

    public List<ResolvableKey<?>> getKeys() {
        return keys;
    }

}
