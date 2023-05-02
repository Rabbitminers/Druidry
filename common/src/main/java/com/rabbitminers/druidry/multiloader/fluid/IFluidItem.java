package com.rabbitminers.druidry.multiloader.fluid;

import com.rabbitminers.druidry.multiloader.ItemStackWrapper;

public interface IFluidItem {

    long insertFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate);

    IFluidStack extractFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate);

    int getTankAmount();

    IFluidStack getFluidInTank(int tank);

    long getTankCapacity(int tank);

    boolean supportsInsertion();

    boolean supportsExtraction();
}
