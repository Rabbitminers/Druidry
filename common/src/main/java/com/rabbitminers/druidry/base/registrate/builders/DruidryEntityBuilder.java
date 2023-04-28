package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.multiloader.EntityBuilderWrapper;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class to help handle entity properties across platforms
 *
 * @param <T>
 *     The type of entity being built
 * @param <P>
 *     Parent object type
 */

public class DruidryEntityBuilder<T extends Entity, P> extends EntityBuilder<T, P> {
    private final NonNullSupplier<EntityBuilderWrapper<T>> builder;
    private NonNullConsumer<EntityBuilderWrapper<T>> builderCallback = $ -> {};

    protected DruidryEntityBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
                                   EntityType.EntityFactory<T> factory, MobCategory classification) {
        super(owner, parent, name, callback, factory, classification);
        this.builder = () -> EntityBuilderWrapper.create(classification, factory);
    }

    public static <T extends Entity, P> EntityBuilder<T, P> create(AbstractRegistrate<?> owner, P parent, String name,
                                                                   BuilderCallback callback, EntityType.EntityFactory<T> factory, MobCategory classification) {
        return (new DruidryEntityBuilder<>(owner, parent, name, callback, factory, classification)).defaultLang();
    }

    public DruidryEntityBuilder<T, P> entityProperties(NonNullConsumer<EntityBuilderWrapper<T>> builderWrapper) {
        this.builderCallback = this.builderCallback.andThen(builderWrapper);
        return this;
    }

    @Override
    protected @NotNull EntityType<T> createEntry() {
        EntityBuilderWrapper<T> builder = this.builder.get();
        this.builderCallback.accept(builder);
        return builder.build(this);
    }
}
