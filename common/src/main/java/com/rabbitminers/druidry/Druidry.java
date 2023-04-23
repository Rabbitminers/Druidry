package com.rabbitminers.druidry;

import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.networking.DruidryNetworkHandler;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;

public class Druidry {
    public static final String MOD_ID = "druidry";
    public static final String MOD_NAME = "Druidry";

    public static void init() {
        DruidryNetworkHandler.register();
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    @ExpectPlatform
    public static DruidicRegistrate registrate() {
        throw new AssertionError();
    }
}
