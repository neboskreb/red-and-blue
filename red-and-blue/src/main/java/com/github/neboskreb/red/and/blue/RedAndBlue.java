package com.github.neboskreb.red.and.blue;

/**
 * A tuple containing Red and Blue objects of their Class.
 *
 * @param <T> - the class of objects
 */
final public class RedAndBlue<T> {
    /* NOTE: the getters in this class follow Java 17 convention instead of JavaBeans (i.e. `clazz()` not `getClazz()`)
     *       for seamless conversion from `class` to `record` once the supported version of Java is elevated to 17 from the current 11.    */
    private final Class<T> clazz;
    private final T red;
    private final T blue;

    /**
     * Constructor.
     * @param clazz  the class
     * @param red red instance
     * @param blue blue instance
     */
    public RedAndBlue(Class<T> clazz, T red, T blue) {
        this.clazz = clazz;
        this.red = red;
        this.blue = blue;
    }

    /**
     * Get the class of object.
     * @return the class
     */
    public Class<T> clazz() {
        return clazz;
    }

    /**
     * Get red instance.
     * @return red instance
     */
    public T red() {
        return red;
    }

    /**
     * Get blue instance.
     * @return blue instance
     */
    public T blue() {
        return blue;
    }
}
