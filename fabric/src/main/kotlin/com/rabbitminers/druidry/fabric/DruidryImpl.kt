package com.rabbitminers.druidry.fabric

import com.rabbitminers.druidry.Druidry
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate
import com.rabbitminers.druidry.fabric.base.registrate.DruidicRegistrateFabric
import com.rabbitminers.druidry.fabric.events.CommonEventsFabric
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer

object DruidryImpl: ModInitializer {
    var REGISTRATE: DruidicRegistrateFabric = DruidicRegistrateFabric.create(Druidry.MOD_ID)
    var currentServer: MinecraftServer? = null

    override fun onInitialize() {
        Druidry.init()
        REGISTRATE.register()
        CommonEventsFabric.register()

        ServerLifecycleEvents.SERVER_STARTING.register(ServerLifecycleEvents.ServerStarting { s: MinecraftServer? -> currentServer = s })
    }

    fun registrate(): DruidicRegistrate {
        return REGISTRATE
    }
}