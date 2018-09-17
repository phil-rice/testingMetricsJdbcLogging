package org.invertthepyramid.utils;

import org.invertthepyramid.Sideeffect2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FunctionWithExceptionDecorator<From, To> implements Function<From, To> {

    Sideeffect2<From, To> onSuccess;
    Sideeffect2<From, RuntimeException> onFailure;
    Function<From, To> delegate;

    public FunctionWithExceptionDecorator(Sideeffect2<From, To> onSuccess, Sideeffect2<From, RuntimeException> onFailure, Function<From, To> delegate) {
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        this.delegate = delegate;
    }

    @Override
    public To apply(From from) {
        try {
            To result = delegate.apply(from);
            onSuccess.apply(from, result);
            return result;
        } catch (RuntimeException e) {
            onFailure.apply(from, e);
            throw e;
        }
    }


    public static <From, To> List<String> delegateNames(Function<From, To> function) {
        List<String> result = new ArrayList<>();
        if (function instanceof FunctionWithExceptionDecorator) {
            result.add(function.getClass().getSimpleName());
            result.addAll(delegateNames(((FunctionWithExceptionDecorator) function).delegate));
        }
        return result;
    }

    public static <From, To> Function<From, To> getFinalDelegate(Function<From, To> function) {
        if (function instanceof FunctionWithExceptionDecorator)
            return getFinalDelegate(((FunctionWithExceptionDecorator) function).delegate);
        else
            return function;
    }

}
