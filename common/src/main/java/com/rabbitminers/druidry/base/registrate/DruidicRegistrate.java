package com.rabbitminers.druidry.base.registrate;

import com.rabbitminers.druidry.base.registrate.builders.SoupIngredientBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public abstract class DruidicRegistrate extends AbstractRegistrate<DruidicRegistrate> {
    protected DruidicRegistrate(String modid) {
        super(modid);
    }

    public SoupIngredientBuilder soupIngredient(Item item) {
        ResourceLocation location = Registry.ITEM.getKey(item);
        return soupIngredient(location);
    }

    public SoupIngredientBuilder soupIngredient(ResourceLocation item) {
        return SoupIngredientBuilder.create(item);
    }
}
