package com.rabbitminers.druidry.base.registrate;

import com.rabbitminers.druidry.base.registrate.builders.DruidryEntityBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public abstract class DruidicRegistrate extends AbstractRegistrate<DruidicRegistrate> {
    protected DruidicRegistrate(String modid) {
        super(modid);
    }

    @Override
    public <T extends Entity> @NotNull DruidryEntityBuilder<T, DruidicRegistrate> entity(String name,
            EntityType.EntityFactory<T> factory, MobCategory classification) {
        return this.entity(self(), name, factory, classification);
    }

    @Override
    public <T extends Entity, P> @NotNull DruidryEntityBuilder<T,  P> entity(P parent, String name,
            EntityType.EntityFactory<T> factory, MobCategory classification) {
        return (DruidryEntityBuilder<T, P>) this.entry(name, (callback) ->
                DruidryEntityBuilder.create(this, parent, name, callback, factory, classification));
    }
}
