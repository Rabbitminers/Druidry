package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;

public class BuilderHooks {
    @ExpectPlatform
    public static <T extends DruidryFlowingFluid, P> DruidryFluidBuilder<T, P> createFluidBuilder(
            AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
            ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory
    ) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends DruidryFlowingFluid, P> VirtualFluidBuilder<T, P> create(AbstractRegistrate<?> owner, P parent,
          String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture,
          NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory
    ) {
        throw new AssertionError();
    }
}
