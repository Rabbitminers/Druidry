package com.rabbitminers.druidry.render.forge;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.rabbitminers.druidry.registry.DruidryTextures;
import com.rabbitminers.druidry.render.CustomRenderTypes;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;


public class CustomRenderTypesImpl extends CustomRenderTypes {
    private static final RenderType OUTLINE_SOLID =
            RenderType.create(createLayerName("outline_solid"), DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false,
                    false, RenderType.CompositeState.builder()
                            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_SOLID_SHADER)
                            .setTextureState(new RenderStateShard.TextureStateShard(DruidryTextures.BLANK.getLocation(), false, false))
                            .setCullState(CULL)
                            .setLightmapState(LIGHTMAP)
                            .setOverlayState(OVERLAY)
                            .createCompositeState(false));

    public static RenderType getOutlineSolid() {
        return OUTLINE_SOLID;
    }
    public static RenderType getOutlineTranslucent(ResourceLocation texture, boolean cull) {
        return RenderType.create(createLayerName("outline_translucent" + (cull ? "_cull" : "")),
                DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
                        .setShaderState(cull ? RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER : RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(cull ? CULL : NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .setWriteMaskState(COLOR_WRITE)
                        .createCompositeState(false));
    }
}
