package com.rabbitminers.druidry.content.grove.golems;

import com.rabbitminers.druidry.base.entities.DruidryEntity;
import com.rabbitminers.druidry.base.helpers.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public abstract class GolemEntity extends DruidryEntity {
    protected final GolemSchedule schedule = new GolemSchedule(this);
    protected int hunger = 10;

    public GolemEntity(EntityType<? extends DruidryEntity> entityType, Level level) {
        super(entityType, level);
    }

    public abstract <T extends Goal & IGolemTask> T getCurrentTask();

    public boolean isHungry() {
        return this.hunger <= 0;
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }

    @Override
    public void tick() {
        schedule.tick(this.level);
    }
}
