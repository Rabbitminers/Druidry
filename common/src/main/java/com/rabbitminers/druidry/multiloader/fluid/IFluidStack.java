package com.rabbitminers.druidry.multiloader.fluid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

public interface IFluidStack {

    Codec<IFluidStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.FLUID.byNameCodec().fieldOf("fluid").forGetter(IFluidStack::getFluid),
            Codec.FLOAT.fieldOf("buckets").orElse(1f).forGetter(fluidHolder -> (float) fluidHolder.getFluidAmount() / FluidHandler.getBucketAmount()),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(fluidHolder -> Optional.ofNullable(fluidHolder.getCompound()))
    ).apply(instance, (fluid, buckets, compoundTag) -> FluidHandler.newFluidHolder(fluid, FluidHandler.buckets(buckets), compoundTag.orElse(null))));


    static IFluidStack of(Fluid fluid) {
        return FluidHandler.newFluidHolder(fluid, FluidHandler.buckets(1D), null);
    }

    static IFluidStack of(Fluid fluid, double buckets, CompoundTag tag) {
        return FluidHandler.newFluidHolder(fluid, FluidHandler.buckets(buckets), tag);
    }

    Fluid getFluid();

    void setFluid(Fluid fluid);

    long getFluidAmount();

    void setAmount(long amount);

    CompoundTag getCompound();

    void setCompound(CompoundTag tag);

    boolean isEmpty();

    boolean matches(IFluidStack fluidHolder);

    IFluidStack copyHolder();

    CompoundTag serialize();

    void deserialize(CompoundTag tag);

    default IFluidStack copyWithAmount(long amount) {
        IFluidStack copy = copyHolder();
        if (!copy.isEmpty()) copy.setAmount(amount);
        return copy;
    }
}
