package com.rabbitminers.druidry.registry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rabbitminers.druidry.Druidry;
import net.minecraft.resources.ResourceLocation;

public enum DruidryTextures {
    BLANK("blank.png")
    ;

    public static final String ASSET_PATH = "textures/misc/";
    private final ResourceLocation location;

    private DruidryTextures(String filename) {
        location = Druidry.asResource(ASSET_PATH + filename);
    }

    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    public ResourceLocation getLocation() {
        return location;
    }
}
