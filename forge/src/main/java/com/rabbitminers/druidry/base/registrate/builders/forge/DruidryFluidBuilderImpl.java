package com.rabbitminers.druidry.base.registrate.builders.forge;

import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder;
import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidEntry;
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.*;
import net.minecraft.Util;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DruidryFluidBuilderImpl<T extends DruidryFlowingFluid, P> extends DruidryFluidBuilder<T, P> {
    @Nullable
    private final NonNullSupplier<FluidType> fluidType;
    private final boolean registerType;
    private NonNullConsumer<FluidType.Properties> typeProperties = $ -> {};

    public DruidryFluidBuilderImpl(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
                                   ResourceLocation flowingTexture, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory) {
        this(owner, parent, name, callback, stillTexture, flowingTexture, DruidryFluidBuilderImpl::defaultFluidType, fluidFactory);
    }

    public DruidryFluidBuilderImpl(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
                                   ResourceLocation flowingTexture, FluidTypeFactory typeFactory, NonNullFunction<DruidryFlowingFluid.Properties, T> fluidFactory) {
        super(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory);
        this.fluidType = NonNullSupplier.lazy(() -> typeFactory.create(makeTypeProperties(), this.stillTexture, this.flowingTexture));
        this.registerType = true;
    }

    @Override
    protected void registerRenderType(T entry) {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () ->
                OneTimeEventReceiver.addModListener(FMLClientSetupEvent.class, $ -> {
                    if (this.layer != null) {
                        RenderType layer = this.layer.get();
                        ItemBlockRenderTypes.setRenderLayer(entry, layer);
                        ItemBlockRenderTypes.setRenderLayer(getSource(), layer);
                    }
                }
            )
        );
    }

    public DruidryFluidBuilder<T, P> defaultLang() {
        return lang(f -> f.getFluidType().getDescriptionId(), RegistrateLangProvider.toEnglishName(sourceName));
    }

    @Override
    public DruidryFluidBuilder<T, P> lang(String name) {
        return lang(f -> f.getFluidType().getDescriptionId(), name);
    }

    @Override
    public <B extends LiquidBlock> BlockBuilder<B, DruidryFluidBuilder<T, P>> block(NonNullBiFunction<NonNullSupplier<? extends T>, BlockBehaviour.Properties, ? extends B> factory) {
        if (this.defaultBlock == Boolean.FALSE) {
            throw new IllegalStateException("Only one call to block/noBlock per builder allowed");
        }
        this.defaultBlock = false;
        NonNullSupplier<T> supplier = asSupplier();
        return getOwner().<B, DruidryFluidBuilder<T, P>>block(this, sourceName, p -> factory.apply(supplier, p))
                .properties(p -> BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable())
                .properties(p -> p.lightLevel(blockState -> fluidType.get().getLightLevel()))
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().getBuilder(sourceName)
                        .texture("particle", stillTexture)));
    }

    @Override
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
    protected void registerDefaultRenderer(T flowing) {

    }

    public DruidryFluidBuilder<T, P> properties(NonNullConsumer<FluidType.Properties> cons) {
        typeProperties = typeProperties.andThen(cons);
        return this;
    }

    @Override
    protected @NonnullType @NotNull T createEntry() {
        return fluidFactory.apply(makeProperties());
    }

    private FluidType.Properties makeTypeProperties() {
        FluidType.Properties properties = FluidType.Properties.create();
        RegistryEntry<Block> block = getOwner().getOptional(sourceName, ForgeRegistries.Keys.BLOCKS);
        this.typeProperties.accept(properties);

        if (block.isPresent()) {
            properties.descriptionId(block.get().getDescriptionId());
            setData(ProviderType.LANG, NonNullBiConsumer.noop());
        } else {
            properties.descriptionId(Util.makeDescriptionId("fluid", new ResourceLocation(getOwner().getModid(), sourceName)));
        }

        return properties;
    }

    @Override
    public @NotNull RegistryEntry<T> register() {
        if (this.fluidType != null) {
            if (this.registerType) {
                getOwner().simple(this, this.sourceName, ForgeRegistries.Keys.FLUID_TYPES, this.fluidType);
            }
        } else {
            throw new IllegalStateException("Fluid must have a type: " + getName());
        }

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
            getCallback().accept(sourceName, ForgeRegistries.Keys.FLUIDS, (DruidryFluidBuilder) this, source);
        } else {
            throw new IllegalStateException("Fluid must have a source version: " + getName());
        }

        return super.register();
    }

    private static FluidType defaultFluidType(FluidType.Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        return new FluidType(properties) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return stillTexture;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return flowingTexture;
                    }
                });
            }
        };
    }
}
