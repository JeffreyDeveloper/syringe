package team.unnamed.inject.process;

import team.unnamed.inject.internal.MembersInjectorFactory;
import team.unnamed.inject.resolve.resolver.InjectableConstructorResolver;
import team.unnamed.inject.resolve.resolver.InjectableMembersResolver;

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
