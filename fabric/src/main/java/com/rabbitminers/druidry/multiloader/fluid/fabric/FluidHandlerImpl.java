package com.rabbitminers.druidry.multiloader.fluid.fabric;

import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.IFluidContainer;
import com.rabbitminers.druidry.multiloader.fluid.IFluidItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class FluidHandlerImpl {

    public static IFluidStack newFluidHolder(Fluid fluid, long amount, CompoundTag tag) {
        return FabricFluidStack.of(fluid, tag, amount);
    }

    public static IFluidStack fluidFromCompound(CompoundTag compoundTag) {
        FabricFluidStack fluid = FabricFluidStack.of(null, 0);
        fluid.deserialize(compoundTag);
        return fluid;
    }

    public static IFluidStack emptyFluid() {
        return FabricFluidStack.empty();
    }

    public static IFluidItem getItemFluidManager(ItemStack stack) {
        return new FabricFluidItemHandler(stack);
    }

    public static IFluidContainer getBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        return new FabricFluidContainer(FluidStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), direction));
    }

    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        return FluidStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), direction) != null;
    }

    public static boolean isFluidContainingItem(ItemStack stack) {
        return FluidStorage.ITEM.find(stack, ItemStackStorage.of(stack)) != null;
    }

    public static long toMillibuckets(long amount) {
        return amount / 81;
    }

    public static long getBucketAmount() {
        return FluidConstants.BUCKET;
    }

    public static long getBottleAmount() {
        return FluidConstants.BOTTLE;
    }

    public static long getBlockAmount() {
        return FluidConstants.BLOCK;
    }

    public static long getIngotAmount() {
        return FluidConstants.INGOT;
    }

    public static long getNuggetAmount() {
        return FluidConstants.NUGGET;
    }
}
