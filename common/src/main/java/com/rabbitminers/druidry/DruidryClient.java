package com.rabbitminers.druidry;

import com.rabbitminers.druidry.render.outline.Outlininator;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class DruidryClient {
    public static final Outlininator OUTLININATOR = new Outlininator();

    @ExpectPlatform
    public static float getPartialTicks() {
        throw new AssertionError();
    }

    public static void init() {

    }
}
