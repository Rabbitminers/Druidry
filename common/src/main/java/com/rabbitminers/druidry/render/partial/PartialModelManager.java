package com.rabbitminers.druidry.render.partial;


import java.util.ArrayList;
import java.util.List;

public class PartialModelManager {
    protected static final List<PartialModel> partialModels = new ArrayList<>();
    protected static boolean isPastModelRegistry = false;

    public static PartialModel add(PartialModel partialModel) {
        if (isPastModelRegistry)
            throw new RuntimeException("Attempted to register partial model after model registry");
        partialModels.add(partialModel);
        return partialModel;
    }

    public static void onModelRegistry() {
        isPastModelRegistry = true;
    }

    public static List<PartialModel> getModels() {
        return PartialModelManager.partialModels;
    }
}
