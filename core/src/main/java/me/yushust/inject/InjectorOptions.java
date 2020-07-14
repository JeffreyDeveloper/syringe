package me.yushust.inject;

import me.yushust.inject.process.*;
import me.yushust.inject.resolve.AnnotationOptionalInjectionChecker;
import me.yushust.inject.resolve.OptionalInjectionChecker;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InjectorOptions {

    private final OptionalInjectionChecker optionalInjectionChecker;
    private final ProcessorInterceptor processorInterceptor;
    private final BindingAnnotationProcessor bindingAnnotationProcessor;
    private final ScopeAnnotationProcessor scopeAnnotationProcessor;
    private final boolean requireResolveAnnotation;

    private InjectorOptions(OptionalInjectionChecker optionalInjectionChecker, ProcessorInterceptor processorInterceptor,
                            BindingAnnotationProcessor bindingProcessor, ScopeAnnotationProcessor scopeProcessor,
                            boolean requireResolveAnnotation) {
        this.optionalInjectionChecker = optionalInjectionChecker;
        this.processorInterceptor = processorInterceptor;
        this.bindingAnnotationProcessor = bindingProcessor;
        this.scopeAnnotationProcessor = scopeProcessor;
        this.requireResolveAnnotation = requireResolveAnnotation;
    }

    public OptionalInjectionChecker getOptionalInjectionChecker() {
        return optionalInjectionChecker;
    }

    public ProcessorInterceptor getProcessorInterceptor() {
        return processorInterceptor;
    }

    public BindingAnnotationProcessor getBindingAnnotationProcessor() {
        return bindingAnnotationProcessor;
    }

    public ScopeAnnotationProcessor getScopeAnnotationProcessor() {
        return scopeAnnotationProcessor;
    }

    public boolean requiresResolveAnnotation() {
        return requireResolveAnnotation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private OptionalInjectionChecker optionalInjectionChecker = new AnnotationOptionalInjectionChecker();
        private ProcessorInterceptor processorInterceptor = ProcessorInterceptor.dummy();
        private BindingAnnotationProcessor bindingAnnotationProcessor = DummyAnnotationProcessor.INSTANCE;
        private ScopeAnnotationProcessor scopeAnnotationProcessor = DummyAnnotationProcessor.INSTANCE;
        private boolean requireResolveAnnotation = false;

        public Builder optionalInjectionChecker(OptionalInjectionChecker optionalInjectionChecker) {
            checkNotNull(optionalInjectionChecker);
            this.optionalInjectionChecker = optionalInjectionChecker;
            return this;
        }

        public Builder processorInterceptor(ProcessorInterceptor processorInterceptor) {
            checkNotNull(processorInterceptor);
            this.processorInterceptor = processorInterceptor;
            return this;
        }

        public Builder requireResolveAnnotation() {
            this.requireResolveAnnotation = true;
            return this;
        }

        public Builder enableTypeBindingAnnotations() {
            bindingAnnotationProcessor = new DefaultBindingAnnotationProcessor();
            return this;
        }

        public Builder enableTypeScopingAnnotations() {
            scopeAnnotationProcessor = new DefaultScopeAnnotationProcessor();
            return this;
        }

        public InjectorOptions build() {
            return new InjectorOptions(
                    optionalInjectionChecker, processorInterceptor, bindingAnnotationProcessor,
                    scopeAnnotationProcessor, requireResolveAnnotation
            );
        }

    }

}
