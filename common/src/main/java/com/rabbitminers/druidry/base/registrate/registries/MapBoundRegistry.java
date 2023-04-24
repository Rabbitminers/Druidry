package com.rabbitminers.druidry.base.registrate.registries;

import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapBoundRegistry<T, P> implements Keyable {
    private final Map<T, P> registry = new HashMap<>();
    private final ResourceLocation registryLocation;

    public MapBoundRegistry(@NotNull ResourceLocation location) {
        this.registryLocation = location;
    }

    public void register(T key, P object) {
        if (registry.containsKey(key)) {
            throw new IllegalStateException("Registry entry " + key + " is already present");
        }
        registry.put(key, object);
    }

    @Nullable
    public P getUnchecked(T key) {
        return this.registry.get(key);
    }

    public P getRegistryEntryOrThrow(T key) {
        P object = this.getUnchecked(key);
        if (object == null) {
            throw new IllegalStateException(
                "Registry entry " + key + " not present in " + this.registryLocation
            );
        } else {
            return object;
        }
    }

    public void clear() {
        registry.clear();
    }

    public Collection<P> registryEntries() {
        return registry.values();
    }

    public Collection<T> keys() {
        return registry.keySet();
    }

    @Override
    public <U> Stream<U> keys(DynamicOps<U> ops) {
        return this.keys().stream().map(key -> ops.createString(key.toString()));
    }
}
