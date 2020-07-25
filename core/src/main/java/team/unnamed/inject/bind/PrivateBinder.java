package team.unnamed.inject.bind;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

public interface PrivateBinder extends Binder {

    <T> void expose(Class<T> key);

    <T> void expose(TypeReference<T> key);

    <T> void expose(Key<T> key);

}
