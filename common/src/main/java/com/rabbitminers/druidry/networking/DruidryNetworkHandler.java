package com.rabbitminers.druidry.networking;

import com.rabbitminers.druidry.Druidry;

public class DruidryNetworkHandler {
    public static final ChannelHandler CHANNEL = ChannelHandler.createChannel(Druidry.asResource("main"));

    public static void register() {
        // CHANNEL.register(NetworkDir.PLAY_TO_CLIENT, SoupIngredientsManager.SyncPacket.class, SoupIngredientsManager.SyncPacket::new);
    }
}
