package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.PrefabBlue;
import com.github.neboskreb.red.and.blue.annotation.PrefabRed;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static com.github.neboskreb.red.and.blue.AnnotationHelper.getAnnotatedFields;
import static com.github.neboskreb.red.and.blue.AnnotationHelper.getAnnotatedMethods;
import static com.github.neboskreb.red.and.blue.AnnotationHelper.ONLY_GETTERS;

class PrefabCollector {
    private final Collection<Field> redPrefabFields;
    private final Collection<Field> bluePrefabFields;
    private final Collection<Method> redPrefabFactories;
    private final Collection<Method> bluePrefabFactories;

    public PrefabCollector(Class<?> clazz) {
        redPrefabFields = getAnnotatedFields(clazz, PrefabRed.class);
        bluePrefabFields = getAnnotatedFields(clazz, PrefabBlue.class);
        redPrefabFactories = getAnnotatedMethods(clazz, ONLY_GETTERS, PrefabRed.class);
        bluePrefabFactories = getAnnotatedMethods(clazz, ONLY_GETTERS, PrefabBlue.class);
    }

    public List<RedAndBlue<?>> collect(Object testInstance) {
        // 1. Collect all RED prefabs
        List<Object> reds = new ArrayList<>();
        redPrefabFactories.stream()
                .map(method -> invokeFactoryMethod(testInstance, method))
                .forEach(reds::add);

        redPrefabFields.stream()
                .map(field -> readField(testInstance, field))
                .forEach(reds::add);

        // 2. Collect all BLUE prefabs
        List<Object> blues = new ArrayList<>();
        bluePrefabFactories.stream()
                .map(method -> invokeFactoryMethod(testInstance, method))
                .forEach(blues::add);

        bluePrefabFields.stream()
                .map(field -> readField(testInstance, field))
                .forEach(blues::add);

        // 3. Group colored prefabs by type
        Map<Class<?>, Object> typedReds = group(reds, (existing, replacement) ->
                "Conflicting RED prefabs of type [" + existing.getClass().getSimpleName() + "]\n" +
                "Prefab " + replacement + " conflicts with prefab " + existing);

        Map<Class<?>, Object> typedBlues = group(blues, (existing, replacement) ->
                "Conflicting BLUE prefabs of type [" + existing.getClass().getSimpleName() + "]\n" +
                "Prefab " + replacement + " conflicts with prefab " + existing);

        // 4. Collect to color pairs by type
        Set<Class<?>> allTypes = new HashSet<>();
        allTypes.addAll(typedReds.keySet());
        allTypes.addAll(typedBlues.keySet());

        List<RedAndBlue<?>> result = new ArrayList<>();
        for (Class<?> clazz : allTypes) {
            Object red = typedReds.get(clazz);
            if (red == null) {
                throw new IllegalStateException("Missing RED prefab of type " + clazz.getCanonicalName());
            }
            Object blue = typedBlues.get(clazz);
            if (blue == null) {
                throw new IllegalStateException("Missing BLUE prefab of type " + clazz.getCanonicalName());
            }
            result.add(assertTypesMatchExactly(clazz, red, blue));
        }

        return result;
    }

    private Map<Class<?>, Object> group(List<Object> reds, BiFunction<Object, Object, String> message) {
        return reds.stream()
                .collect(toMap(Object::getClass,
                        Function.identity(),
                        (existing, replacement) -> {
                            throw new IllegalStateException(message.apply(existing, replacement));}
                        ));
    }

    private Object readField(Object testInstance, Field field) {
        try {
            field.setAccessible(true);
            return field.get(testInstance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeFactoryMethod(Object testInstance, Method method) {
        try {
            method.setAccessible(true);
            Object result = method.invoke(testInstance);
            if (result == null) {
                throw new IllegalArgumentException("Prefab method returned null:" + method);
            }
            return result;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> RedAndBlue<T> assertTypesMatchExactly(Class<?> type, Object red, Object blue) {
        Class<?> redClass = red.getClass();
        if (redClass != type) {
            throw new IllegalArgumentException("Prefab RED expected class [" + type.getCanonicalName() + "] but was [" + redClass + "]");
        }
        Class<?> blueClass = blue.getClass();
        if (blueClass != type) {
            throw new IllegalArgumentException("Prefab BLUE expected class [" + type.getCanonicalName() + "] but was [" + blueClass + "]");
        }
        @SuppressWarnings("unchecked")
        RedAndBlue<T> result = new RedAndBlue<>((Class<T>) type, (T) red, (T) blue);
        return result;
    }
}
