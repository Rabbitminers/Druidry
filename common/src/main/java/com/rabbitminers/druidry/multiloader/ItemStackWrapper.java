package com.rabbitminers.druidry.multiloader;

import net.minecraft.world.item.ItemStack;

public class ItemStackWrapper {
    private ItemStack stack;
    private boolean isDirty;

    public ItemStackWrapper(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        if(!ItemStack.matches(stack, this.stack)) {
            this.stack = stack;
            isDirty = true;
        }
    }

    public boolean isDirty() {
        return isDirty;
    }

    public ItemStackWrapper copy() {
        return new ItemStackWrapper(stack.copy());
    }
}
