package com.github.neboskreb.red.and.blue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Provide prefabricated Red instance.
 * <p>Once you prefab a Red instance, you must prefab also the Blue one.
 * <br>
 * <p>Provide the instance directly:</p>
 * <pre>{@code
 *     @ExtendWith(RedAndBlueExtension.class)
 *     class MyTrickyClassTest {
 *
 *         // :
 *         @PrefabRed
 *         private TrickyClass trickyRed = new TrickyClass(...);
 *         ...
 *     }
 * }</pre>
 * <br>
 * <p>Construct the instance via a factory method:</p>
 * <pre>{@code
 *     @ExtendWith(RedAndBlueExtension.class)
 *     class MyTrickyClassTest {
 *
 *         @PrefabRed
 *         private static TrickyClass createTrickyClassRed() {
 *             return new TrickyClass(...);
 *         }
 *         ...
 *     }
 * }</pre>
 * <p>NOTE: the order of invocation of the factory methods is NOT guaranteed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface PrefabRed {
}
