package com.rabbitminers.druidry.fabric;

import com.rabbitminers.druidry.DruidryClient;
import com.rabbitminers.druidry.fabric.events.ClientEventsFabric;
import io.github.fabricators_of_create.porting_lib.util.MinecraftClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class DruidryClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DruidryClient.init();
        ClientEventsFabric.register();
    }

    public static float getPartialTicks() {
        Minecraft mc = Minecraft.getInstance();
        return (mc.isPaused() ? MinecraftClientUtil.getRenderPartialTicksPaused(mc) : mc.getFrameTime());
    }
}
