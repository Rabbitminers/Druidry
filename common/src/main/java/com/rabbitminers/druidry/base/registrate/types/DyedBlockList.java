package com.rabbitminers.druidry.base.registrate.types;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class DyedBlockList extends EnumBoundBlockList<Block, DyeColor> {
    public DyedBlockList(Class<DyeColor> e, Function<DyeColor, BlockEntry<? extends Block>> filler) {
        super(e, filler);
    }
}
