package com.github.neboskreb.red.and.blue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Helper for collecting the fields and methods annotated with a given annotation.
 */
public class AnnotationHelper {
    private AnnotationHelper() {/* no instance */}

    /** Filter any methods other than getters. */
    public static final boolean ONLY_GETTERS = true;

    /**
     * Find fields with the given annotation. All superclasses are included in the search.
     *
     * @param clazz the class
     * @param annotationClass the annotation
     * @return the collection of matching fields if any found, otherwise an empty collection
     */
    public static List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return FieldUtils.getFieldsListWithAnnotation(clazz, annotationClass);
    }

    /**
     * Find methods annotated with the given annotation. All superclasses are included in the search.
     *
     * @param clazz the class
     * @param onlyGetters return only getters
     * @param annotationClass the annotation
     * @return the collection of matching methods if any found, otherwise an empty collection
     */
    public static Collection<Method> getAnnotatedMethods(Class<?> clazz, boolean onlyGetters, Class<? extends Annotation> annotationClass) {
        List<Method> methods = MethodUtils.getMethodsListWithAnnotation(clazz, annotationClass, true, true);
        if (onlyGetters) {
            methods = methods.stream()
                .filter(AnnotationHelper::isGetter)
                .collect(toList());
        }

        return methods;
    }

    private static boolean isGetter(Method method) {
        if (method.getParameterCount() > 0) {
            return false;
        }

        if (method.getReturnType().equals(Void.class)) {
            return false;
        }

        return true;
    }
}
