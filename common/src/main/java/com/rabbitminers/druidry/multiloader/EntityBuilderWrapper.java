package com.rabbitminers.druidry.multiloader;

import com.rabbitminers.druidry.base.registrate.builders.DruidryEntityBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public abstract class EntityBuilderWrapper<T extends Entity> {
    @ExpectPlatform
    public static <T extends Entity> EntityBuilderWrapper<T> create(MobCategory classification, EntityType.EntityFactory<T> factory) {
        throw new AssertionError();
    }

    public abstract EntityBuilderWrapper<T> sized(float x, float y);

    public abstract EntityType<T> build(DruidryEntityBuilder<T, ?> builder);
}
