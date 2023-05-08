package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.fabric.RegistryObject;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class DruidryFluidEntry<T extends DruidryFlowingFluid> extends RegistryEntry<T> {
    private final @Nullable BlockEntry<? extends Block> block;

    public DruidryFluidEntry(AbstractRegistrate<?> owner, RegistryObject<T> delegate) {
        super(owner, delegate);
        BlockEntry<? extends Block> block = null;
        try {
            block = BlockEntry.cast(getSibling(Registry.BLOCK));
        } catch (IllegalArgumentException ignored) {

        }
        this.block = block;
    }

    @Override
    public <R> boolean is(R entry) {
        return get().isSame((Fluid) entry);
    }

    @SuppressWarnings("unchecked")
    public <S extends SimpleFlowableFluid> S getSource() {
        return (S) get().getSource();
    }

    @SuppressWarnings({ "unchecked", "null" })
    public <B extends Block> Optional<B> getBlock() {
        return (Optional<B>) Optional.ofNullable(block).map(RegistryEntry::get);
    }

    @Override
    public @NotNull RegistryEntry<T> filter(Predicate<? super T> predicate) {
        return super.filter(predicate);
    }

    @SuppressWarnings({ "unchecked", "null" })
    public <I extends Item> Optional<I> getBucket() {
        return Optional.ofNullable((I) get().getBucket());
    }

}
