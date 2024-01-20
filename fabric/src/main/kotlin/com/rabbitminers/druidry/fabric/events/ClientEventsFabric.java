package com.rabbitminers.druidry.fabric.events;

import com.rabbitminers.druidry.events.ClientEvents;
import com.rabbitminers.druidry.fabric.events.listeners.SoupIngredientReloadListenerFabric;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;

public class ClientEventsFabric {
    public static void onTick(Minecraft client) {
        ClientEvents.onTick();
    }

    public static void onRenderWorld(WorldRenderContext event) {
        ClientEvents.onRenderWorld(event.matrixStack());
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventsFabric::onTick);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ClientEventsFabric::onRenderWorld);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
                .registerReloadListener(SoupIngredientReloadListenerFabric.INSTANCE);
    }
}
