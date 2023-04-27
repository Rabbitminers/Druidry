package com.rabbitminers.druidry.content.grove.golems.copper;

import com.rabbitminers.druidry.Druidry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CopperGolemRenderer extends MobRenderer<CopperGolemEntity, CopperGolemModel> {
    private static final ResourceLocation TEXTURE =
            Druidry.asResource("textures/entity/copper_golem.png");

    public CopperGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new CopperGolemModel(), 0.6F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CopperGolemEntity entity) {
        return switch (entity.getWeatherState()) {
            case UNAFFECTED -> Druidry.asResource("textures/entity/copper_golem.png");
            case EXPOSED -> Druidry.asResource("textures/entity/exposed_copper_golem.png");
            case WEATHERED -> Druidry.asResource("textures/entity/weathered_copper_golem.png");
            case OXIDIZED -> Druidry.asResource("textures/entity/oxidised_copper_golem.png");
        };
    }
}
