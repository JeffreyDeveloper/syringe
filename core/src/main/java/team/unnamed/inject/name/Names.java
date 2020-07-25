package team.unnamed.inject.name;

import java.lang.annotation.Annotation;

import static team.unnamed.inject.internal.Preconditions.checkNotNull;

public final class Names {

    private Names() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static Named named(String name) {
        checkNotNull(name);

        return new Named() {

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
                Named named = (Named) o;
                return name.equals(named.value());
            }

            @Override
            public int hashCode() {
                return (127 * "value".hashCode()) ^ name.hashCode();
            }

            @Override
            public String toString() {
                return "@" + Named.class.getName() + "(value=" + value() + ")";
            }
        };

    }

}
