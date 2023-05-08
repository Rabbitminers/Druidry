package com.rabbitminers.druidry.base.registrate;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.builders.BuilderHooks;
import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder;
import com.rabbitminers.druidry.base.registrate.builders.VirtualFluidBuilder;
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.rabbitminers.druidry.multiloader.fluid.VirtualFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;

public abstract class DruidicRegistrate extends AbstractRegistrate<DruidicRegistrate> {
    protected DruidicRegistrate(String modid) {
        super(modid);
    }

    public <T extends DruidryFlowingFluid> DruidryFluidBuilder<T, DruidicRegistrate> virtualFluid(String name,
            NonNullFunction<DruidryFlowingFluid.Properties, T> factory) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, Druidry.asResource("fluid/" + name + "_still"),
                        Druidry.asResource("fluid/" + name + "_flow"), factory));
    }

    public <T extends DruidryFlowingFluid> DruidryFluidBuilder<T, DruidicRegistrate> virtualFluid(String name, ResourceLocation still,
            ResourceLocation flow, NonNullFunction<DruidryFlowingFluid.Properties, T> factory) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, still, flow, factory));
    }

    public DruidryFluidBuilder<VirtualFluid, DruidicRegistrate> virtualFluid(String name) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, Druidry.asResource("fluid/" + name + "_still"),
                        Druidry.asResource("fluid/" + name + "_flow"), VirtualFluid::new));
    }

    public DruidryFluidBuilder<VirtualFluid, DruidicRegistrate> virtualFluid(String name, ResourceLocation still, ResourceLocation flow) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, still, flow, VirtualFluid::new));
    }

    public DruidryFluidBuilder<DruidryFlowingFluid.Flowing, DruidicRegistrate> standardFluid(String name) {
        return standardFluid(name, Druidry.asResource("fluid/" + name + "_still"), Druidry.asResource("fluid/" + name + "_flow"));
    }

    public DruidryFluidBuilder<DruidryFlowingFluid.Flowing, DruidicRegistrate> standardFluid(String name, ResourceLocation stillTexture,
                                                                                             ResourceLocation flowingTexture) {
        return standardFluid(this, name, stillTexture, flowingTexture);
    }

    public DruidryFluidBuilder<DruidryFlowingFluid.Flowing, DruidicRegistrate> standardFluid(DruidicRegistrate parent, String name, ResourceLocation stillTexture,
                                                                                             ResourceLocation flowingTexture) {
        return entry(name, callback -> DruidryFluidBuilder.create(this, parent, name, callback, stillTexture, flowingTexture));
    }
}
