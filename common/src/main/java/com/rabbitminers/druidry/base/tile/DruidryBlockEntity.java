package com.rabbitminers.druidry.base.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DruidryBlockEntity extends BlockEntity {
    protected int lazyTickOffset;

    public DruidryBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state) {
        super(type, position, state);
    }

    public abstract int getLazyTickRate();

    public void tick() {
        if (lazyTickOffset-- <= 0) {
            lazyTickOffset = this.getLazyTickRate();
            lazyTick();
        }
    }

    public void lazyTick() { }
}
