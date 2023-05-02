package com.rabbitminers.druidry.multiloader.fluid.forge;

import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.IFluidContainer;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public record ForgeFluidContainer(IFluidHandler handler) implements IFluidContainer {

    @Override
    public long insertFluid(IFluidStack fluid, boolean simulate) {
        return handler.fill(new ForgeFluidStack(fluid).getFluidStack(), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public IFluidStack extractFluid(IFluidStack fluid, boolean simulate) {
        return new ForgeFluidStack(handler.drain(new ForgeFluidStack(fluid).getFluidStack(), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
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
    public List<IFluidStack> getFluidTanks() {
        List<IFluidStack> fluids = new ArrayList<>();
        for (int i = 0; i < handler.getTanks(); i++) {
            fluids.add(getFluidInTank(i));
        }
        return fluids;
    }

    @Override
    public long getTankCapacity(int tank) {
        return handler.getTankCapacity(tank);
    }

    @Override
    public boolean supportsInsertion() {
        return true;
    }

    @Override
    public boolean supportsExtraction() {
        return true;
    }
}
