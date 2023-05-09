package com.rabbitminers.druidry.content.debug;

import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.rabbitminers.druidry.registry.DruidryFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.Collection;
import java.util.HashSet;

@TestOnly
public class DebugItem extends Item {
    Collection<BlockPos> logs = new HashSet<>();
    Object outliner = new Object();

    public DebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
        Fluid fluid = Registry.FLUID.get(DruidryFluids.SOUP.getId());
        System.out.println(fluid);
        System.out.println(fluid instanceof DruidryFlowingFluid);
        System.out.println(DruidryFluids.SOUP);
        return super.useOn(useOnContext);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {

    }
}
