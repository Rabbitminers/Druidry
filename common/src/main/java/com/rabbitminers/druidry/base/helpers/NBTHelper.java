package com.rabbitminers.druidry.base.helpers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
}
