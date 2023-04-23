package com.rabbitminers.druidry.networking;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public abstract class ChannelHandler {
    @ExpectPlatform
    public static ChannelHandler createChannel(ResourceLocation channelName) {
        throw new AssertionError();
    }

    protected final ResourceLocation channelName;

    protected ChannelHandler(ResourceLocation channelName) {
        this.channelName = channelName;
    }

    public abstract <M extends Packet> void register(
        NetworkDir direction,
        Class<M> messageClass,
        Function<FriendlyByteBuf, M> decoder
    );

    public interface Context {
        NetworkDir getDirection();

        Player getSender();
    }

    public abstract void sendToClientPlayer(ServerPlayer serverPlayer, Packet packet);

    public abstract void sendToAllClientPlayers(Packet packet);

    public abstract void sendToAllClientPlayersInRange(Level level, BlockPos pos, double radius, Packet packet);

    public abstract void sentToAllClientPlayersTrackingEntity(Entity target, Packet packet);

    public abstract void sentToAllClientPlayersTrackingEntityAndSelf(Entity target, Packet packet);

    public abstract void sendToServer(Packet packet);
}
