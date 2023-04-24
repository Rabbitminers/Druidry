package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import net.minecraft.resources.ResourceLocation;

public class DruidryRegistries {
    public static final MapBoundRegistry<ResourceLocation, SoupIngredient> SOUP_INGREDIENT_REGISTRY =
            new MapBoundRegistry<>(Druidry.asResource("soup_ingredients"));
}
