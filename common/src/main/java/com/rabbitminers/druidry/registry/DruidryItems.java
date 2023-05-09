package com.rabbitminers.druidry.registry;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.rabbitminers.druidry.content.debug.DebugItem;
import com.rabbitminers.druidry.content.soup.bowl.SoupBlockItem;
import com.tterrag.registrate.util.entry.ItemEntry;

public class DruidryItems {
    public static DruidicRegistrate REGISTRATE = Druidry.registrate();

    public static ItemEntry<DebugItem> DEBUG = REGISTRATE.item("debug", DebugItem::new)
            .register();

    public static ItemEntry<SoupBlockItem> SOUP_ITEM = REGISTRATE.item("soup_bowl", SoupBlockItem::new)
            .register();

    public static void register() {

    }
}
