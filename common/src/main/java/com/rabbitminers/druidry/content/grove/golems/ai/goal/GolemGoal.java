package com.rabbitminers.druidry.content.grove.golems.ai.goal;

import com.rabbitminers.druidry.content.grove.golems.GolemEntity;
import com.rabbitminers.druidry.content.grove.golems.GolemSchedule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class GolemGoal extends Goal {
    protected final GolemEntity golem;

    public GolemGoal(GolemEntity golem) {
        this.golem = golem;
    }

    @Override
    public boolean canUse() {
        GolemSchedule schedule = this.golem.getSchedule();
        return schedule.getCurrentGoal() == this;
    }

    public abstract Component getName();

    @Override
    public void stop() {
        this.golem.decreaseHunger();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
