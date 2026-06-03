package com.github.neboskreb.red.and.blue;

/**
 * Factory which can be used to create Red and Blue instances programmatically.
 */
public interface IRedAndBlueFactory {
    /** Create Red instance of the given type. */
    <T> T createRed(Class<T> type);
    /** Create Blue instance of the given type. */
    <T> T createBlue(Class<T> type);
}
