package team.unnamed.inject.internal;

import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.internal.injector.ConstructorInjector;

public interface MembersInjectorFactory {

    MembersInjector getMembersInjector(TypeReference<?> key);

    <T> ConstructorInjector<T> getConstructorInjector(TypeReference<T> key);

    MembersInjectorFactory usingInjector(InternalInjector injector);

}
