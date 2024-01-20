package com.rabbitminers.druidry.fabric.base.registrate;

import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;

public class DruidicRegistrateFabric extends DruidicRegistrate {
    protected DruidicRegistrateFabric(String modid) {
        super(modid);
    }

    public static DruidicRegistrateFabric create(String modid) {
        return new DruidicRegistrateFabric(modid);
    }
}
