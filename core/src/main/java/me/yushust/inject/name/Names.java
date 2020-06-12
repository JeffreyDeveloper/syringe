package me.yushust.inject.name;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class Names {

    private Names() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static Named named(String name) {
        Objects.requireNonNull(name);
        return new NamedImpl(name);
    }

    public static class NamedImpl implements Named {

        private final String name;

        public NamedImpl(String name) {
            Objects.requireNonNull(name);
            this.name = name;
        }

        @Override
        public String value() {
            return name;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Named.class;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Named)) {
                return false;
            }
            NamedImpl named = (NamedImpl) o;
            return name.equals(named.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

}
