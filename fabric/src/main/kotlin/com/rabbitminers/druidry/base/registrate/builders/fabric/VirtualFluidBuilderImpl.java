package com.rabbitminers.druidry.base.registrate.builders.fabric;

import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder;
import com.rabbitminers.druidry.base.registrate.builders.VirtualFluidBuilder;
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.fabric.EnvExecutor;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class VirtualFluidBuilderImpl<T extends DruidryFlowingFluid, P> extends VirtualFluidBuilder<T, P> {
    @Nullable
    private NonNullSupplier<FluidVariantAttributeHandler> attributeHandler = null;

    public VirtualFluidBuilderImpl(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
                                   ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory) {
        super(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory);
    }

    @Override
    protected void registerRenderType(DruidryFlowingFluid entry) {
        EnvExecutor.runWhenOn(
            EnvType.CLIENT, () -> () ->
                BlockRenderLayerMap.INSTANCE.putFluids(layer.get(), entry, getSource()
            )
        );
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void registerDefaultRenderer(DruidryFlowingFluid flowing) {
        FluidRenderHandlerRegistry.INSTANCE.register(getSource(), flowing, new SimpleFluidRenderHandler(stillTexture, flowingTexture));
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) -> {
            registry.register(stillTexture);
            registry.register(flowingTexture);
        });
    }

    @Override
    @SuppressWarnings({"unused", "unchecked"})
    public <I extends BucketItem> ItemBuilder<I, DruidryFluidBuilder<T, P>> bucket(NonNullBiFunction<? extends DruidryFlowingFluid,
            Item.Properties, ? extends I> factory) {
        if (this.defaultBucket == Boolean.FALSE) {
            throw new IllegalStateException("Only one call to bucket/noBucket per builder allowed");
        }
        this.defaultBucket = false;
        NonNullSupplier<? extends DruidryFlowingFluid> source = this.source;

        if (source == null) {
            throw new IllegalStateException("Cannot create a bucket before creating a source block");
        }

        return getOwner().<I, DruidryFluidBuilder<T, P>>item(this, bucketName, p ->
                        ((NonNullBiFunction<DruidryFlowingFluid, Item.Properties, ? extends I>) factory)
                                .apply(source.get(), p))
                .properties(p -> p.craftRemainder(Items.BUCKET).stacksTo(1))
                .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation(getOwner().getModid(), "item/" + bucketName)));
    }

    @Override
    public <B extends LiquidBlock> BlockBuilder<B, DruidryFluidBuilder<T, P>> block(NonNullBiFunction<NonNullSupplier<? extends T>,
            BlockBehaviour.Properties, ? extends B> factory) {
        if (this.defaultBlock == Boolean.FALSE) {
            throw new IllegalStateException("Only one call to block/noBlock per builder allowed");
        }
        this.defaultBlock = false;
        NonNullSupplier<T> supplier = asSupplier();
        return getOwner().<B, DruidryFluidBuilder<T, P>>block(this, sourceName, p -> factory.apply(supplier, p))
                .properties(p -> BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable())
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().getBuilder(sourceName)
                        .texture("particle", stillTexture)));
    }

    public DruidryFluidBuilder<T, P> fluidAttributes(NonNullSupplier<FluidVariantAttributeHandler> handler) {
        if (attributeHandler == null) {
            this.attributeHandler = handler;
        }
        return this;
    }
    @Override
    protected @NotNull T createEntry() {
        return fluidFactory.apply(makeProperties());
    }

    @SuppressWarnings("unchecked, rawtypes")
    @Override
    public @NotNull RegistryEntry<T> register() {
        if (this.attributeHandler != null) {
            onRegister(entry -> {
                FluidVariantAttributeHandler handler = attributeHandler.get();
                FluidVariantAttributes.register(entry, handler);
                FluidVariantAttributes.register(getSource(), handler);
            });
        }

        EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> onRegister(this::registerDefaultRenderer));

        if (defaultSource == Boolean.TRUE) {
            source(DruidryFlowingFluid.Source::new);
        }
        if (defaultBlock == Boolean.TRUE) {
            block().register();
        }
        if (defaultBucket == Boolean.TRUE) {
            bucket().register();
        }

        NonNullSupplier<? extends DruidryFlowingFluid> source = this.source;
        if (source != null) {
            getCallback().accept(sourceName, Registry.FLUID_REGISTRY, (DruidryFluidBuilder) this, source);
        } else {
            throw new IllegalStateException("Fluid must have a source version: " + getName());
        }

        return super.register();
    }
}
