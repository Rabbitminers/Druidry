package com.rabbitminers.druidry.networking;

import net.minecraft.network.FriendlyByteBuf;

public interface Packet {
    void writeToBuffer(FriendlyByteBuf buf);

    void handle(ChannelHandler.Context context);
}
