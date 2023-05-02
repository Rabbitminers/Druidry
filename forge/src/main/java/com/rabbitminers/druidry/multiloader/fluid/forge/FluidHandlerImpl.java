package com.rabbitminers.druidry.multiloader.fluid.forge;

import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.IFluidContainer;
import com.rabbitminers.druidry.multiloader.fluid.IFluidItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

public class FluidHandlerImpl {
    
    public static IFluidStack newFluidHolder(Fluid fluid, long amount, CompoundTag tag) {
        return new ForgeFluidStack(fluid, (int) amount, tag);
    }

    
    public static IFluidStack fluidFromCompound(CompoundTag compoundTag) {
        return ForgeFluidStack.fromCompound(compoundTag);
    }

    
    public static IFluidItem getItemFluidManager(ItemStack stack) {
        return new ForgeFluidItem(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElseThrow(IllegalArgumentException::new));
    }

    
    public static IFluidContainer getBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        return new ForgeFluidContainer(entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElseThrow(IllegalArgumentException::new));
    }

    
    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        return entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).isPresent();
    }

    
    public static boolean isFluidContainingItem(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }

    
    public static IFluidStack emptyFluid() {
        return ForgeFluidStack.empty();
    }

    
    public static long toMillibuckets(long amount) {
        return amount;
    }

    
    public static long getBucketAmount() {
        return FluidType.BUCKET_VOLUME;
    }

    
    public static long getBottleAmount() {
        return FluidType.BUCKET_VOLUME / 4;
    }

    
    public static long getBlockAmount() {
        return FluidType.BUCKET_VOLUME;
    }

    
    public static long getIngotAmount() {
        return 90;
    }

    
    public static long getNuggetAmount() {
        return 10;
    }
}
