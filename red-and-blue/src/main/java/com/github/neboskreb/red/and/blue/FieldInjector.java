package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedAndBlueFactory;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;

import java.lang.reflect.Field;
import java.util.List;

import static com.github.neboskreb.red.and.blue.AnnotationHelper.getAnnotatedFields;

class FieldInjector {
    private final List<Field> reds;
    private final List<Field> blues;
    private final List<Field> factories;

    public FieldInjector(Class<?> clazz) {
        reds = getAnnotatedFields(clazz, RedInstance.class);
        blues = getAnnotatedFields(clazz, BlueInstance.class);
        factories = getAnnotatedFields(clazz, RedAndBlueFactory.class);
    }

    public void inject(Object testInstance, ObjectFactory factory) {
        injectAll(testInstance, reds, factory::createRed);
        injectAll(testInstance, blues, factory::createBlue);

        IFactory<Object> injectFactory = clazz -> {
            if (clazz.isAssignableFrom(IRedAndBlueFactory.class)) {
                return factory;
            } else {
                throw new IllegalArgumentException("Type of field must be IObjectFactory");
            }
        };
        injectAll(testInstance, factories, injectFactory);
    }

    interface IFactory<T> {
        T create(Class<T> type);
    }

    private <T> void injectAll(Object testInstance, List<Field> fields, IFactory<T> fac) {
        for (Field field : fields) {
            try {
                @SuppressWarnings("unchecked")
                Class<T> clazz = (Class<T>) field.getType();
                Object value = fac.create(clazz);
                injectField(testInstance, field, value);
            } catch (Exception e) {
                throw new RuntimeException("Can not inject field " + field, e);
            }
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