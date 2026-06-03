package com.github.neboskreb.red.and.blue;

import com.github.neboskreb.red.and.blue.annotation.RedAndBlueFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;


class FactoryParamResolver implements ParameterResolver {
    private final ObjectFactory factory;

    FactoryParamResolver(ObjectFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();

        for (Annotation annotation : parameter.getAnnotations()) {
            if (RedAndBlueFactory.class.equals(annotation.annotationType())) {
                Class<?> targetType = parameter.getType();
                if (targetType.isAssignableFrom(IRedAndBlueFactory.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return factory;
    }
}
