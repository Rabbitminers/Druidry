package com.rabbitminers.druidry.fabric.events

import com.rabbitminers.druidry.events.CommonEvents
import com.rabbitminers.druidry.fabric.events.listeners.SoupIngredientReloadListenerFabric
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

object CommonEventsFabric {
    fun addReloadListeners() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(SoupIngredientReloadListenerFabric.INSTANCE)
    }

    fun onDatapackSync(player: ServerPlayer?, joined: Boolean) {
        CommonEvents.onDatapackSync(player)
    }

    fun register() {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(ServerLifecycleEvents.SyncDataPackContents { obj: ServerPlayer, player: Boolean -> obj) })
        addReloadListeners()
    }
}