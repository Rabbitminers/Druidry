package com.rabbitminers.druidry.forge;

import com.rabbitminers.druidry.DruidryClient;
import com.rabbitminers.druidry.render.forge.partial.PartialModelManagerImpl;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class DruidryClientImpl {
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        DruidryClient.init();

        modEventBus.addListener(PartialModelManagerImpl::onModelBake);
        modEventBus.addListener(PartialModelManagerImpl::onModelRegistry);
    }

    public static float getPartialTicks() {
        Minecraft mc = Minecraft.getInstance();
        return (mc.isPaused() ? mc.pausePartialTick : mc.getFrameTime());
    }
}
