package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class PourableSoupIngredientBuilder extends SoupIngredientBuilder {
    private NonNullSupplier<Item> emptiedItem;

    protected PourableSoupIngredientBuilder(@NotNull MapBoundRegistry<Item, SoupIngredient> registry, Item ingredientItem) {
        super(registry, ingredientItem);
    }

    public PourableSoupIngredientBuilder(@NotNull SoupIngredientBuilder builder) {
        this(builder.registry, builder.entryName);
    }

    public PourableSoupIngredientBuilder(@NotNull SoupIngredientBuilder builder, Item emptieditem) {
        this(builder.registry, builder.entryName);
        this.emptiedItem = () -> emptieditem;
    }

    public PourableSoupIngredientBuilder emptiedItem(Item emptieditem) {
        this.emptiedItem = () -> emptieditem;
        return this;
    }

    @Override
    protected @NotNull SoupIngredient.PourableSoupIngredient createEntry() {
        return new SoupIngredient.PourableSoupIngredient(super.createEntry(), emptiedItem.get());
    }
}
