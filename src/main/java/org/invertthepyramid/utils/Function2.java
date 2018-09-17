package org.invertthepyramid.utils;

public interface Function2<From1, From2, To> {
    To apply(From1 from1, From2 from2);
}
