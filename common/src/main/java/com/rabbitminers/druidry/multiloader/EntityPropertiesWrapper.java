package com.rabbitminers.druidry.multiloader;

import com.tterrag.registrate.builders.EntityBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;

public abstract class EntityPropertiesWrapper<T extends Entity, P> {
    @ExpectPlatform
    public static <T extends Entity, P> EntityPropertiesWrapper<T, P> create(EntityBuilder<T, P> builder) {
        throw new AssertionError();
    }

    public abstract EntityPropertiesWrapper<T, P> sized(float x, float y);

    public abstract EntityBuilder<T, P> build();
}
