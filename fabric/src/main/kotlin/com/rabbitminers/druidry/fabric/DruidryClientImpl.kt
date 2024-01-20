package com.rabbitminers.druidry.fabric

import com.rabbitminers.druidry.DruidryClient
import com.rabbitminers.druidry.fabric.events.ClientEventsFabric
import com.rabbitminers.druidry.render.partial.PartialModelManagerImpl
import io.github.fabricators_of_create.porting_lib.util.MinecraftClientUtil
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import java.util.function.Consumer

object DruidryClientImpl: ClientModInitializer {
    override fun onInitializeClient() {
        DruidryClient.init()
        ClientEventsFabric.register()

        ModelLoadingRegistry.INSTANCE.registerModelProvider { manager: ResourceManager?, out: Consumer<ResourceLocation?>? -> PartialModelManagerImpl.onModelRegistry(manager, out) }
    }

    fun getPartialTicks(): Float {
        val mc = Minecraft.getInstance()
        return (if (mc.isPaused) MinecraftClientUtil.getRenderPartialTicksPaused(mc) else mc.frameTime)
    }
}