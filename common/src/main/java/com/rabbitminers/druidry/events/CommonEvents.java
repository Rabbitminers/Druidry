package com.rabbitminers.druidry.events;

import com.rabbitminers.druidry.content.soup.data.SoupIngredientsManager;
import net.minecraft.server.level.ServerPlayer;

public class CommonEvents {
    public static void onDatapackSync(ServerPlayer player) {
        if (player != null) {
            SoupIngredientsManager.syncTo(player);
        } else {
            SoupIngredientsManager.syncToAll();
        }
    }
}
