package com.rabbitminers.druidry.content.grove.golems.copper.ai.goal;

import com.rabbitminers.druidry.content.grove.golems.copper.CopperGolemEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class MoochAboutGoal extends RandomStrollGoal {
    public MoochAboutGoal(CopperGolemEntity copperGolemEntity) {
        super(copperGolemEntity, 0.35f);
    }
}
