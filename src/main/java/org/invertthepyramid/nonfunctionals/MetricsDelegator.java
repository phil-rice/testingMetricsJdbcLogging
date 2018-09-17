package org.invertthepyramid.nonfunctionals;

import org.invertthepyramid.utils.FunctionWithExceptionDecorator;

import java.util.function.Function;

public class MetricsDelegator<From, To> extends FunctionWithExceptionDecorator<From, To> {

    public MetricsDelegator(IMetrics metrics, String successName, String failureName, Function<From, To> delegate) {
        super((from, to) -> metrics.mark(successName, 1), (from, e) -> metrics.mark(failureName, 1), delegate);
    }
}
