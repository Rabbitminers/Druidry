package com.rabbitminers.druidry.multiloader.fluid;

import java.util.List;

public interface IFluidContainer {

    long insertFluid(IFluidStack fluid, boolean simulate);

    IFluidStack extractFluid(IFluidStack fluid, boolean simulate);

    int getTankAmount();

    IFluidStack getFluidInTank(int tank);

    List<IFluidStack> getFluidTanks();

    long getTankCapacity(int tank);

    boolean supportsInsertion();

    boolean supportsExtraction();
}
