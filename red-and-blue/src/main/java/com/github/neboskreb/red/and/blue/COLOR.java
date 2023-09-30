package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;

enum COLOR {
    RED(RedInstance.class), BLUE(BlueInstance.class);

    private final Class<?> annotation;

    COLOR(Class<?> annotation) {
        this.annotation = annotation;
    }

    public static COLOR getColor(Class<?> annotation) {
        for (COLOR color : values()) {
            if (color.annotation == annotation) return color;
        }
        throw new IllegalArgumentException("Annotation does not belong to Red or Blue: " + annotation);
    }
}
