package com.rabbitminers.druidry.fabric.events;

import com.rabbitminers.druidry.events.CommonEvents;
import com.rabbitminers.druidry.fabric.events.listeners.SoupIngredientReloadListenerFabric;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;

public class CommonEventsFabric {
    public static void addReloadListeners() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(SoupIngredientReloadListenerFabric.INSTANCE);
    }

    public static void onDatapackSync(ServerPlayer player, boolean joined) {
        CommonEvents.onDatapackSync(player);
    }

    public static void register() {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(CommonEventsFabric::onDatapackSync);
        CommonEventsFabric.addReloadListeners();
    }
}
