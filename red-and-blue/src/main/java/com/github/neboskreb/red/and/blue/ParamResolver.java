package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.isEqual;

class ParamResolver implements ParameterResolver {
    private static final Predicate<Object> INJECTABLE_INSTANCE = isEqual(RedInstance.class).or(isEqual(BlueInstance.class));

    private final ObjectFactory factory;

    ParamResolver(ObjectFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Annotation[] annotations = parameterContext.getParameter().getAnnotations();

        return stream(annotations)
                .map(Annotation::annotationType)
                .anyMatch(INJECTABLE_INSTANCE);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();

        Class<?> targetType = parameter.getType();

        COLOR color = stream(parameter.getAnnotations())
                .filter(a -> INJECTABLE_INSTANCE.test(a.annotationType()))
                .findAny()
                .map(Annotation::annotationType)
                .map(COLOR::getColor)
                .orElseThrow(() -> new IllegalStateException("Expected annotation @RedInstance or @BlueInstance but got nothing"));

        return factory.create(targetType, color);
    }
}
