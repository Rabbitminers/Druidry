package com.rabbitminers.druidry.multiloader.fluid.forge;

import com.rabbitminers.druidry.multiloader.ItemStackWrapper;
import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.ItemFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public record ForgeItemFluidHandler(IFluidHandlerItem handler) implements ItemFluidHandler {
    @Override
    public long insertFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate) {
        int fill = handler.fill(new ForgeFluidStack(fluid).getFluidStack(), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
        item.setStack(handler.getContainer());
        return fill;
    }

    @Override
    public IFluidStack extractFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate) {
        ForgeFluidStack drained = new ForgeFluidStack(handler.drain(new ForgeFluidStack(fluid).getFluidStack(), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
        item.setStack(handler.getContainer());
        return drained;
    }

    @Override
    public int getTankAmount() {
        return handler.getTanks();
    }

    @Override
    public IFluidStack getFluidInTank(int tank) {
        return new ForgeFluidStack(handler.getFluidInTank(tank));
    }

    @Override
    public long getTankCapacity(int tank) {
        return handler.getTankCapacity(tank);
    }

    @Override
    public boolean supportsInsertion() {
        return insertFluid(new ItemStackWrapper(handler.getContainer()), getFluidInTank(0), true) > 0;
    }

    @Override
    public boolean supportsExtraction() {
        return extractFluid(new ItemStackWrapper(handler.getContainer()), getFluidInTank(0), true).getFluidAmount() > 0;
    }
}
