package com.github.neboskreb.red.and.blue.android;

import com.github.neboskreb.red.and.blue.RedAndBlueExtension;
import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.PrefabBlue;
import com.github.neboskreb.red.and.blue.annotation.PrefabRed;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RedAndBlueExtension.class)
class AndroidSmokeTest {

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
}
