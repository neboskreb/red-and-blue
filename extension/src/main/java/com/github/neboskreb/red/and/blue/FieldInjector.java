package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;

import java.lang.reflect.Field;
import java.util.List;

import static com.github.neboskreb.red.and.blue.COLOR.BLUE;
import static com.github.neboskreb.red.and.blue.COLOR.RED;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.helpers.AnnotationHelper.getAnnotatedFields;

class FieldInjector {
    private final List<Field> reds;
    private final List<Field> blues;

    public FieldInjector(Class<?> clazz) {
        reds = getAnnotatedFields(clazz, RedInstance.class);
        blues = getAnnotatedFields(clazz, BlueInstance.class);
    }

    public void inject(Object testInstance, ObjectFactory factory) {
        injectAll(testInstance, reds, RED, factory::create);
        injectAll(testInstance, blues, BLUE, factory::create);
    }

    interface IFactory<T> {
        T create(Class<T> type, COLOR color);
    }

    private <T> void injectAll(Object testInstance, List<Field> fields, COLOR color, IFactory<T> fac) {
        for (Field field : fields) {
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) field.getType();
            Object value = fac.create(clazz, color);
            injectField(testInstance, field, value);
        }
    }

    private void injectField(Object testInstance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(testInstance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}