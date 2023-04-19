package com.rabbitminers.druidry.fabric;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.fabric.base.registrate.DruidicRegistrateFabric;
import net.fabricmc.api.ModInitializer;

public class DruidryImpl implements ModInitializer {
    public static DruidicRegistrateFabric REGISTRATE = DruidicRegistrateFabric.create(Druidry.MOD_ID);

    @Override
    public void onInitialize() {
        Druidry.init();
        REGISTRATE.register();
    }

    public static DruidicRegistrate registrate() {
        return REGISTRATE;
    }
}
