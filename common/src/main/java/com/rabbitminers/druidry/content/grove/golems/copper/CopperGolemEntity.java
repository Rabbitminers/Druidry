package com.rabbitminers.druidry.content.grove.golems.copper;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CopperGolemEntity extends Mob {
    private WeatherState weatherState = WeatherState.UNAFFECTED;

    public CopperGolemEntity(EntityType<? extends CopperGolemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.55f * entityDimensions.height;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        this.oxidise();
        return super.interactAt(player, vec3, interactionHand);
    }

    public void oxidise() {
        WeatherState[] states = WeatherState.values();
        this.weatherState = states[(weatherState.ordinal() + 1) % states.length];
    }

    @NotNull
    public WeatherState getWeatherState() {
        return weatherState;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return new EntityDimensions(0.85f, 1.25f, true);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }
}
