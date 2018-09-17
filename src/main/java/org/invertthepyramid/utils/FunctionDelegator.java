package org.invertthepyramid.utils;

import java.util.function.Function;

public interface FunctionDelegator<From,To> extends Function<Function<From,To>, Function<From,To>> {
}
