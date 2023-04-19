package com.rabbitminers.druidry.forge.base.registrate;

import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class DruidicRegistrateForge extends DruidicRegistrate {
    protected DruidicRegistrateForge(String modid) {
        super(modid);
    }

    public static DruidicRegistrateForge create(String modid) {
        return new DruidicRegistrateForge(modid);
    }

    @Override
    public @NotNull DruidicRegistrate registerEventListeners(@NotNull IEventBus bus) {
        return super.registerEventListeners(bus);
    }
}
