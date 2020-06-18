package me.yushust.inject.resolve;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Member;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InjectableMember {

    private final Token<?> declaringClass;
    private final Member member;
    private final List<KeyEntry<?>> keys;

    public InjectableMember(Token<?> declaringClass, Member member, List<KeyEntry<?>> keys) {
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

    public List<KeyEntry<?>> getKeys() {
        return keys;
    }

    public static class KeyEntry<T> {

        private final Key<T> key;
        private final boolean optional;

        public KeyEntry(Key<T> key, boolean optional) {
            checkNotNull(key);
            this.key = key;
            this.optional = optional;
        }

        public Key<T> getKey() {
            return key;
        }

        public boolean isOptional() {
            return optional;
        }

    }

}
