package com.rabbitminers.druidry.multiloader.fabric;

import com.rabbitminers.druidry.base.registrate.builders.DruidryEntityBuilder;
import com.rabbitminers.druidry.multiloader.EntityBuilderWrapper;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityBuilderWrapperImpl<T extends Entity> extends EntityBuilderWrapper<T> {
    private final FabricEntityTypeBuilder<T> builder;

    protected EntityBuilderWrapperImpl(FabricEntityTypeBuilder<T> builder) {
        this.builder = builder;
    }

    public static <T extends Entity> EntityBuilderWrapper<T> create(MobCategory classification, EntityType.EntityFactory<T> factory) {
        return new EntityBuilderWrapperImpl<>(FabricEntityTypeBuilder.create(classification, factory));
    }

    @Override
    public EntityBuilderWrapper<T> sized(float x, float y) {
        builder.dimensions(EntityDimensions.fixed(x, y));
        return this;
    }

    @Override
    public EntityType<T> build(DruidryEntityBuilder<T, ?> builder) {
        return this.builder.build();
    }
}
