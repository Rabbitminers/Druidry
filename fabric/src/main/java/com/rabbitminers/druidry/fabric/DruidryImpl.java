package com.rabbitminers.druidry.fabric;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.fabric.events.CommonEventsFabric;
import com.rabbitminers.druidry.fabric.base.registrate.DruidicRegistrateFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class DruidryImpl implements ModInitializer {
    public static DruidicRegistrateFabric REGISTRATE = DruidicRegistrateFabric.create(Druidry.MOD_ID);
    public static MinecraftServer currentServer;

    @Override
    public void onInitialize() {
        Druidry.init();
        REGISTRATE.register();
        CommonEventsFabric.register();

        ServerLifecycleEvents.SERVER_STARTING.register(s -> currentServer = s);
    }

    public static DruidicRegistrate registrate() {
        return REGISTRATE;
    }
}
