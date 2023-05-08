package com.rabbitminers.druidry;

import com.mojang.logging.LogUtils;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.networking.DruidryNetworkHandler;
import com.rabbitminers.druidry.registry.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;


public class Druidry {
    public static final String MOD_ID = "druidry";
    public static final String MOD_NAME = "Druidry";

    public static final Logger LOGGER = LogUtils.getLogger();


    public static void init() {
        DruidryNetworkHandler.register();
        DruidryEntities.register();
        DruidryBlocks.register();
        DruidryBlockEntities.register();
        DruidryItems.register();
        DruidryFluids.register();
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    @ExpectPlatform
    public static DruidicRegistrate registrate() {
        throw new AssertionError();
    }
}
