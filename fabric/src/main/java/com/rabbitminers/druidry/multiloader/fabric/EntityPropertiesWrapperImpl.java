package com.rabbitminers.druidry.multiloader.fabric;

import com.rabbitminers.druidry.multiloader.EntityPropertiesWrapper;
import com.tterrag.registrate.builders.EntityBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

public class EntityPropertiesWrapperImpl<T extends Entity, P> extends EntityPropertiesWrapper<T, P> {
    private final EntityBuilder<T, P> builder;

    protected EntityPropertiesWrapperImpl(EntityBuilder<T, P> builder) {
        this.builder = builder;
    }

    public static <T extends Entity, P> EntityPropertiesWrapper<T, P> create(EntityBuilder<T, P> builder) {
        return new EntityPropertiesWrapperImpl<>(builder);
    }

    @Override
    public EntityPropertiesWrapper<T, P> sized(float x, float y) {
        this.builder.properties(b -> b.dimensions(EntityDimensions.fixed(x, y)));
        return this;
    }

    @Override
    public EntityBuilder<T, P> build() {
        return this.builder;
    }
}
