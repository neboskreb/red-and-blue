package com.github.neboskreb.red.and.blue;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

import static nl.jqno.equalsverifier.internal.util.CachedHashCodeInitializer.passthrough;

import nl.jqno.equalsverifier.internal.instantiation.UserPrefabValueCaches;
import org.objenesis.ObjenesisStd;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.internal.instantiation.SubjectCreator;
import nl.jqno.equalsverifier.internal.reflection.FieldCache;
import nl.jqno.equalsverifier.internal.util.Configuration;
import nl.jqno.equalsverifier.internal.util.Context;
import nl.jqno.equalsverifier.internal.util.FieldNameExtractor;

class ObjectFactory {
    private static final EnumSet<Warning> NO_SUPPRESSED_WARNINGS = EnumSet.noneOf(Warning.class);

    private final UserPrefabValueCaches prefabCache = new UserPrefabValueCaches();
    private final Map<Class<?>, SubjectCreator<?>> creators;

    public ObjectFactory(List<RedAndBlue<?>> prefabs) {
        addPrefabs(prefabs);
        creators = new HashMap<>();
    }

    private <T> void addPrefabs (List<RedAndBlue<?>> prefabs) {
        for (RedAndBlue<?> pr : prefabs) {
            @SuppressWarnings("unchecked")
            RedAndBlue<T> prefab = (RedAndBlue<T>) pr;
            // TODO Seems OK to pass the red for its copy, or do we actually need a copy for some corner scenarios?
            T redCopy = prefab.red();
            prefabCache.register(prefab.clazz(), prefab.red(), prefab.blue(), redCopy);
        }
    }

    public <T> T  create(Class<T> type, COLOR color) {
        SubjectCreator<T> creator = getSubjectCreator(type);
        switch (color) {
            case RED:  return creator.plain();
            case BLUE: return creator.withAllFieldsChanged();
            default: throw new UnsupportedOperationException("Not (yet) implemented for: " + color);
        }
    }

    private <T> SubjectCreator<T> getSubjectCreator(Class<T> type) {
        @SuppressWarnings("unchecked")
        SubjectCreator<T> creator = (SubjectCreator<T>) creators.get(type);
        if (creator == null) {
            creator = buildSubjectCreator(type);
            creators.put(type, creator);
        }
        return creator;
    }

    private <T> SubjectCreator<T> buildSubjectCreator(Class<T> type) {
        Set<String> actualFields = FieldNameExtractor.extractFieldNames(type);
        Configuration<T> config = buildConfig(type, actualFields);
        Context<T> context = new Context<>(config, prefabCache, new FieldCache(), new ObjenesisStd());
        return context.getSubjectCreator();
    }


    private static <T> Configuration<T> buildConfig(Class<T> type, Set<String> actualFields) {
        return Configuration.build(type,
                                   emptySet(),
                                   emptySet(),
                                   emptySet(),
                                   emptySet(),
                                   passthrough(),
                                   false,
                                   null,
                                   false,
                                   NO_SUPPRESSED_WARNINGS,
                                   emptySet(),
                                   null,
                                   actualFields,
                                   emptySet(),
                                   emptyList(),
                                   emptyList());
    }
}
