package me.yushust.inject;

import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.resolve.AnnotationOptionalInjectionChecker;
import me.yushust.inject.resolve.OptionalInjectionChecker;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InjectorOptions {

    private final OptionalInjectionChecker optionalInjectionChecker;
    private final ProcessorInterceptor processorInterceptor;

    private InjectorOptions(OptionalInjectionChecker optionalInjectionChecker, ProcessorInterceptor processorInterceptor) {
        this.optionalInjectionChecker = optionalInjectionChecker;
        this.processorInterceptor = processorInterceptor;
    }

    public OptionalInjectionChecker getOptionalInjectionChecker() {
        return optionalInjectionChecker;
    }

    public ProcessorInterceptor getProcessorInterceptor() {
        return processorInterceptor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private OptionalInjectionChecker optionalInjectionChecker = new AnnotationOptionalInjectionChecker();
        private ProcessorInterceptor processorInterceptor = ProcessorInterceptor.dummy();

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

        public InjectorOptions build() {
            return new InjectorOptions(optionalInjectionChecker, processorInterceptor);
        }

    }

}
