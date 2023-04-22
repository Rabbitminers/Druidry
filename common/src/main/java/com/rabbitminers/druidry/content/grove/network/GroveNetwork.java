package com.rabbitminers.druidry.content.grove.network;

import com.rabbitminers.druidry.content.grove.golems.IGolemEntity;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class GroveNetwork {
    public Long id;
    public boolean initialised;
    public BlockPos heartLocation;
    protected Set<IGolemEntity> golems;

    private float purity;

    protected GroveNetwork() {
        golems = new HashSet<>();
    }

    public void initialseFromHeart() {

    }
}
