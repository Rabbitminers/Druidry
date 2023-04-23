package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import net.minecraft.world.item.Items;

public class DruidrySoupIngredients {
    public static DruidicRegistrate REGISTRATE = Druidry.registrate();

    public static final SoupIngredient HONEY = REGISTRATE.soupIngredient(Items.HONEY_BOTTLE)
            .saltiness(0.1).sweetness(0.9).spice(0)
            .register();

    public static final SoupIngredient APPLE = REGISTRATE.soupIngredient(Items.APPLE)
            .synergisticIngredient(HONEY)
            .saltiness(0.2).sweetness(0.4).spice(0.05)
            .register();

    public static final SoupIngredient FIRE_CHARGE = REGISTRATE.soupIngredient(Items.FIRE_CHARGE)
            .conflictingIngredient(APPLE, HONEY)
            .saltiness(0).sweetness(0).spice(1)
            .register();

    public static void init() {

    }
}
