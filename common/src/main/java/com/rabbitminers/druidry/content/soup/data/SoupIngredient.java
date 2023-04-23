package com.rabbitminers.druidry.content.soup.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
* Annoyingly cannot be a record as they cannot be inherited from
 */

public class SoupIngredient {
    public final Set<SoupIngredient> synergisticIngredients;
    public final Set<SoupIngredient> conflictingIngredients;

    private final Item ingredient;
    public final double sweetness, spice, saltiness, sour, bitter;

    public SoupIngredient(Item ingredient, double sweetness, double spice, double saltiness, double sour, double bitter,
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

    public SoupIngredient(Item ingredient, double sweetness, double spice, double saltiness, double sour, double bitter) {
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

    /**
     * A subclass of SoupIngredient representing an ingredient that can be emptied,
     * and used up, such as a honey bottle or a milk bucket.
     **/
    public static class PourableSoupIngredient extends SoupIngredient {
        // TODO: STORE ANIMATION PREDICATE
        private Item emptiedItem;

        public PourableSoupIngredient(Item ingredient, double sweetness, double spice, double saltiness,  double sour, double bitter,
                              Set<SoupIngredient> synergisticIngredients, Set<SoupIngredient> conflictingIngredients, Item emptiedItem) {
            super(ingredient, sweetness, spice, saltiness, sour, bitter, synergisticIngredients, conflictingIngredients);
            this.emptiedItem = emptiedItem;
        }

        public PourableSoupIngredient(SoupIngredient ingredient, Item emptiedItem) {
            this(ingredient.ingredient, ingredient.sweetness, ingredient.spice, ingredient.saltiness, ingredient.sour,
                ingredient.bitter, ingredient.synergisticIngredients, ingredient.conflictingIngredients, emptiedItem);
        }

        public Item getEmptiedItem() {
            return this.emptiedItem;
        }

        /**
         * Get new item stack from empty item
         *
         * @return new ItemStack of the type of the empty item
         */

        public ItemStack getEmptiedItemStack() {
            return new ItemStack(emptiedItem);
        }

        public PourableSoupIngredient setEmptiedItem(Item emptiedItem) {
            this.emptiedItem = emptiedItem;
            return this;
        }
    }
}
