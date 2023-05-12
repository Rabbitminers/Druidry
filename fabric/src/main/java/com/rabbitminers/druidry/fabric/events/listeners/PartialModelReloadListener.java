package com.rabbitminers.druidry.fabric.events.listeners;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.render.partial.PartialModelManagerImpl;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.List;

public class PartialModelReloadListener implements ResourceManagerReloadListener, IdentifiableResourceReloadListener {
    public static final ResourceLocation ID = Druidry.asResource("partial_models");
    public static final List<ResourceLocation> DEPENDENCIES = List.of(ResourceReloadListenerKeys.MODELS);

    public static final PartialModelReloadListener INSTANCE = new PartialModelReloadListener();

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        PartialModelManagerImpl.onModelBake(Minecraft.getInstance().getModelManager());
    }
}
