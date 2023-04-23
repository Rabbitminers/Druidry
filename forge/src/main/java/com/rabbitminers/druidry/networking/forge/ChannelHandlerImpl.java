package com.rabbitminers.druidry.networking.forge;

import com.rabbitminers.druidry.networking.ChannelHandler;
import com.rabbitminers.druidry.networking.NetworkDir;
import com.rabbitminers.druidry.networking.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChannelHandlerImpl extends ChannelHandler {
    public static ChannelHandler createChannel(ResourceLocation channelName) {
        return new ChannelHandlerImpl(channelName);
    }

    public final SimpleChannel channel;
    protected int id = 0;

    protected ChannelHandlerImpl(ResourceLocation channelName) {
        super(channelName);
        String version = "1";
        this.channel = NetworkRegistry.newSimpleChannel(channelName, () -> version,
                version::equals, version::equals);
    }

    @Override
    public <M extends Packet> void register(
            NetworkDir dir,
            Class<M> messageClass,
            Function<FriendlyByteBuf, M> decoder) {
        Optional<NetworkDirection> d = switch (dir){
            case BOTH -> Optional.empty();
            case PLAY_TO_CLIENT -> Optional.of(NetworkDirection.PLAY_TO_CLIENT);
            case PLAY_TO_SERVER -> Optional.of(NetworkDirection.PLAY_TO_SERVER);
        };

        channel.registerMessage(id++, messageClass, Packet::writeToBuffer, decoder, this::consumer, d);
    }

    private <P extends Packet> void consumer(P packet, Supplier<NetworkEvent.Context> context) {
        var c = context.get();
        c.enqueueWork(() -> packet.handle(new Wrapper(c)));
        c.setPacketHandled(true);
    }

    static class Wrapper implements Context {

        private final NetworkEvent.Context context;

        public Wrapper(NetworkEvent.Context ctx) {
            this.context = ctx;
        }

        @Override
        public NetworkDir getDirection() {
            return switch (context.getDirection()) {
                case PLAY_TO_CLIENT -> NetworkDir.PLAY_TO_CLIENT;
                default -> NetworkDir.PLAY_TO_SERVER;
            };
        }

        @Override
        public Player getSender() {
            return context.getSender();
        }
    }

    @Override
    public void sendToClientPlayer(ServerPlayer serverPlayer, Packet packet) {
        channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
    }

    @Override
    public void sendToAllClientPlayers(Packet packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    @Override
    public void sendToServer(Packet packet) {
        channel.sendToServer(packet);
    }

    @Override
    public void sendToAllClientPlayersInRange(Level level, BlockPos pos, double radius, Packet packet) {
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        if (currentServer != null) {
            var distributor = PacketDistributor.NEAR.with(() ->
                    new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), radius, level.dimension()));
            channel.send(distributor, packet);
        }
    }

    @Override
    public void sentToAllClientPlayersTrackingEntity(Entity target, Packet packet) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), packet);
    }

    @Override
    public void sentToAllClientPlayersTrackingEntityAndSelf(Entity target, Packet packet) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> target), packet);
    }

}
