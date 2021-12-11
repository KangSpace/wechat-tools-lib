package org.kangspace.wechat.util.jdk18;

/**
 * Represents a function that accepts one argument and produces a result.
 * from jdk1.8
 */
public interface Function<T,R>{
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}
