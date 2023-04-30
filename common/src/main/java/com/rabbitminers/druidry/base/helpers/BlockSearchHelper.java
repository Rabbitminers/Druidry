package com.rabbitminers.druidry.base.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockSearchHelper {
    public static Set<BlockPos> getConnectedBlocks(Level world, BlockPos pos, BlockCondition condition) {
        Set<BlockPos> connectedBlocks = new HashSet<>();

        Queue<BlockPos>queue = new LinkedList<>();
        queue.add(pos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            if (!(condition.validate(world, currentPos)))
                continue;
            connectedBlocks.add(currentPos);
            Set<BlockPos> neighbors = getNeighborPositions(currentPos)
                    .filter(neighborPos -> !connectedBlocks.contains(neighborPos) && !queue.contains(neighborPos))
                    .collect(Collectors.toSet());
            queue.addAll(neighbors);
        }

        return connectedBlocks;
    }

    private static Stream<BlockPos> getNeighborPositions(BlockPos pos) {
        return Stream.of(pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below());
    }

    public static Set<BlockPos> getConnectedBlocks(Level world, BlockPos pos) {
        BlockState origin = world.getBlockState(pos);
        return BlockSearchHelper.getConnectedBlocks(world, pos, (level, location) -> {
            BlockState block = level.getBlockState(location);
            return block.getBlock() == origin.getBlock();
        });
    }

    public static Set<BlockPos> getConnectedBlocks(Level world, BlockPos pos, Block block) {
        return BlockSearchHelper.getConnectedBlocks(world, pos, (level, location) -> {
            BlockState foundBlock = level.getBlockState(location);
            return foundBlock.getBlock() == block;
        });
    }

    public static Set<BlockPos> getConnectedBlocks(Level world, BlockPos pos, TagKey<Block>... tags) {
        return BlockSearchHelper.getConnectedBlocks(world, pos, (level, location) -> {
            BlockState foundBlock = level.getBlockState(location);
            return Arrays.stream(tags).anyMatch(foundBlock::is);
        });
    }

    @FunctionalInterface
    public interface BlockCondition {
        public boolean validate(Level world, BlockPos blockPos);
    }
}
