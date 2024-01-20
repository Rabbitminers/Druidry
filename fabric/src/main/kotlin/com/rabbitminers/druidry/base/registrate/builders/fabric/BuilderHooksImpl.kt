package com.rabbitminers.druidry.base.registrate.builders.fabric

import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid
import com.rabbitminers.druidry.base.registrate.builders.VirtualFluidBuilder
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.resources.ResourceLocation

class BuilderHooksImpl {
    fun <T : DruidryFlowingFluid?, P> createFluidBuilder(
            owner: AbstractRegistrate<*>?, parent: P, name: String?, callback: BuilderCallback?, stillTexture: ResourceLocation?,
            flowingTexture: ResourceLocation?, fluidFactory: NonNullFunction<DruidryFlowingFluid.Properties?, T>?
    ): DruidryFluidBuilder<T, P> {
        return DruidryFluidBuilderImpl(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory)
    }

    fun <T : DruidryFlowingFluid?, P> create(
            owner: AbstractRegistrate<*>?, parent: P, name: String?, callback: BuilderCallback?, stillTexture: ResourceLocation?,
            flowingTexture: ResourceLocation?, fluidFactory: NonNullFunction<DruidryFlowingFluid.Properties?, T>?
    ): VirtualFluidBuilder<T, P> {
        return VirtualFluidBuilderImpl(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory)
    }
}