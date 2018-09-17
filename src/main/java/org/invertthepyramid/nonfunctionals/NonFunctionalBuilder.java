package org.invertthepyramid.nonfunctionals;

import org.invertthepyramid.utils.Function2;

import java.util.function.Function;

public class NonFunctionalBuilder<From, To> {

    Function<From, To> delegate;

    public NonFunctionalBuilder(Function<From, To> delegate) {
        this.delegate = delegate;
    }

    public NonFunctionalBuilder<From, To> withMetrics(IMetrics metrics, String successful, String failure) {
        return new NonFunctionalBuilder<>(new MetricsDelegator<>(metrics, successful, failure, delegate));
    }

    public NonFunctionalBuilder<From, To> withLogger(ILogger logger, Function2<From, To, String> successful, Function2<From, RuntimeException, String> failure) {
        return new NonFunctionalBuilder<>(new LoggerDecorator<>(logger, successful, failure, delegate));
    }

    public Function<From, To> build() {
        return delegate;
    }
}
