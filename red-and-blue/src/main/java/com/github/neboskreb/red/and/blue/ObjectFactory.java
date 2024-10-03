package com.github.neboskreb.red.and.blue;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import nl.jqno.equalsverifier.internal.reflection.FactoryCache;
import nl.jqno.equalsverifier.internal.reflection.JavaApiPrefabValues;
import nl.jqno.equalsverifier.internal.reflection.TypeTag;
import nl.jqno.equalsverifier.internal.reflection.instantiation.VintageValueProvider;
import nl.jqno.equalsverifier.internal.reflection.vintage.ClassAccessor;
import nl.jqno.equalsverifier.internal.util.PrefabValuesApi;

import java.util.LinkedHashSet;
import java.util.List;

class ObjectFactory {
    private static final FactoryCache BASIC_JAVA_PREFABS = JavaApiPrefabValues.build();

    private final Objenesis objenesis;
    private final VintageValueProvider valueProvider;

    public ObjectFactory(List<RedAndBlue<?>> prefabs) {
        objenesis = new ObjenesisStd();
        FactoryCache factoryCache = new FactoryCache();
        addPrefabs(prefabs, factoryCache);
        FactoryCache cache = BASIC_JAVA_PREFABS.merge(factoryCache);
        valueProvider = new VintageValueProvider(cache, objenesis);
    }

    private <T> void addPrefabs (List<RedAndBlue<?>> prefabs, FactoryCache factoryCache) {
        for (RedAndBlue<?> pr : prefabs) {
            @SuppressWarnings("unchecked")
            RedAndBlue<T> prefab = (RedAndBlue<T>) pr;
            PrefabValuesApi.addPrefabValues(factoryCache, objenesis, prefab.clazz(), prefab.red(), prefab.blue());
        }
    }

    public <T> T  create(Class<T> type, COLOR color) {
        ClassAccessor<T> classAccessor = ClassAccessor.of(type, valueProvider, objenesis);
        TypeTag typeTag = new TypeTag(type);
        switch (color) {
            case RED:  return classAccessor.getRedObject(typeTag, new LinkedHashSet<>());
            case BLUE: return classAccessor.getBlueObject(typeTag, new LinkedHashSet<>());
            default: throw new UnsupportedOperationException("Not (yet) implemented for: " + color);
        }
    }
}
