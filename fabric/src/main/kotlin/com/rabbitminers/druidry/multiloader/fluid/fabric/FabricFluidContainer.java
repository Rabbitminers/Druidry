package com.rabbitminers.druidry.multiloader.fluid.fabric;

import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.IFluidContainer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public record FabricFluidContainer(Storage<FluidVariant> storage) implements IFluidContainer {

    @Override
    public long insertFluid(IFluidStack fluid, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            FabricFluidStack fabricFluidHolder = FabricFluidStack.of(fluid);
            long inserted = storage.insert(fabricFluidHolder.toVariant(), fabricFluidHolder.getAmount(), transaction);
            if (!simulate) {
                transaction.commit();
            }
            return inserted;
        }
    }

    @Override
    public IFluidStack extractFluid(IFluidStack fluid, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            FabricFluidStack fabricFluidHolder = FabricFluidStack.of(fluid);
            long extracted = storage.extract(fabricFluidHolder.toVariant(), fabricFluidHolder.getAmount(), transaction);
            if (!simulate) {
                transaction.commit();
            }
            return extracted == 0 ? FabricFluidStack.of(fabricFluidHolder.toVariant(), extracted) : fluid;
        }
    }

    @Override
    public int getTankAmount() {
        int size = 0;
        for (StorageView<FluidVariant> ignored : storage) {
            size++;
        }
        return size;
    }

    @Override
    public IFluidStack getFluidInTank(int tank) {
        List<IFluidStack> fluids = new ArrayList<>();
        storage.iterator().forEachRemaining(variant -> fluids.add(FabricFluidStack.of(variant.getResource(), variant.getAmount())));
        return fluids.get(tank);
    }

    @Override
    public List<IFluidStack> getFluidTanks() {
        List<IFluidStack> fluids = new ArrayList<>();
        storage.iterator().forEachRemaining(variant -> fluids.add(FabricFluidStack.of(variant.getResource(), variant.getAmount())));
        return fluids;
    }

    @Override
    public long getTankCapacity(int tank) {
        List<StorageView<FluidVariant>> fluids = new ArrayList<>();
        storage.iterator().forEachRemaining(fluids::add);
        return fluids.get(tank).getCapacity();
    }

    @Override
    public boolean supportsInsertion() {
        return insertFluid(getFluidInTank(0), true) > 0;
    }

    @Override
    public boolean supportsExtraction() {
        return extractFluid(getFluidInTank(0), true).getFluidAmount() > 0;
    }
}
