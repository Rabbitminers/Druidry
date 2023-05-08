package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class VirtualFluidBuilder<T extends DruidryFlowingFluid, P> extends DruidryFluidBuilder<T, P> {
    public VirtualFluidBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
                               ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory) {
        super(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory);
        source(fluidFactory);
    }

    @Override
    public @NotNull RegistryEntry<T> register() {
        return super.register();
    }

    @Override
    public @NotNull NonNullSupplier<T> asSupplier() {
        return super.asSupplier();
    }
}
