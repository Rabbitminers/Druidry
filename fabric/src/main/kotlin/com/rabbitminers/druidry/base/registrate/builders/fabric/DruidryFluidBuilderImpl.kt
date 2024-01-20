package com.rabbitminers.druidry.base.registrate.builders.fabric

import com.rabbitminers.druidry.base.registrate.builders.DruidryFluidBuilder
import com.rabbitminers.druidry.multiloader.fluid.DruidryFlowingFluid
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.fabric.EnvExecutor
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.providers.RegistrateItemModelProvider
import com.tterrag.registrate.util.entry.RegistryEntry
import com.tterrag.registrate.util.nullness.NonNullBiFunction
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import java.lang.Boolean
import kotlin.IllegalStateException
import kotlin.String
import kotlin.Suppress
import kotlin.check

@SuppressWarnings("UnstableApiUsage")
class DruidryFluidBuilderImpl<T: DruidryFlowingFluid, P>
(owner: AbstractRegistrate<*>?, parent: P, name: String?, callback: BuilderCallback?, stillTexture: ResourceLocation?,
 flowingTexture: ResourceLocation?, fluidFactory: NonNullFunction<DruidryFlowingFluid.Properties?, T>?):
        DruidryFluidBuilder<T, P>(owner, parent, name, callback, stillTexture, flowingTexture, fluidFactory) {
    private var attributeHandler: NonNullSupplier<FluidVariantAttributeHandler>? = null

    override fun registerRenderType(entry: T) {
        EnvExecutor.runWhenOn(
                EnvType.CLIENT
        ) {
            Runnable {
                BlockRenderLayerMap.INSTANCE.putFluids(layer!!.get(), entry, getSource()
                )
            }
        }
    }

    @Environment(EnvType.CLIENT)
    override fun registerDefaultRenderer(flowing: T) {
        FluidRenderHandlerRegistry.INSTANCE.register(getSource(), flowing, SimpleFluidRenderHandler(stillTexture, flowingTexture))
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(ClientSpriteRegistryCallback { atlas: TextureAtlas?, registry: ClientSpriteRegistryCallback.Registry ->
            registry.register(stillTexture)
            registry.register(flowingTexture)
        })
    }

    @Suppress("unused")
    override fun <I : BucketItem?> bucket(factory: NonNullBiFunction<out DruidryFlowingFluid?, Item.Properties?, out I>): ItemBuilder<I, DruidryFluidBuilder<T, P>> {
        check(this.defaultBucket !== Boolean.FALSE) { "Only one call to bucket/noBucket per builder allowed" }
        this.defaultBucket = false
        val source = this.source
                ?: throw IllegalStateException("Cannot create a bucket before creating a source block")

        return owner.item<I, DruidryFluidBuilder<T, P>>(this, bucketName) { p: Item.Properties? ->
            (factory as NonNullBiFunction<DruidryFlowingFluid?, Item.Properties?, out I>)
                    .apply(source.get(), p)
        }
                .properties { p: Item.Properties -> p.craftRemainder(Items.BUCKET).stacksTo(1) }
                .model { ctx: DataGenContext<Item?, I>, prov: RegistrateItemModelProvider -> prov.generated({ ctx.entry }, ResourceLocation(owner.modid, "item/$bucketName")) }
    }

    override fun <B : LiquidBlock?> block(factory: NonNullBiFunction<NonNullSupplier<out T>?, BlockBehaviour.Properties?, out B>): BlockBuilder<B, DruidryFluidBuilder<T, P>> {
        check(this.defaultBlock !== Boolean.FALSE) { "Only one call to block/noBlock per builder allowed" }
        this.defaultBlock = false
        val supplier = asSupplier()
        return owner.block<B, DruidryFluidBuilder<T, P>>(this, sourceName) { p: BlockBehaviour.Properties? -> factory.apply(supplier, p) }
                .properties { p: BlockBehaviour.Properties? -> BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable() }
                .blockstate { ctx: DataGenContext<Block?, B>, prov: RegistrateBlockstateProvider ->
                    prov.simpleBlock(ctx.entry, prov.models().getBuilder(sourceName)
                            .texture("particle", stillTexture))
                }
    }

    fun fluidAttributes(handler: NonNullSupplier<FluidVariantAttributeHandler>?): DruidryFluidBuilder<T, P> {
        if (attributeHandler == null) {
            this.attributeHandler = handler
        }
        return this
    }

    override fun createEntry(): T {
        return fluidFactory.apply(makeProperties())
    }

    override fun register(): RegistryEntry<T> {
        if (this.attributeHandler != null) {
            onRegister { entry: T ->
                val handler = attributeHandler!!.get()
                FluidVariantAttributes.register(entry, handler)
                FluidVariantAttributes.register(getSource(), handler)
            }
        }

        EnvExecutor.runWhenOn(EnvType.CLIENT) { Runnable { onRegister { flowing: T -> this.registerDefaultRenderer(flowing) } } }

        if (defaultSource === Boolean.TRUE) {
            source { properties: DruidryFlowingFluid.Properties? -> DruidryFlowingFluid.Source(properties) }
        }
        if (defaultBlock === Boolean.TRUE) {
            block().register()
        }
        if (defaultBucket === Boolean.TRUE) {
            bucket().register()
        }

        val source: NonNullSupplier<out DruidryFlowingFluid>? = this.source
        if (source != null) {
            callback.accept(sourceName, Registry.FLUID_REGISTRY, this as DruidryFluidBuilder<*, *>, source)
        } else {
            throw IllegalStateException("Fluid must have a source version: $name")
        }

        return super.register() as RegistryEntry<T>
    }
}