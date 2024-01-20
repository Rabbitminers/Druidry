package com.rabbitminers.druidry.multiloader.fluid.fabric;

import com.rabbitminers.druidry.multiloader.ItemStackWrapper;
import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.FluidHandler;
import com.rabbitminers.druidry.multiloader.fluid.ItemFluidHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record FabricFluidItemHandler(ItemStack stack, ContainerItemContext context, Storage<FluidVariant> storage) implements ItemFluidHandler {

    public FabricFluidItemHandler(ItemStack stack) {
        this(stack, ItemStackStorage.of(stack));
    }

    public FabricFluidItemHandler(ItemStack stack, ContainerItemContext context) {
        this(stack, context, FluidStorage.ITEM.find(stack, context));
    }

    @Override
    public long insertFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            FabricFluidStack fabricFluidHolder = FabricFluidStack.of(fluid);
            long inserted = storage.insert(fabricFluidHolder.toVariant(), fabricFluidHolder.getAmount(), transaction);
            if (!simulate) {
                transaction.commit();
                item.setStack(context.getItemVariant().toStack());
            }
            return inserted;
        }
    }

    @Override
    public IFluidStack extractFluid(ItemStackWrapper item, IFluidStack fluid, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            FabricFluidStack fabricFluidHolder = FabricFluidStack.of(fluid);
            long extracted = storage.extract(fabricFluidHolder.toVariant(), fabricFluidHolder.getAmount(), transaction);
            if (!simulate) {
                transaction.commit();
                item.setStack(context.getItemVariant().toStack());
            }
            return extracted == 0 ? FluidHandler.emptyFluid() : FabricFluidStack.of(fabricFluidHolder.toVariant(), extracted);
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
    public long getTankCapacity(int tank) {
        List<StorageView<FluidVariant>> fluids = new ArrayList<>();
        storage.iterator().forEachRemaining(fluids::add);
        return fluids.get(tank).getCapacity();
    }

    @Override
    public boolean supportsInsertion() {
        return insertFluid(new ItemStackWrapper(stack).copy(), getFluidInTank(0), true) > 0;
    }

    @Override
    public boolean supportsExtraction() {
        return extractFluid(new ItemStackWrapper(stack), getFluidInTank(0), true).getFluidAmount() > 0;
    }
}
