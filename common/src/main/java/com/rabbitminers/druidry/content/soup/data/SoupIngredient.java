package com.rabbitminers.druidry.content.soup.data;

import com.google.gson.JsonObject;
import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.helpers.NBTHelper;
import com.rabbitminers.druidry.base.registrate.builders.SoupIngredientBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/*
* Annoyingly cannot be a record as they cannot be inherited from
 */

public class SoupIngredient {
    public final Set<SoupIngredient> synergisticIngredients;
    public final Set<SoupIngredient> conflictingIngredients;

    public final ResourceLocation ingredient;
    public final double sweetness, spice, saltiness, sour, bitter;

    public SoupIngredient(ResourceLocation ingredient, double sweetness, double spice, double saltiness, double sour, double bitter,
                          Set<SoupIngredient> synergisticIngredients, Set<SoupIngredient> conflictingIngredients) {
        this.ingredient = ingredient;
        this.sweetness = sweetness;
        this.spice = spice;
        this.saltiness = saltiness;
        this.sour = sour;
        this.bitter = bitter;

        this.synergisticIngredients = synergisticIngredients;
        this.conflictingIngredients = conflictingIngredients;
    }

    public SoupIngredient(ResourceLocation ingredient, double sweetness, double spice, double saltiness, double sour, double bitter) {
        this(ingredient, sweetness, spice, saltiness, sour, bitter, new HashSet<>(), new HashSet<>());
    }

    /**
     * Adds the given SoupIngredients as synergistic ingredients.
     *
     * @param ingredients the ingredients to add
     * @return this SoupIngredient
     */
    public SoupIngredient addSynergisticIngredients(SoupIngredient... ingredients) {
        Collections.addAll(this.synergisticIngredients, ingredients);
        return this;
    }

    /**
     * Adds the given SoupIngredients as conflicting ingredients.
     *
     * @param ingredients the ingredients to add
     * @return this SoupIngredient
     */
    public SoupIngredient addConflictingIngredients(SoupIngredient... ingredients) {
        Collections.addAll(this.conflictingIngredients, ingredients);
        return this;
    }

    public void serialize(CompoundTag nbt) {
        NBTHelper.writeResourceLocation(nbt, "item", this.ingredient);

        nbt.putDouble("sweetness", this.sweetness);
        nbt.putDouble("spice", this.spice);
        nbt.putDouble("saltiness", this.saltiness);
        nbt.putDouble("sour", this.sour);
        nbt.putDouble("bitter", this.bitter);

        NBTHelper.writeCollection(nbt, "synergisticIngredients", this.synergisticIngredients);
        NBTHelper.writeCollection(nbt, "conflictingIngredients", this.conflictingIngredients);
    }

    public static SoupIngredient read(CompoundTag nbt) {
        ResourceLocation item = NBTHelper.readResourceLocation(nbt, "item");
        SoupIngredientBuilder builder = Druidry.registrate().soupIngredient(item);

        builder.sweetness(nbt.getDouble("sweetness"))
                    .spice(nbt.getDouble("spice"))
                    .saltiness(nbt.getDouble("saltiness"))
                    .sour(nbt.getDouble("sour"))
                    .bitter(nbt.getDouble("bitter"));

        if (nbt.contains("emptiedItem")) {
            ResourceLocation emptyItem = NBTHelper.readResourceLocation(nbt, "emptiedItem");
            builder.pourable().bottled();
        }

        return builder.createEntry();
    }

    /**
     * A subclass of SoupIngredient representing an ingredient that can be emptied,
     * and used up, such as a honey bottle or a milk bucket.
     **/
    public static class PourableSoupIngredient extends SoupIngredient {
        // TODO: STORE ANIMATION PREDICATE
        private ResourceLocation emptiedItem;

        public PourableSoupIngredient(ResourceLocation ingredient, double sweetness, double spice, double saltiness,  double sour, double bitter,
                              Set<SoupIngredient> synergisticIngredients, Set<SoupIngredient> conflictingIngredients, ResourceLocation emptiedItem) {
            super(ingredient, sweetness, spice, saltiness, sour, bitter, synergisticIngredients, conflictingIngredients);
            this.emptiedItem = emptiedItem;
        }

        public PourableSoupIngredient(SoupIngredient ingredient, ResourceLocation emptiedItem) {
            this(ingredient.ingredient, ingredient.sweetness, ingredient.spice, ingredient.saltiness, ingredient.sour,
                ingredient.bitter, ingredient.synergisticIngredients, ingredient.conflictingIngredients, emptiedItem);
        }

        public Optional<Item> getEmptiedItem() {
            return Registry.ITEM.getOptional(emptiedItem);
        }

        /**
         * Get new item stack from empty item
         *
         * @return new ItemStack of the type of the empty item
         */

        public Optional<ItemStack> getEmptiedItemStack() {
            Optional<Item> item = getEmptiedItem();
            return item.map(ItemStack::new);
        }

        public PourableSoupIngredient setEmptiedItem(ResourceLocation emptiedItem) {
            this.emptiedItem = emptiedItem;
            return this;
        }
    }
}
