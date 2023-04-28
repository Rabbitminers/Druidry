package com.rabbitminers.druidry.multiloader.forge;

import com.rabbitminers.druidry.base.registrate.builders.DruidryEntityBuilder;
import com.rabbitminers.druidry.multiloader.EntityBuilderWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityBuilderWrapperImpl<T extends Entity> extends EntityBuilderWrapper<T> {
    private final EntityType.Builder<T> builder;

    public EntityBuilderWrapperImpl(EntityType.Builder<T> builder) {
        this.builder = builder;
    }

    public static <T extends Entity> EntityBuilderWrapper<T> create(MobCategory classification, EntityType.EntityFactory<T> factory) {
        return new EntityBuilderWrapperImpl<>(EntityType.Builder.of(factory, classification));
    }

    @Override
    public EntityBuilderWrapper<T> sized(float x, float y) {
        builder.sized(x, y);
        return this;
    }

    @Override
    public EntityType<T> build(DruidryEntityBuilder<T, ?> builder) {
        return this.builder.build(builder.getName());
    }
}
