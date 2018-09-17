package org.invertthepyramid.utils;

import java.util.function.Function;

public interface FunctionCurried<First, Second, Result> extends Function<First, Function<Second, Result>> {
}
