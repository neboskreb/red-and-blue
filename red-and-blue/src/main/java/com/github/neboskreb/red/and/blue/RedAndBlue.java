package com.github.neboskreb.red.and.blue;

final public class RedAndBlue<T> {
    private final Class<T> clazz;
    private final T red;
    private final T blue;

    public RedAndBlue(Class<T> clazz, T red, T blue) {
        this.clazz = clazz;
        this.red = red;
        this.blue = blue;
    }

    public Class<T> clazz() {
        return clazz;
    }

    public T red() {
        return red;
    }

    public T blue() {
        return blue;
    }
}
