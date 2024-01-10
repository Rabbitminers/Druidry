package com.rabbitminers.druidry.content.grove.golems.ai.goal;

//import com.rabbitminers.druidry.content.grove.golems.GolemEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.Component;
//
//import java.util.EnumSet;
//
///**
// * <p>
// * Abstract as golem naviagation should only be handled in GroveNodes aside from some use cases with
// * their own goals
// * <p>
// * Essentially just a port of {@link net.minecraft.world.entity.ai.goal.MoveToBlockGoal}
// */
//public abstract class GolemNavigationGoal extends GolemGoal {
//    private final float minimumDistance;
//    private final BlockPos targetLocation;
//
//    private boolean arrived = false;
//    private int ticksTaken = 0;
//
//    public GolemNavigationGoal(GolemEntity golem, BlockPos pos) {
//        this(golem, pos, 1.0f);
//    }
//
//    public GolemNavigationGoal(GolemEntity golem, BlockPos pos, float minimumDistance) {
//        super(golem);
//        this.minimumDistance = minimumDistance;
//        this.targetLocation = pos;
//        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return !this.arrived;
//    }
//
//    public BlockPos getTarget() {
//        return this.targetLocation;
//    }
//
//    @Override
//    public void start() {
//        this.moveGolemToBlock();
//        this.ticksTaken = 0;
//    }
//
//    /**
//     * Taken from {@link net.minecraft.world.entity.ai.goal.MoveToBlockGoal#moveMobToBlock}
//     *
//     */
//    protected void moveGolemToBlock() {
//        this.golem.getNavigation().moveTo(
//            this.targetLocation.getX() + 0.5d,
//            this.targetLocation.getY() + 1,
//            this.targetLocation.getZ() + 0.5d,
//            this.golem.getSpeed()
//        );
//    }
//
//    @Override
//    public void tick() {
//        if (!targetLocation.closerToCenterThan(this.golem.position(), this.minimumDistance)) {
//            this.arrived = false;
//            ++this.ticksTaken;
//        } else {
//            this.arrived = true;
//        }
//    }
//
//    @Override
//    public Component getName() {
//        return null;
//    }
//}
