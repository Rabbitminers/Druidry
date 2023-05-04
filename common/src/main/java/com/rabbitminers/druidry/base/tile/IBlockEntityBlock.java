package com.rabbitminers.druidry.base.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface IBlockEntityBlock<B extends DruidryBlockEntity> extends EntityBlock {
    Class<B> getBlockEntityClass();

    BlockEntityType<? extends B> getBlockEntityType();

    @Nullable
    @Override
    default <E extends BlockEntity> BlockEntityTicker<E> getTicker(Level level, BlockState blockState,
                                                                   BlockEntityType<E> blockEntityType) {
        if (DruidryBlockEntity.class.isAssignableFrom(getBlockEntityClass()))
            return new DruidryBlockEntityTicker<>();
        return null;
    }

    default Optional<B> getBlockEntityOptionally(BlockGetter world, BlockPos pos) {
        return Optional.ofNullable(getBlockEntity(world, pos));
    }

    @Nullable
    @Override
    default BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return getBlockEntityType().create(pos, state);
    }
    @Nullable
    @SuppressWarnings("unchecked")
    default B getBlockEntity(BlockGetter worldIn, BlockPos pos) {
        BlockEntity blockEntity = worldIn.getBlockEntity(pos);
        Class<B> expectedClass = getBlockEntityClass();

        if (!expectedClass.isInstance(blockEntity))
            return null;

        return (B) blockEntity;
    }
}
