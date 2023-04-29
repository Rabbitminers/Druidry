package com.rabbitminers.druidry.render;

import com.rabbitminers.druidry.Druidry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class CustomRenderTypes extends RenderStateShard {
    @ExpectPlatform
    public static RenderType getOutlineSolid() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static RenderType getOutlineTranslucent(ResourceLocation texture, boolean cull) {
        throw new AssertionError();
    }

    protected static String createLayerName(String name) {
        return Druidry.MOD_ID + ":" + name;
    }

    protected CustomRenderTypes() {
        super(null, null, null);
    }
}
