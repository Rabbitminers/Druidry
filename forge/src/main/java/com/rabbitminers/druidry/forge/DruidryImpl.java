package com.rabbitminers.druidry.forge;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.forge.base.registrate.DruidicRegistrateForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Druidry.MOD_ID)
public class DruidryImpl {
    public static DruidicRegistrateForge REGISTRATE = DruidicRegistrateForge.create(Druidry.MOD_ID);

    public DruidryImpl() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        Druidry.init();
    }

    public static DruidicRegistrate registrate() {
        return REGISTRATE;
    }
}

