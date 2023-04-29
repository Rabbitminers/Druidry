package com.rabbitminers.druidry.forge;

import com.rabbitminers.druidry.DruidryClient;
import net.minecraft.client.Minecraft;

public class DruidryClientImpl {
    public static void init() {
        DruidryClient.init();
    }

    public static float getPartialTicks() {
        Minecraft mc = Minecraft.getInstance();
        return (mc.isPaused() ? mc.pausePartialTick : mc.getFrameTime());
    }
}
