package com.rabbitminers.druidry.base.registrate.builders;

import com.rabbitminers.druidry.base.registrate.registries.MapBoundRegistry;
import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import com.rabbitminers.druidry.registry.DruidryRegistries;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SoupIngredientBuilder extends AbstractMapBoundBuilder<SoupIngredient, ResourceLocation, MapBoundRegistry<ResourceLocation, SoupIngredient>> {
    protected final NonNullSupplier<Set<SoupIngredient>> synergisticIngredients = HashSet::new;
    protected final NonNullSupplier<Set<SoupIngredient>> conflictingIngredients = HashSet::new;

    protected NonNullSupplier<Double> sweetness, spice, saltiness, sour, bitter;

    public static SoupIngredientBuilder create(ResourceLocation ingredientItem) {
        return new SoupIngredientBuilder(DruidryRegistries.SOUP_INGREDIENT_REGISTRY, ingredientItem);
    }

    protected SoupIngredientBuilder(@NotNull MapBoundRegistry<ResourceLocation, SoupIngredient> registry, ResourceLocation ingredientItem) {
        super(registry, ingredientItem);
        this.sweetness = () -> 0.0d;
        this.spice = () -> 0.0d;
        this.saltiness = () -> 0.0d;
        this.sour = () -> 0.0d;
        this.bitter = () -> 0.0d;
    }

    public SoupIngredientBuilder synergisticIngredient(SoupIngredient... ingredients) {
        Collections.addAll(this.synergisticIngredients.get(), ingredients);
        return this;
    }


    public SoupIngredientBuilder conflictingIngredient(SoupIngredient... ingredients) {
        Collections.addAll(this.conflictingIngredients.get(), ingredients);
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

    public SoupIngredientBuilder sour(double sour) {
        this.sour = () -> sour;
        return this;
    }

    public SoupIngredientBuilder bitter(double bitter) {
        this.bitter = () -> bitter;
        return this;
    }

    public PourableSoupIngredientBuilder pourable() {
        return new PourableSoupIngredientBuilder(this);
    }

    public PourableSoupIngredientBuilder bottled() {
        return new PourableSoupIngredientBuilder(this, Items.GLASS_BOTTLE);
    }

    public PourableSoupIngredientBuilder bucketed() {
        return new PourableSoupIngredientBuilder(this, Items.BUCKET);
    }

    @Override
    public @NotNull SoupIngredient createEntry() {
        return new SoupIngredient(
            super.entryName,
            sweetness.get(),
            spice.get(),
            saltiness.get(),
            sour.get(),
            bitter.get(),
            synergisticIngredients.get(),
            conflictingIngredients.get()
        );
    }
}
