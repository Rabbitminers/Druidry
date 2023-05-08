package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.content.soup.SoupFluid;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class DruidryFluids {
    private static final DruidicRegistrate REGISTRATE = Druidry.registrate();

    public static RegistryEntry<SoupFluid> SOUP = REGISTRATE.virtualFluid("soup", SoupFluid::new)
            .lang("Soup")
            .register();

    public static void register() {

    }
}
