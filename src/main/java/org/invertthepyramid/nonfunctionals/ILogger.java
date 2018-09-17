package org.invertthepyramid.nonfunctionals;

import org.invertthepyramid.utils.Function2;
import org.invertthepyramid.utils.FunctionWithExceptionDecorator;

import java.util.function.Function;

public interface ILogger {
    void info(String message);

    void error(String message, Exception e);


}


