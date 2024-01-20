package com.rabbitminers.druidry.fabric.events.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredientsManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class SoupIngredientReloadListenerFabric extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
    private static Gson GSON = new Gson();
    public static final SoupIngredientReloadListenerFabric INSTANCE = new SoupIngredientReloadListenerFabric();

    protected SoupIngredientReloadListenerFabric() {
        super(GSON, "soup_ingredients");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        SoupIngredientsManager.apply(map, resourceManager, profilerFiller);
    }

    @Override
    public ResourceLocation getFabricId() {
        return Druidry.asResource("soup_ingredients");
    }
}