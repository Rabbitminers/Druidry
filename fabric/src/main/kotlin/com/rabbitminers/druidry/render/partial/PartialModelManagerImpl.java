package com.rabbitminers.druidry.render.partial;

import com.rabbitminers.druidry.render.partial.PartialModel;
import com.rabbitminers.druidry.render.partial.PartialModelManager;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.function.Consumer;

public class PartialModelManagerImpl {
    public static void onModelRegistry(ResourceManager manager, Consumer<ResourceLocation> out) {
        for (PartialModel partial : PartialModelManager.getModels())
            out.accept(partial.getLocation());

        PartialModelManager.onModelRegistry();
    }

    public static void onModelBake(ModelManager manager) {
        for (PartialModel partial : PartialModelManager.getModels())
            partial.set(BakedModelManagerHelper.getModel(manager, partial.getLocation()));
    }

}
