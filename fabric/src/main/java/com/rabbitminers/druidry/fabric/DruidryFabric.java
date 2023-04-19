package com.rabbitminers.druidry.fabric;

import com.rabbitminers.druidry.Druidry;
import net.fabricmc.api.ModInitializer;

public class DruidryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Druidry.init();
    }
}
