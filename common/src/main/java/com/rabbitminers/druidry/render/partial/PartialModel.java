package com.rabbitminers.druidry.render.partial;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class PartialModel {
    protected final ResourceLocation location;
    protected BakedModel bakedModel;

    public PartialModel(ResourceLocation modelLocation) {
        this.location = modelLocation;
        PartialModelManager.add(this);
    }

    public void set(BakedModel bakedModel) {
        this.bakedModel = bakedModel;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public BakedModel getModel() {
        return this.bakedModel;
    }
}
