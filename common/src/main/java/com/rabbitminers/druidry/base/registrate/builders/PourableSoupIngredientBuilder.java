package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class PourableSoupIngredientBuilder extends SoupIngredientBuilder {
    private NonNullSupplier<ResourceLocation> emptiedItem;

    protected PourableSoupIngredientBuilder(@NotNull MapBoundRegistry<ResourceLocation, SoupIngredient> registry, ResourceLocation ingredientItem) {
        super(registry, ingredientItem);
    }

    public PourableSoupIngredientBuilder(@NotNull SoupIngredientBuilder builder) {
        this(builder.registry, builder.entryName);
    }

    public PourableSoupIngredientBuilder(@NotNull SoupIngredientBuilder builder, ResourceLocation emptieditem) {
        this(builder.registry, builder.entryName);
        this.emptiedItem = () -> emptieditem;
    }

    public PourableSoupIngredientBuilder(@NotNull SoupIngredientBuilder builder, Item emptieditem) {
        this(builder.registry, builder.entryName);
        this.emptiedItem = () -> Registry.ITEM.getKey(emptieditem);
    }

    public PourableSoupIngredientBuilder emptiedItem(ResourceLocation emptieditem) {
        this.emptiedItem = () -> emptieditem;
        return this;
    }

    public PourableSoupIngredientBuilder emptiedItem(Item emptieditem) {
        this.emptiedItem = () -> Registry.ITEM.getKey(emptieditem);
        return this;
    }

    @Override
    public @NotNull SoupIngredient.PourableSoupIngredient createEntry() {
        return new SoupIngredient.PourableSoupIngredient(super.createEntry(), emptiedItem.get());
    }
}
