package com.rabbitminers.druidry.render.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class FancyEntityModel<T extends Entity> extends EntityModel<T> {
    public final int textureWidth = 64;
    public final int textureHeight = 64;
    public final List<FancyEntityModelPart> modelParts = new ArrayList<>();

    protected FancyEntityModel() {
        this(RenderType::entityCutoutNoCull);
    }

    protected FancyEntityModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }

    public void accept(FancyEntityModelPart modelRenderer) {
        modelParts.add(modelRenderer);
    }

    @Override
    public abstract void setupAnim(@NotNull T entity, float f, float g, float h, float i, float j);
}
