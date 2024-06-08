package com.github.neboskreb.red.and.blue;

import nl.jqno.equalsverifier.internal.prefabvalues.FactoryCache;
import nl.jqno.equalsverifier.internal.prefabvalues.JavaApiPrefabValues;
import nl.jqno.equalsverifier.internal.prefabvalues.PrefabValues;
import nl.jqno.equalsverifier.internal.prefabvalues.TypeTag;
import nl.jqno.equalsverifier.internal.reflection.ClassAccessor;
import nl.jqno.equalsverifier.internal.util.PrefabValuesApi;

import java.util.List;

class ObjectFactory {
    private static final FactoryCache BASIC_JAVA_PREFABS = JavaApiPrefabValues.build();

    private final PrefabValues prefabValues;

    public ObjectFactory(List<RedAndBlue<?>> prefabs) {
        FactoryCache factoryCache = new FactoryCache();
        addPrefabs(prefabs, factoryCache);
        FactoryCache cache = BASIC_JAVA_PREFABS.merge(factoryCache);
        prefabValues = new PrefabValues(cache);
    }

    private <T> void addPrefabs (List<RedAndBlue<?>> prefabs, FactoryCache factoryCache) {
        for (RedAndBlue<?> pr : prefabs) {
            @SuppressWarnings("unchecked")
            RedAndBlue<T> prefab = (RedAndBlue<T>) pr;
            PrefabValuesApi.addPrefabValues(factoryCache, prefab.clazz(), prefab.red(), prefab.blue());
        }
    }

    public <T> T  create(Class<T> type, COLOR color) {
        ClassAccessor<T> classAccessor = ClassAccessor.of(type, prefabValues);
        TypeTag typeTag = new TypeTag(type);
        switch (color) {
            case RED:  return classAccessor.getRedObject(typeTag);
            case BLUE: return classAccessor.getBlueObject(typeTag);
            default: throw new UnsupportedOperationException("Not (yet) implemented for: " + color);
        }
    }
}