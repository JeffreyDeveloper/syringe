package me.yushust.inject.process;

import me.yushust.inject.internal.MembersInjectorFactory;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;

public interface ProcessorInterceptor {

    InjectableMembersResolver interceptMembersResolver(InjectableMembersResolver resolver);

    MembersInjectorFactory interceptMembersInjectorFactory(MembersInjectorFactory membersInjectorFactory);

    InjectableConstructorResolver interceptConstructorResolver(InjectableConstructorResolver injectableConstructorResolver);

    static ProcessorInterceptor dummy() {
        return new DummyProcessorInterceptor();
    }

    class DummyProcessorInterceptor implements ProcessorInterceptor {

        @Override
        public InjectableMembersResolver interceptMembersResolver(InjectableMembersResolver resolver) {
            return resolver;
        }

        @Override
        public MembersInjectorFactory interceptMembersInjectorFactory(MembersInjectorFactory membersInjectorFactory) {
            return membersInjectorFactory;
        }

        @Override
        public InjectableConstructorResolver interceptConstructorResolver(InjectableConstructorResolver injectableConstructorResolver) {
            return injectableConstructorResolver;
        }

    }

}
