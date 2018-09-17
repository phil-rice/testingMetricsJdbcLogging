package org.invertthepyramid.utils;

import org.invertthepyramid.Sideeffect2;

import java.util.function.Function;

public class Exceptions {

    static <From, To> Function<From, To> call(Sideeffect2<From, To> onSuccess, Sideeffect2<From, RuntimeException> onFailure, Function<From, To> delegate) {
        return (from) -> {
            try {
                To result = delegate.apply(from);
                onSuccess.apply(from, result);
                return result;
            } catch (RuntimeException e) {
                onFailure.apply(from, e);
                throw e;
            }
        };
    }

}
