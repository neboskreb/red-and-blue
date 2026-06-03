package com.github.neboskreb.red.and.blue;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


class Resolvers implements ParameterResolver {
    private final Set<ParameterResolver> delegates = new HashSet<>();

    Resolvers(ParameterResolver... resolvers) {
        Collections.addAll(delegates, resolvers);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ParameterResolver resolver = getResolverFor(parameterContext, extensionContext);

        return (resolver != null);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ParameterResolver resolver = getResolverFor(parameterContext, extensionContext);

        return resolver.resolveParameter(parameterContext, extensionContext);
    }

    private ParameterResolver getResolverFor(ParameterContext parameterContext, ExtensionContext extensionContext) {
        for (ParameterResolver resolver : delegates) {
            if (resolver.supportsParameter(parameterContext, extensionContext)) {
                return resolver;
            }
        }

        return null;
    }
}
