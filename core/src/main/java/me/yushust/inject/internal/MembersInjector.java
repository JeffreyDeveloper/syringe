package me.yushust.inject.internal;

import me.yushust.inject.exception.UnsupportedInjectionException;

import java.lang.reflect.Member;
import java.util.Stack;

public interface MembersInjector {

    void injectMembers(Object instance, Stack<Member> injections) throws UnsupportedInjectionException;

}
