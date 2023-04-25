package com.rabbitminers.druidry.forge.events;

import com.rabbitminers.druidry.events.CommonEvents;
import com.rabbitminers.druidry.forge.events.listeners.SoupIngredientReloadListenerForge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
@EventBusSubscriber
public class CommonEventsForge {
    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(SoupIngredientReloadListenerForge.INSTANCE);
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        CommonEvents.onDatapackSync(player);
    }
}
