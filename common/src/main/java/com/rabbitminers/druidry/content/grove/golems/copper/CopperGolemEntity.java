package com.rabbitminers.druidry.content.grove.golems.copper;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import org.jetbrains.annotations.NotNull;

public class CopperGolemEntity extends Mob {
    private WeatherState weatherState = WeatherState.UNAFFECTED;

    public CopperGolemEntity(EntityType<? extends CopperGolemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.95F * entityDimensions.height;
    }

    @NotNull
    public WeatherState getWeatherState() {
        return weatherState;
    }
}
