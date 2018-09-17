package org.invertthepyramid.nonfunctionals;

import org.invertthepyramid.utils.FunctionWithExceptionDecorator;

import java.util.function.Function;

public interface IMetrics {
    void mark(String metricName, int count);

}

