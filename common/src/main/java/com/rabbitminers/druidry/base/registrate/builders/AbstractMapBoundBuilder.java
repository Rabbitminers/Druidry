package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for map bound registries
 *
 * @param <R>
 *     Type of registry for the current object.
 * @param <T>
 *     Type of object
 * @param <E>
 *     Registry key
 */
public abstract class AbstractMapBoundBuilder<T, E, R extends MapBoundRegistry<E, T>> {
    protected final R registry;
    protected final E entryName;

    protected AbstractMapBoundBuilder(@NotNull R registry, E name) {
        this.registry = registry;
        this.entryName = name;
    }

    protected abstract @NotNull T createEntry();

    public T register() {
        T object = this.createEntry();
        registry.register(entryName, object);
        return object;
    }
}
