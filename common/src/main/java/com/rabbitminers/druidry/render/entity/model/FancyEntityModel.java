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
    public final int textureWidth;
    public final int textureHeight;
    public final List<FancyEntityModelPart> modelParts = new ArrayList<>();

    protected FancyEntityModel(int w, int h) {
        this(RenderType::entityCutoutNoCull, w, h);
    }

    protected FancyEntityModel(Function<ResourceLocation, RenderType> renderType, int w, int h) {
        super(renderType);
        this.textureWidth = w;
        this.textureHeight = h;
    }

    public void accept(FancyEntityModelPart modelRenderer) {
        modelParts.add(modelRenderer);
    }

    public FancyEntityModelPart createPart(int x, int y) {
        return new FancyEntityModelPart(this, x, y);
    }

    @Override
    public abstract void setupAnim(@NotNull T entity, float f, float g, float h, float i, float j);
}
