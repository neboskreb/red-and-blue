package com.github.neboskreb.red.and.blue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnnotationHelper {
    private AnnotationHelper() {/* no instance */}

    public static final boolean ONLY_GETTERS = true;

    public static List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return FieldUtils.getFieldsListWithAnnotation(clazz, annotationClass);
    }

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
