package com.github.neboskreb.red.and.blue;

import org.junit.jupiter.api.extension.*;

import java.util.List;

public class RedAndBlueFactoryExtension implements TestInstancePostProcessor,
                                                   BeforeAllCallback,
                                                   ParameterResolver
{
    private PrefabCollector prefabCollector;
    private FieldInjector fieldInjector;
    private ParamResolver paramResolver;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> clazz = context.getTestClass().orElseThrow(() -> new Exception("Missing test class"));
        prefabCollector = new PrefabCollector(clazz);
        fieldInjector = new FieldInjector(clazz);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        List<RedAndBlue<?>> prefabs = prefabCollector.collect(testInstance);
        ObjectFactory factory = new ObjectFactory(prefabs);

        fieldInjector.inject(testInstance, factory);

        paramResolver = new ParamResolver(factory);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return paramResolver.supportsParameter(parameterContext, extensionContext);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return paramResolver.resolveParameter(parameterContext, extensionContext);
    }
}
