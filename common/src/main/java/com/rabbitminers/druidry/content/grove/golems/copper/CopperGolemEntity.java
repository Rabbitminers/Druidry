package com.rabbitminers.druidry.content.grove.golems.copper;

import com.rabbitminers.druidry.content.grove.golems.copper.ai.goal.MoochAboutGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import static net.minecraft.world.level.block.WeatheringCopper.*;

public class CopperGolemEntity extends PathfinderMob {
    private static final float OXIDATION_CHANCE = 0.05688889F;
    private static final Random random = new Random();
    private WeatherState weatherState = WeatherState.UNAFFECTED;
    private boolean waxed = false;

    public CopperGolemEntity(EntityType<? extends CopperGolemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions entityDimensions) {
        return 0.55f * entityDimensions.height;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoochAboutGoal(this));
        super.registerGoals();
    }

    @Override
    public @NotNull InteractionResult interactAt(Player player, @NotNull Vec3 vec3,
                                                 @NotNull InteractionHand interactionHand) {
        ItemStack heldItem = player.getItemInHand(interactionHand);

        if (heldItem.is(Items.HONEYCOMB) && !waxed) {
            heldItem.shrink(1);
            this.waxed = true;
            return InteractionResult.CONSUME;
        }

        if (heldItem.getItem() instanceof AxeItem) {
            if (!waxed) this.clean();
            this.waxed = false;
            return InteractionResult.CONSUME;
        }

        return super.interactAt(player, vec3, interactionHand);
    }

    @Override
    public void tick() {
        super.tick();

        if (!waxed && random.nextFloat() < OXIDATION_CHANCE) {
            this.oxidize();
        }
    }

    public void oxidize() {
        int nextOrdinal = weatherState.ordinal() + 1;
        WeatherState[] values = WeatherState.values();
        if (nextOrdinal <= values.length - 1)
            this.weatherState = values[nextOrdinal];
    }

    public void clean() {
        int ordinal = weatherState.ordinal() - 1;
        weatherState = WeatherState.values()[Math.max(ordinal, 0)];
    }

    @NotNull
    public WeatherState getWeatherState() {
        return weatherState;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return new EntityDimensions(0.85f, 1.25f, true);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }
}
