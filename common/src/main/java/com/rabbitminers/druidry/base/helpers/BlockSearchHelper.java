package com.rabbitminers.druidry.base.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockSearchHelper {
    public static Set<BlockPos> getConnectedBlocks(Level world, BlockPos pos, Block targetBlock) {
        Set<BlockPos> connectedBlocks = new HashSet<>();

        Queue<BlockPos>queue = new LinkedList<>();
        queue.add(pos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            if (!world.getBlockState(currentPos).getBlock().equals(targetBlock)) {
                continue;
            }

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

}
