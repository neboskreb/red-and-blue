package com.github.neboskreb.red.and.blue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject the Red instance into your test method as parameter or into test class as field:<br>
 * <br>
 * Field injection:
 * <pre>{@code
 *     @ExtendWith(RedAndBlueExtension.class)
 *     class MyClassTest {
 *
 *         @RedInstance
 *         private MyClass red;
 *         ...
 *     }
 * }</pre>
 * <br>
 * Parameter injection:
 * <pre>{@code
 *     @Test
 *     void testParameterInjection(@RedInstance MyClass red, @BlueInstance MyClass blue) {
 *         ...
 *     }
 * }</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface RedInstance {
}
