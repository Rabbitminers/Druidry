package com.rabbitminers.druidry.content.soup.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.networking.ChannelHandler;
import com.rabbitminers.druidry.networking.Packet;
import com.rabbitminers.druidry.registry.DruidryRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

public class SoupIngredientsManager {
    public static final Map<ResourceLocation, SoupIngredient> INGREDIENTS = new HashMap<>();
    public static final Map<Item, SoupIngredient> ITEM_TO_INGREDIENTS = new IdentityHashMap<>();

    public static void toBuffer(FriendlyByteBuf buffer) {
        buffer.writeVarInt(INGREDIENTS.size());
        INGREDIENTS.values().forEach(ingredient -> ingredient.toBuffer(buffer));
    }

    public static void fromBuffer(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        INGREDIENTS.clear();

        for (int i = 0; i < size; i++) {
            SoupIngredient ingredient = SoupIngredient.fromBuffer(buffer);
            INGREDIENTS.put(ingredient.location(), ingredient);
        }
    }

    public static void mapMapToItemMap() {
        for (Map.Entry<ResourceLocation, SoupIngredient> entry : INGREDIENTS.entrySet()) {
            SoupIngredient type = entry.getValue();
            ITEM_TO_INGREDIENTS.put(type.ingredient(), type);
        }
    }

    public static void clear() {
        INGREDIENTS.clear();
        ITEM_TO_INGREDIENTS.clear();
    }

    public static class ReloadListener extends SimpleJsonResourceReloadListener {
        private static Gson GSON = new Gson();
        public static final ReloadListener INSTANCE = new ReloadListener();

        protected ReloadListener() {
            super(GSON, "soup_ingredients");
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
            clear();

            for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
                JsonElement element = entry.getValue();
                if (!element.isJsonObject())
                    return;

                ResourceLocation id = entry.getKey();
                JsonObject object = element.getAsJsonObject();
                Optional<SoupIngredient> ingredient = SoupIngredient.fromJson(id, object);

                if (ingredient.isEmpty()) {
                    Druidry.LOGGER.error("Unable to register invalid soup ingredient: " + id);
                    continue;
                }

                INGREDIENTS.put(id, ingredient.get());
            }

            mapMapToItemMap();
        }
    }

    public static class SyncPacket implements Packet {
        private FriendlyByteBuf buffer;

        public SyncPacket() {

        }

        public SyncPacket(FriendlyByteBuf buffer) {
            this.buffer = buffer;
        }

        @Override
        public void writeToBuffer(FriendlyByteBuf buffer) {
            toBuffer(buffer);
        }

        @Override
        public void handle(ChannelHandler.Context context) {
            fromBuffer(this.buffer);
        }
    }

}