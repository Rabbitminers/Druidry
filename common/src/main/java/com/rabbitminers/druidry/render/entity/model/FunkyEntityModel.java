package com.rabbitminers.druidry.render.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Function;

public abstract class FunkyEntityModel<T extends Entity> extends EntityModel<T> {
    public final int textureWidth = 64;
    public final int textureHeight = 64;
    public final List<FunkyEntityModelRenderer> modelParts = Lists.newArrayList();

    protected FunkyEntityModel() {
        this(RenderType::entityCutoutNoCull);
    }

    protected FunkyEntityModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }

    public void accept(FunkyEntityModelRenderer modelRenderer) {
        modelParts.add(modelRenderer);
    }

    @Override
    public abstract void setupAnim(T entity, float f, float g, float h, float i, float j);

    public abstract void prepareModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_);

}
