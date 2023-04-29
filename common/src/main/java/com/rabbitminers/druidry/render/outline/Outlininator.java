package com.rabbitminers.druidry.render.outline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;

import com.rabbitminers.druidry.render.base.TriBufferSource;
import com.rabbitminers.druidry.render.outline.Outline.OutlineParams;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

public class Outlininator {
    private final Map<Object, OutlineEntry> outlines = Collections.synchronizedMap(new HashMap<>());
    private final Map<Object, OutlineEntry> outlinesView = Collections.unmodifiableMap(outlines);

    public OutlineParams showCluster(Object slot, Iterable<BlockPos> selection) {
        BlockClusterOutline outline = new BlockClusterOutline(selection);
        addOutline(slot, outline);
        return outline.getParams();
    }

    public OutlineParams showAABB(Object slot, AABB bb, int ttl) {
        createAABBOutlineIfMissing(slot, bb);
        ChasingAABBOutline outline = getAndRefreshAABB(slot, ttl);
        outline.prevBB = outline.targetBB = outline.bb = bb;
        return outline.getParams();
    }

    public OutlineParams showAABB(Object slot, AABB bb) {
        createAABBOutlineIfMissing(slot, bb);
        ChasingAABBOutline outline = getAndRefreshAABB(slot);
        outline.prevBB = outline.targetBB = outline.bb = bb;
        return outline.getParams();
    }

    public OutlineParams chaseAABB(Object slot, AABB bb) {
        createAABBOutlineIfMissing(slot, bb);
        ChasingAABBOutline outline = getAndRefreshAABB(slot);
        outline.targetBB = bb;
        return outline.getParams();
    }

    private void createAABBOutlineIfMissing(Object slot, AABB bb) {
        if (!outlines.containsKey(slot) || !(outlines.get(slot).outline instanceof AABBOutline)) {
            ChasingAABBOutline outline = new ChasingAABBOutline(bb);
            outlines.put(slot, new OutlineEntry(outline));
        }
    }

    private ChasingAABBOutline getAndRefreshAABB(Object slot) {
        OutlineEntry entry = outlines.get(slot);
        entry.ticksTillRemoval = 1;
        return (ChasingAABBOutline) entry.getOutline();
    }

    private ChasingAABBOutline getAndRefreshAABB(Object slot, int ttl) {
        OutlineEntry entry = outlines.get(slot);
        entry.ticksTillRemoval = ttl;
        return (ChasingAABBOutline) entry.getOutline();
    }

    public void keep(Object slot) {
        if (outlines.containsKey(slot))
            outlines.get(slot).ticksTillRemoval = 1;
    }

    public void remove(Object slot) {
        outlines.remove(slot);
    }

    public Optional<OutlineParams> edit(Object slot) {
        keep(slot);
        if (outlines.containsKey(slot))
            return Optional.of(outlines.get(slot)
                    .getOutline()
                    .getParams());
        return Optional.empty();
    }

    public Map<Object, OutlineEntry> getOutlines() {
        return outlinesView;
    }

    private void addOutline(Object slot, Outline outline) {
        outlines.put(slot, new OutlineEntry(outline));
    }


    public void tickOutlines() {
        Iterator<OutlineEntry> iterator = outlines.values()
                .iterator();
        while (iterator.hasNext()) {
            OutlineEntry entry = iterator.next();
            entry.tick();
            if (!entry.isAlive())
                iterator.remove();
        }
    }

    public void renderOutlines(PoseStack ms, TriBufferSource buffer, float pt) {
        outlines.forEach((key, entry) -> {
            Outline outline = entry.getOutline();
            OutlineParams params = outline.getParams();
            params.alpha = 1;
            if (entry.isFading()) {
                int prevTicks = entry.ticksTillRemoval + 1;
                float fadeticks = OutlineEntry.FADE_TICKS;
                float lastAlpha = prevTicks >= 0 ? 1 : 1 + (prevTicks / fadeticks);
                float currentAlpha = 1 + (entry.ticksTillRemoval / fadeticks);
                float alpha = Mth.lerp(pt, lastAlpha, currentAlpha);

                params.alpha = alpha * alpha * alpha;
                if (params.alpha < 1 / 8f)
                    return;
            }
            outline.render(ms, buffer, pt);
        });
    }

    public static class OutlineEntry {
        public static final int FADE_TICKS = 8;

        private final Outline outline;
        private int ticksTillRemoval = 1;

        public OutlineEntry(Outline outline) {
            this.outline = outline;
        }

        public Outline getOutline() {
            return outline;
        }

        public int getTicksTillRemoval() {
            return ticksTillRemoval;
        }

        public boolean isAlive() {
            return ticksTillRemoval >= -FADE_TICKS;
        }

        public boolean isFading() {
            return ticksTillRemoval < 0;
        }

        public void tick() {
            ticksTillRemoval--;
            outline.tick();
        }
    }

}
