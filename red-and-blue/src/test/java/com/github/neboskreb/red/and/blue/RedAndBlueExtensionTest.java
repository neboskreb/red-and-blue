package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.PrefabBlue;
import com.github.neboskreb.red.and.blue.annotation.PrefabRed;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import com.github.neboskreb.red.and.blue.model.CompositeInteger;
import com.github.neboskreb.red.and.blue.model.CompositeLong;
import com.github.neboskreb.red.and.blue.model.CompositeString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(RedAndBlueExtension.class)
class RedAndBlueExtensionTest extends ExampleBase {

    @RedInstance
    private CompositeString redString;
    @RedInstance
    protected CompositeInteger redInt;
    @RedInstance
    protected CompositeLong redLong;

    @PrefabRed
    protected String prefabRedString = "red";
    @PrefabRed
    protected static Integer prefabRedInt = 17;

    @PrefabRed
    private Long prefabRedLong() {
        return 1L;
    }

    @Test
    void testParameterInjection(@RedInstance CompositeString red, @BlueInstance CompositeString blue) {
        assertEquals("red", red.value());
        assertEquals("blue", blue.value());
    }

    @Test
    void testFieldInjection() {
        assertEquals("red", redString.value());
        assertEquals("blue", blueString.value());
        assertEquals(17, redInt.value());
        assertEquals(-42, blueInt.value());
        assertEquals(1L, redLong.value());
        assertEquals(-1L, blueLong.value());
    }
}

abstract class ExampleBase {
    @BlueInstance
    protected CompositeString blueString;
    @BlueInstance
    protected CompositeInteger blueInt;
    @BlueInstance
    protected CompositeLong blueLong;

    @PrefabBlue
    protected static Integer staticPrefabBlueInt = -42;
    @PrefabBlue
    protected Long prefabBlueLong = -1L;

    @PrefabBlue
    private static String baseStaticPrefabBlueString() {
        return "blue";
    }

    @Test
    void testBaseParameterInjection(@BlueInstance CompositeLong blue) {
        assertEquals(-1L, blue.value());
    }
}
