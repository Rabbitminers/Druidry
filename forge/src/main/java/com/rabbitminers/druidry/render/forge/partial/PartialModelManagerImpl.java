package com.rabbitminers.druidry.render.forge.partial;

import com.rabbitminers.druidry.render.partial.PartialModel;
import com.rabbitminers.druidry.render.partial.PartialModelManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

import java.util.Map;

public class PartialModelManagerImpl {
    public static void onModelRegistry(ModelEvent.RegisterAdditional event) {
        for (PartialModel partial : PartialModelManager.getModels())
            event.register(partial.getLocation());

        PartialModelManager.onModelRegistry();
    }

    public static void onModelBake(ModelEvent.BakingCompleted event) {
        Map<ResourceLocation, BakedModel> models = event.getModels();
        for (PartialModel partial : PartialModelManager.getModels())
            partial.set(models.get(partial.getLocation()));
    }
}
