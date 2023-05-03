package com.rabbitminers.druidry.base.tile;

import com.rabbitminers.druidry.multiloader.fluid.FluidHandler;
import com.rabbitminers.druidry.multiloader.fluid.IFluidContainer;
import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IMultiblockContainer<T extends BlockEntity & IMultiblockContainer<T>> {
    BlockPos getController();

    T blockEntityType();

    void setController(BlockPos pos);

    void removeController();

    void notifyUpdated();

    int maxWidth();
    int getWidth();

    int maxHeight();
    int getHeight();

    public interface FluidContainer<T extends BlockEntity & FluidContainer<T>> extends IMultiblockContainer<T> {
        default boolean hasTank() { return false; }

        default int getTankSize(int tank) {	return 0; }

        default void setTankSize(int tank, int blocks) {}

        default IFluidContainer getTank(int tank) { return null; }

        default IFluidStack getFluid(int tank) {	return FluidHandler.emptyFluid(); }
    }
}
