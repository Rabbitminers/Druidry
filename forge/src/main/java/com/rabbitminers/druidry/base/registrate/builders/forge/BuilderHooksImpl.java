package com.rabbitminers.druidry.base.registrate.builders.forge;

import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder;
import com.rabbitminers.druidry.base.registrate.builders.VirtualFluidBuilder;
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;

public class BuilderHooksImpl {
    public static <T extends DruidryFlowingFluid, P> DruidryFluidBuilder<T, P> createFluidBuilder(
            AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
            ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory
    ) {
        return new DruidryFluidBuilderImpl<>(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory);
    }

    public static <T extends DruidryFlowingFluid, P> VirtualFluidBuilder<T, P> create(
            AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
            ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory
    ) {
        return new VirtualFluidBuilderImpl<>(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory);
    }
}
