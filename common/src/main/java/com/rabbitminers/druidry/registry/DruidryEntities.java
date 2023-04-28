package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.content.grove.golems.copper.CopperGolemEntity;
import com.rabbitminers.druidry.content.grove.golems.copper.CopperGolemRenderer;
import com.rabbitminers.druidry.multiloader.EntityPropertiesWrapper;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DruidryEntities {
    private static final DruidicRegistrate REGISTRATE = Druidry.registrate();

    public static final EntityEntry<CopperGolemEntity> COPPER_GOLEM =
            REGISTRATE.entity("copper_golem", CopperGolemEntity::new, MobCategory.AMBIENT)
                    .renderer(() -> CopperGolemRenderer::new)
                    .lang("Copper Golem")
                    .transform(t -> EntityPropertiesWrapper.create(t).sized(0.6f, 1.5f).build())
                    .attributes(() -> Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2F)
                            .add(Attributes.MAX_HEALTH, 20.0D))
                    .register();

    public static void register() {
        Druidry.LOGGER.info("Registering entites for " + Druidry.MOD_NAME);
    }
}
