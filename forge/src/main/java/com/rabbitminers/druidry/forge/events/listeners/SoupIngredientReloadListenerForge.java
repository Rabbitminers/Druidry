package com.rabbitminers.druidry.forge.events.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.rabbitminers.druidry.content.soup.data.SoupIngredientsManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class SoupIngredientReloadListenerForge extends SimpleJsonResourceReloadListener {
    private static Gson GSON = new Gson();
    public static final SoupIngredientReloadListenerForge INSTANCE = new SoupIngredientReloadListenerForge();

    protected SoupIngredientReloadListenerForge() {
        super(GSON, "soup_ingredients");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        SoupIngredientsManager.apply(map, resourceManager, profilerFiller);
    }
}