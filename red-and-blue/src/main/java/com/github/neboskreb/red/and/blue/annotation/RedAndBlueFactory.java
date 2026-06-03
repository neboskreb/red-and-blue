package com.github.neboskreb.red.and.blue.annotation;

import com.github.neboskreb.red.and.blue.IRedAndBlueFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject the object factory which can be used to obtain Red and Blue instances programmatically.<br>
 * <br>
 * Field injection:
 * <pre>{@code
 *     @ExtendWith(RedAndBlueExtension.class)
 *     class MyClassTest {
 *
 *         @RedAndBlueFactory
 *         private IRedAndBlueFactory factory;
 *
 *         @RedAndBlueFactory
 *         private static IRedAndBlueFactory staticFactory;
 *         ...
 *     }
 * }</pre>
 * <br>
 * Parameter injection:
 * <pre>{@code
 *     @Test
 *     void testParameterInjection(@RedAndBlueFactory IRedAndBlueFactory factory) {
 *         ...
 *     }
 * }</pre>
 *
 * @see IRedAndBlueFactory IRedAndBlueFactory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface RedAndBlueFactory {
}
