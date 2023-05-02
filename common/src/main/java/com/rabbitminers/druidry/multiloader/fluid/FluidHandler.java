package com.rabbitminers.druidry.multiloader.fluid;

import com.rabbitminers.druidry.multiloader.ItemStackWrapper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FluidHandler {

    /**
     * Creates a new fluid holder a (platform specific) abstraction over the native fluid interfaces to replicate
     * (roughly) the functionality of a FluidStack
     *
     * @param fluid The type of fluid to be made
     * @param amount The amount (int mb) to be made
     * @param tag (Optional) compound tag to be written and read from
     * @return new FluidStack wrapper class
     */
    @ExpectPlatform
    public static IFluidStack newFluidHolder(Fluid fluid, long amount, @Nullable CompoundTag tag) {
        throw new AssertionError();
    }

    /**
     * Creates a new fluid holder a (platform specific) abstraction over the native fluid interfaces to replicate
     * (roughly) the functionality of a FluidStack
     *
     * @param compoundTag compoundTag to be read
     * @return new FluidStack wrapper class
     */
    @ExpectPlatform
    public static IFluidStack fluidFromCompound(CompoundTag compoundTag) {
        throw new AssertionError();
    }

    /**
     * Creates a new fluid holder a (platform specific) abstraction over the native fluid interfaces to replicate
     * (roughly) the functionality of a FluidStack
     *
     * @return new empty FluidStack wrapper class
     */
    @ExpectPlatform
    public static IFluidStack emptyFluid() {
        throw new AssertionError();
    }

    /**
     * Get volume (in mb) of a given amount of buckets
     *
     * @param buckets The count of buckets to get the volume of
     * @return volume (in mb)
     */
    public static long buckets(double buckets) {
        return (long) (buckets * getBucketAmount());
    }

    /**
     * Creates a new Item bound fluid holder a (platform specific) abstraction over the native fluid interfaces to
     * replicate(roughly) the functionality of a FluidStack
     *
     * @param stack The itemstack to bind to
     * @return Fluid Item Abstraction bound to fluid stack
     */
    @ExpectPlatform
    public static IFluidItem getItemFluidManager(ItemStack stack) {
        throw new AssertionError();
    }

    /**
     * Creates a new Block bound fluid holder a (platform specific) abstraction over the native fluid interfaces to
     * replicate(roughly) the functionality of a FluidStack
     *
     * @param entity The Blockentity to bind the fluid stack to
     * @param direction (optional) input/output direction
     * @return new FluidContainer abstraction over fluid stack bound to block
     */
    @ExpectPlatform
    public static IFluidContainer getBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        throw new AssertionError();
    }

    /**
     * Utility function to check if a BlockEntity has a fluid container
     *
     * @param entity The BlockEntity to check
     * @param direction The direction to check
     * @return boolean value if a fluid handler is present
     */
    @ExpectPlatform
    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        throw new AssertionError();
    }

    /**
     * Utility function to check if an ItemStack has a fluid container
     *
     * @param stack The ItemStack to check
     * @return boolean value if a fluid handler is present
     */
    @ExpectPlatform
    public static boolean isFluidContainingItem(ItemStack stack) {
        throw new AssertionError();
    }

    /**
     *
     * @param entity
     * @param direction
     * @return
     */
    public static Optional<IFluidContainer> safeGetBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        if (entity == null) return Optional.empty();
        return isFluidContainingBlock(entity, direction) ? Optional.of(getBlockFluidManager(entity, direction)) : Optional.empty();
    }

    /**
     *
     * @param stack
     * @return
     */
    public static Optional<IFluidItem> safeGetItemFluidManager(ItemStack stack) {
        return isFluidContainingItem(stack) ? Optional.of(getItemFluidManager(stack)) : Optional.empty();
    }

    /**
     *
     * @param from
     * @param to
     * @param fluid
     * @return
     */
    public static long moveFluid(IFluidContainer from, IFluidContainer to, IFluidStack fluid) {
        IFluidStack extracted = from.extractFluid(fluid, true);
        long inserted = to.insertFluid(extracted, true);
        from.extractFluid(newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(extracted, false);
    }

    /**
     *
     * @param from
     * @param sender
     * @param to
     * @param fluid
     * @return
     */
    public static long moveItemToStandardFluid(IFluidItem from, ItemStackWrapper sender, IFluidContainer to, IFluidStack fluid) {
        IFluidStack extracted = from.extractFluid(sender.copy(), fluid, true);
        long inserted = to.insertFluid(extracted, true);
        from.extractFluid(sender, newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(extracted, false);
    }

    /**
     *
     * @param from
     * @param to
     * @param receiver
     * @param fluid
     * @return
     */
    public static long moveStandardToItemFluid(IFluidContainer from, IFluidItem to, ItemStackWrapper receiver, IFluidStack fluid) {
        IFluidStack extracted = from.extractFluid(fluid, true);
        long inserted = to.insertFluid(receiver.copy(), extracted, true);
        from.extractFluid(newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(receiver, extracted, false);
    }

    /**
     *
     * @param from
     * @param sender
     * @param to
     * @param receiver
     * @param fluid
     * @return
     */
    public static long moveItemToItemFluid(IFluidItem from, ItemStackWrapper sender, IFluidItem to, ItemStackWrapper receiver, IFluidStack fluid) {
        IFluidStack extracted = from.extractFluid(sender.copy(), fluid, true);
        long inserted = to.insertFluid(receiver.copy(), extracted, true);
        from.extractFluid(sender, newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(receiver, extracted, false);
    }

    /**
     *
     * @param from
     * @param to
     * @param fluid
     * @return
     */
    public static long safeMoveFluid(Optional<IFluidContainer> from, Optional<IFluidContainer> to, IFluidStack fluid) {
        return from.flatMap(f -> to.map(t -> moveFluid(f, t, fluid))).orElse(0L);
    }

    /**
     *
     * @param from
     * @param sender
     * @param to
     * @param fluid
     * @return
     */
    public static long safeMoveItemToStandard(Optional<IFluidItem> from, ItemStackWrapper sender, Optional<IFluidContainer> to, IFluidStack fluid) {
        return from.flatMap(f -> to.map(t -> moveItemToStandardFluid(f, sender, t, fluid))).orElse(0L);
    }

    /**
     *
     * @param from
     * @param to
     * @param receiver
     * @param fluid
     * @return
     */
    public static long safeMoveStandardToItem(Optional<IFluidContainer> from, Optional<IFluidItem> to, ItemStackWrapper receiver, IFluidStack fluid) {
        return from.flatMap(f -> to.map(t -> moveStandardToItemFluid(f, t, receiver, fluid))).orElse(0L);
    }

    /**
     *
     * @param from
     * @param sender
     * @param to
     * @param receiver
     * @param fluid
     * @return
     */
    public static long safeMoveItemToItem(Optional<IFluidItem> from, ItemStackWrapper sender, Optional<IFluidItem> to, ItemStackWrapper receiver, IFluidStack fluid) {
        return from.flatMap(f -> to.map(t -> moveItemToItemFluid(f, sender, t, receiver, fluid))).orElse(0L);
    }

    /**
     *
     * @param from
     * @param fromDirection
     * @param to
     * @param toDirection
     * @param fluid
     * @return
     */
    public static long moveBlockToBlockFluid(BlockEntity from, @Nullable Direction fromDirection, BlockEntity to, @Nullable Direction toDirection, IFluidStack fluid) {
        return safeMoveFluid(safeGetBlockFluidManager(from, fromDirection), safeGetBlockFluidManager(to, toDirection), fluid);
    }

    /**
     *
     * @param from
     * @param fromDirection
     * @param to
     * @param fluid
     * @return
     */
    public static long moveBlockToItemFluid(BlockEntity from, @Nullable Direction fromDirection, ItemStackWrapper to, IFluidStack fluid) {
        return safeMoveStandardToItem(safeGetBlockFluidManager(from, fromDirection), safeGetItemFluidManager(to.getStack()), to, fluid);
    }

    /**
     *
     * @param from
     * @param to
     * @param toDirection
     * @param fluid
     * @return
     */
    public static long moveItemToBlockFluid(ItemStackWrapper from, BlockEntity to, @Nullable Direction toDirection, IFluidStack fluid) {
        return safeMoveItemToStandard(safeGetItemFluidManager(from.getStack()), from, safeGetBlockFluidManager(to, toDirection), fluid);
    }

    /**
     *
     * @param from
     * @param to
     * @param fluid
     * @return
     */
    public static long moveItemToItemFluid(ItemStackWrapper from, ItemStackWrapper to, IFluidStack fluid) {
        return safeMoveItemToItem(safeGetItemFluidManager(from.getStack()), from, safeGetItemFluidManager(to.getStack()), to, fluid);
    }

    @ExpectPlatform
    public static long toMillibuckets(long amount) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static long getBucketAmount() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static long getBottleAmount() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static long getBlockAmount() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static long getIngotAmount() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static long getNuggetAmount() {
        throw new AssertionError();
    }
}
