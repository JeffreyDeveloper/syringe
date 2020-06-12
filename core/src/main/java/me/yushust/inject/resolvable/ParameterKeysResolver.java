package me.yushust.inject.resolvable;

import me.yushust.inject.identity.Key;

import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.util.List;

public interface ParameterKeysResolver {

    List<Key<?>> resolveParameterKeys(Member methodOrConstructor);

    <T> Key<T> getParameterKey(Parameter parameter);

}
