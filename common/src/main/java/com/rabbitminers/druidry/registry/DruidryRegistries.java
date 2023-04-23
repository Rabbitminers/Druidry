package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import net.minecraft.world.item.Item;

public class DruidryRegistries {
    public static final MapBoundRegistry<Item, SoupIngredient> SOUP_INGREDIENT_REGISTRY =
            new MapBoundRegistry<>(Druidry.asResource("soup_ingredients"));
}
