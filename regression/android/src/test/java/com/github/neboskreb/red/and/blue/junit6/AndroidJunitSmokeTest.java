package com.github.neboskreb.red.and.blue.junit6;

import com.github.neboskreb.red.and.blue.IRedAndBlueFactory;
import com.github.neboskreb.red.and.blue.RedAndBlueExtension;
import com.github.neboskreb.red.and.blue.annotation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(RedAndBlueExtension.class)
class AndroidJunitSmokeTest {

    @BlueInstance
    protected MyObject blueField;
    @RedInstance
    private MyObject redField;

    @PrefabRed
    protected String prefabRedString = "red";

    @PrefabBlue
    private static String baseStaticPrefabBlueString() {
        return "blue";
    }

    @Test
    void testParameterInjection(@RedInstance MyObject red, @BlueInstance MyObject blue) {
        assertEquals("red", red.value());
        assertEquals("blue", blue.value());
    }

    @Test
    void testFieldInjection() {
        assertEquals("red", redField.value());
        assertEquals("blue", blueField.value());
    }

    @RedAndBlueFactory
    private IRedAndBlueFactory factory;

    @Test
    void testFactoryParameter(@RedAndBlueFactory IRedAndBlueFactory factory) {
        MyObject red = factory.createRed(MyObject.class);
        MyObject blue = factory.createBlue(MyObject.class);
        assertEquals("red", red.value());
        assertEquals("blue", blue.value());
    }

    @Test
    void testFactoryField() {
        assertNotNull(factory);
        MyObject blue = factory.createBlue(MyObject.class);
        assertEquals("blue", blue.value());
    }
}
