package com.rabbitminers.druidry.base.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class NBTHelper {
    public static void writeResourceLocation(CompoundTag nbt, String key, ResourceLocation location) {
        nbt.putString(key, location.toString());
    }

    public static ResourceLocation readResourceLocation(CompoundTag nbt, String key) {
        if (!nbt.contains(key))
            return null;
        String[] data = nbt.getString(key).split(":");
        if (data.length != 2)
            return null;
        return new ResourceLocation(data[0], data[1]);
    }

    public static void writeBlockPos(CompoundTag nbt, BlockPos pos) {
        nbt.putInt("x", pos.getX());
        nbt.putInt("y", pos.getX());
        nbt.putInt("z", pos.getX());
    }

    public static BlockPos readBlockPos(CompoundTag nbt) {
        return new BlockPos(
            nbt.getInt("x"),
            nbt.getInt("y"),
            nbt.getInt("z")
        );
    }

    public static <T> void writeCollection(CompoundTag nbt, String key, Collection<T> collection) {
        String serialized = collection.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        nbt.putString(key, serialized);
    }

    public static Collection<String> readCollection(CompoundTag nbt, String key) {
        String serialized = nbt.getString(key);
        return Arrays.stream(serialized.split(", "))
                .map(String::trim)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
