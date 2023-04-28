package com.rabbitminers.druidry.multiloader.forge;

import com.rabbitminers.druidry.multiloader.EntityPropertiesWrapper;
import com.tterrag.registrate.builders.EntityBuilder;
import net.minecraft.world.entity.Entity;

public class EntityPropertiesWrapperImpl<T extends Entity, P> extends EntityPropertiesWrapper<T, P> {
    private final EntityBuilder<T, P> builder;

    protected EntityPropertiesWrapperImpl(EntityBuilder<T, P> builder) {
        this.builder = builder;
    }
    public static <T extends Entity, P> EntityPropertiesWrapper<T, P> create(EntityBuilder<T, P> builder) {
        return new EntityPropertiesWrapperImpl<>(builder);
    }

    @Override
    public com.rabbitminers.druidry.multiloader.EntityPropertiesWrapper<T, P> sized(float x, float y) {
        this.builder.properties(b -> b.sized(x, y));
        return this;
    }

    @Override
    public EntityBuilder<T, P> build() {
        return this.builder;
    }
}
