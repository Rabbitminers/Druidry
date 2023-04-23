package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import com.rabbitminers.druidry.registry.DruidryRegistries;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SoupIngredientBuilder extends AbstractMapBoundBuilder<SoupIngredient, Item, MapBoundRegistry<Item, SoupIngredient>> {
    private final NonNullSupplier<Set<SoupIngredient>> synergisticIngredients = HashSet::new;
    private final NonNullSupplier<Set<SoupIngredient>> conflictingIngredients = HashSet::new;

    private NonNullSupplier<Double> sweetness;
    private NonNullSupplier<Double> spice;
    private NonNullSupplier<Double> saltiness;

    public static SoupIngredientBuilder create(Item ingredientItem) {
        return new SoupIngredientBuilder(DruidryRegistries.SOUP_INGREDIENT_REGISTRY, ingredientItem);
    }

    protected  SoupIngredientBuilder(@NotNull MapBoundRegistry<Item, SoupIngredient> registry, Item ingredientItem) {
        super(registry, ingredientItem);
        this.sweetness = () -> 0.0d;
        this.spice = () -> 0.0d;
        this.saltiness = () -> 0.0d;
    }

    public SoupIngredientBuilder synergisticIngredient(SoupIngredient... ingredients) {
        Collections.addAll(this.synergisticIngredients.get(), ingredients);
        return this;
    }

    public SoupIngredientBuilder synergisticIngredient(SoupIngredient ingredient) {
        this.synergisticIngredient(ingredient);
        return this;
    }

    public SoupIngredientBuilder conflictingIngredient(SoupIngredient... ingredients) {
        Collections.addAll(this.conflictingIngredients.get(), ingredients);
        return this;
    }

    public SoupIngredientBuilder conflictingIngredient(SoupIngredient ingredient) {
        this.conflictingIngredient(ingredient);
        return this;
    }

    public SoupIngredientBuilder sweetness(double sweetness) {
        this.sweetness = () -> sweetness;
        return this;
    }

    public SoupIngredientBuilder spice(double spice) {
        this.spice = () -> spice;
        return this;
    }

    public SoupIngredientBuilder saltiness(double saltiness) {
        this.saltiness = () -> saltiness;
        return this;
    }

    @Override
    protected @NotNull SoupIngredient createEntry() {
        return new SoupIngredient(
            super.entryName,
            sweetness.get(),
            spice.get(),
            saltiness.get(),
            synergisticIngredients.get(),
            conflictingIngredients.get()
        );
    }
}
