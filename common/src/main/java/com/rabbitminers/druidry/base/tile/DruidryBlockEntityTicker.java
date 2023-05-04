package com.rabbitminers.druidry.base.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class DruidryBlockEntityTicker<B extends BlockEntity> implements BlockEntityTicker<B> {
    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, B blockEntity) {
        if (!blockEntity.hasLevel())
            blockEntity.setLevel(level);
        ((DruidryBlockEntity) blockEntity).tick();
    }
}
