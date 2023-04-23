package com.rabbitminers.druidry.content.soup.data;

import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record SoupIngredient(Item ingredient, double sweetness, double spice, double saltiness,
                             Set<SoupIngredient> synergisticIngredients, Set<SoupIngredient> conflictingIngredients) {
    public SoupIngredient(Item ingredient, double sweetness, double spice, double saltiness) {
        this(ingredient, sweetness, spice, saltiness, new HashSet<>(), new HashSet<>());
    }

    public SoupIngredient addSynergisticIngredient(SoupIngredient ingredient) {
        this.synergisticIngredients.add(ingredient);
        return this;
    }

    public SoupIngredient addSynergisticIngredients(SoupIngredient... ingredients) {
        Collections.addAll(this.synergisticIngredients, ingredients);
        return this;
    }

    public SoupIngredient addConflictingIngredient(SoupIngredient ingredient) {
        this.conflictingIngredients.add(ingredient);
        return this;
    }

    public SoupIngredient addConflictingIngredients(SoupIngredient... ingredients) {
        Collections.addAll(this.conflictingIngredients, ingredients);
        return this;
    }
}
