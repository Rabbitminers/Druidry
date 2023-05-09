package com.rabbitminers.druidry.content.soup.bowl;

import com.rabbitminers.druidry.multiloader.ItemStackWrapper;
import com.rabbitminers.druidry.multiloader.fluid.FluidHandler;
import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.ItemFluidHandler;
import com.rabbitminers.druidry.registry.DruidryFluids;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class SoupBlockItem extends Item {
    public static final long DRINK_AMOUNT = FluidHandler.getBucketAmount() / 2;

    public SoupBlockItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
        // TODO: fill when interacting with cauldron
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!(itemStack.getItem() instanceof SoupBlockItem))
            return InteractionResultHolder.fail(itemStack);
        if (this.getFluidAmount(itemStack) > DRINK_AMOUNT)
            return super.use(level, player, interactionHand);
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!(itemStack.getItem() instanceof SoupBlockItem soup) || level.isClientSide)
            return itemStack;

        ItemStackWrapper itemStackWrapper = new ItemStackWrapper(itemStack);

        ItemFluidHandler handler = soup.getFluidHandler(itemStack);
        IFluidStack drankFluid =
                FluidHandler.newFluidHolder(DruidryFluids.SOUP.get(), FluidHandler.getBlockAmount(), null);
        handler.extractFluid(itemStackWrapper, drankFluid, false);
        return itemStackWrapper.getStack();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 32; // Standard for food
    }

    public ItemFluidHandler getFluidHandler(ItemStack stack) {
        return FluidHandler.getItemFluidManager(stack);
    }

    public long getFluidAmount(ItemStack stack) {
        ItemFluidHandler handler = this.getFluidHandler(stack);
        return handler.getTankCapacity(0);
    }

    @Override
    public boolean isEdible() {
        return true;
    }
}
