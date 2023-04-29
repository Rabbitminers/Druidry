package com.rabbitminers.druidry.forge.events;

import com.rabbitminers.druidry.events.ClientEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventsForge {
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        ClientEvents.onTick();
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelLastEvent event) {
        ClientEvents.onRenderWorld(event.getPoseStack());
    }
}
