package com.rabbitminers.druidry.fabric;

import com.rabbitminers.druidry.DruidryClient;
import com.rabbitminers.druidry.fabric.events.ClientEventsFabric;
import com.rabbitminers.druidry.render.partial.PartialModelManagerImpl;
import io.github.fabricators_of_create.porting_lib.util.MinecraftClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.Minecraft;

public class DruidryClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DruidryClient.init();
        ClientEventsFabric.register();

        ModelLoadingRegistry.INSTANCE.registerModelProvider(PartialModelManagerImpl::onModelRegistry);
    }

    public static float getPartialTicks() {
        Minecraft mc = Minecraft.getInstance();
        return (mc.isPaused() ? MinecraftClientUtil.getRenderPartialTicksPaused(mc) : mc.getFrameTime());
    }
}
