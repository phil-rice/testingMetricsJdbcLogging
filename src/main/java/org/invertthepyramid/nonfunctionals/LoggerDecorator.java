package org.invertthepyramid.nonfunctionals;

import org.invertthepyramid.utils.Function2;
import org.invertthepyramid.utils.FunctionWithExceptionDecorator;

import java.util.function.Function;

public class LoggerDecorator<From, To> extends FunctionWithExceptionDecorator<From, To> {

    public LoggerDecorator(ILogger logger, Function2<From, To, String> success, Function2<From, RuntimeException, String> failure, Function<From, To> delegate) {
        super((from2, to) -> logger.info(success.apply(from2, to)), (from3, e) -> logger.error(failure.apply(from3, e), e), delegate);
    }

}
