package com.rabbitminers.druidry.content.soup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.function.Predicate;

public record SoupIngredient(ResourceLocation location, Item ingredient, Flavour flavour, Optional<Item> returnItem,
                             Set<ResourceLocation> synergisticIngredients, Set<ResourceLocation> conflictingIngredients) {
    public void toBuffer(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(location);
        buffer.writeResourceLocation(Registry.ITEM.getKey(ingredient));
        flavour.toBuffer(buffer);
        boolean pourable = returnItem.isPresent();
        buffer.writeBoolean(pourable);
        if (pourable)
            buffer.writeResourceLocation(Registry.ITEM.getKey(returnItem.get()));
        buffer.writeCollection(synergisticIngredients, FriendlyByteBuf::writeResourceLocation);
        buffer.writeCollection(conflictingIngredients, FriendlyByteBuf::writeResourceLocation);
    }

    public static SoupIngredient fromBuffer(FriendlyByteBuf buffer) {
        ResourceLocation location = buffer.readResourceLocation();
        Item item = Registry.ITEM.get(buffer.readResourceLocation());
        Flavour taste = Flavour.fromBuffer(buffer);
        Optional<Item> emptyItem = buffer.readBoolean()
                ? getOptionalItem(buffer.readResourceLocation())
                : Optional.empty();
        Set<ResourceLocation>
                synergisticIngredients = buffer.readCollection(HashSet::new, FriendlyByteBuf::readResourceLocation),
                conflictingIngredients = buffer.readCollection(HashSet::new, FriendlyByteBuf::readResourceLocation);
        return new SoupIngredient(location, item, taste, emptyItem, synergisticIngredients, conflictingIngredients);
    }

    private static Optional<Item> getOptionalItem(ResourceLocation location) {
        Item item = Registry.ITEM.get(location);
        return item == Items.AIR ? Optional.empty() : Optional.of(item);
    }

    public static Optional<SoupIngredient> fromJson(ResourceLocation location, JsonObject object) {
        try {
            Optional<Item> item = readItemFromJson(object, "item");
            if (item.isEmpty())
                return Optional.empty();
            Optional<Item> returnItem = readItemFromJson(object, "returnItem");
            Flavour taste = Flavour.fromJson(object.get("flavour").getAsJsonObject());
            Set<ResourceLocation>
                    synergistic = readResourceLocationList(object, "synergisticIngredients"),
                    conflicting = readResourceLocationList(object, "conflictingIngredients");
            return Optional.of(
                new SoupIngredient(location, item.get(), taste, returnItem, synergistic, conflicting)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<Item> readItemFromJson(JsonObject object, String key) {
        JsonElement serializedItem = object.get(key);

        if (serializedItem == null || !serializedItem.isJsonPrimitive())
            return Optional.empty();
        JsonPrimitive primitive = serializedItem.getAsJsonPrimitive();
        if (!primitive.isString())
            return Optional.empty();

        return SoupIngredient.getOptionalItem(new ResourceLocation(primitive.getAsString()));
    }

    private static JsonPrimitive parseJsonPrimitive(JsonObject object, String key, Predicate<JsonPrimitive> predicate) {
        JsonElement element = object.get(key);
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (predicate.test(primitive)) {
                return primitive;
            }
        }
        throw new IllegalArgumentException("Invalid or missing key: " + key);
    }

    private static Set<ResourceLocation> readResourceLocationList(JsonObject object, String key) {
        Set<ResourceLocation> collection = new HashSet<>();

        JsonArray array = object.getAsJsonArray(key);

        if (array == null) {
            return collection;
        }

        for (JsonElement element : array) {
            if (!element.isJsonPrimitive()) {
                continue;
            }

            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (!primitive.isString()) {
                continue;
            }

            collection.add(new ResourceLocation(primitive.getAsString()));
        }

        return collection;
    }

    /*
    Extracted to make it more easily re-usable

    See
     */
    public record Flavour(double sweetness, double spice, double saltiness, double sour, double bitter) {
        public void toBuffer(FriendlyByteBuf buf) {
            Flavour.writeDoubles(buf, sweetness, spice, sweetness, sour, bitter);
        }

        public static Flavour fromBuffer(FriendlyByteBuf buf) {
            return new Flavour(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        public static Flavour fromJson(JsonObject object) {
            return new Flavour(
                parseJsonPrimitive(object, "sweetness", JsonPrimitive::isNumber).getAsDouble(),
                parseJsonPrimitive(object, "spice", JsonPrimitive::isNumber).getAsDouble(),
                parseJsonPrimitive(object, "saltiness", JsonPrimitive::isNumber).getAsDouble(),
                parseJsonPrimitive(object, "sour", JsonPrimitive::isNumber).getAsDouble(),
                parseJsonPrimitive(object, "bitter", JsonPrimitive::isNumber).getAsDouble()
            );
        }

        public static void writeDoubles(FriendlyByteBuf buf, double... values) {
            Arrays.stream(values).forEach(buf::writeDouble);
        }
    }
}