package com.rabbitminers.druidry.base.registrate;

import com.rabbitminers.druidry.base.registrate.builders.SoupIngredientBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import net.minecraft.world.item.Item;

public abstract class DruidicRegistrate extends AbstractRegistrate<DruidicRegistrate> {
    protected DruidicRegistrate(String modid) {
        super(modid);
    }

    public SoupIngredientBuilder soupIngredient(Item item) {
        return SoupIngredientBuilder.create(item);
    }
}
