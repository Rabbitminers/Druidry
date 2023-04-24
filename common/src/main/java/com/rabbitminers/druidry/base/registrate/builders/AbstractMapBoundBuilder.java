package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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

    @SuppressWarnings("unchecked")
    public <A extends AbstractMapBoundBuilder<T, E, R>> A transform(NonNullFunction<A, A> function) {
        return function.apply((A) this);
    }

    public E getName() {
        return this.entryName;
    }

    public Optional<T> get() {
        T object = this.registry.getUnchecked(this.entryName);
        return object == null ? Optional.empty() : Optional.of(object);
    }

    public T register() {
        T object = this.createEntry();
        registry.register(entryName, object);
        return object;
    }
}
