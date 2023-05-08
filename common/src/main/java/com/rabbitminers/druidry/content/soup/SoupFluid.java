package com.rabbitminers.druidry.content.soup;

import com.rabbitminers.druidry.content.soup.data.SoupIngredient;
import com.rabbitminers.druidry.multiloader.fluid.FluidHandler;
import com.rabbitminers.druidry.multiloader.fluid.IFluidStack;
import com.rabbitminers.druidry.multiloader.fluid.VirtualFluid;
import com.rabbitminers.druidry.registry.DruidryFluids;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.util.Arrays;

public class SoupFluid extends VirtualFluid {
    public static final String INGREDIENTS_KEY = "Ingredients";
    public static final String BASE_FLUID_KEY = "BaseFluid";

    public SoupFluid(Properties properties) {
        super(properties);
    }

    public static IFluidStack create(IFluidStack base) {
        CompoundTag nbt = SoupFluid.createSoupData(base.getFluid());
        return FluidHandler.newFluidHolder(DruidryFluids.SOUP.get(), base.getFluidAmount(), nbt);
    }

    public static IFluidStack addIngredients(IFluidStack stack, SoupIngredient... ingredients) {
        CompoundTag nbt = stack.getCompound();
        ListTag serialized = SoupFluid.getIngredients(stack);
        Arrays.stream(ingredients)
                .map(ingredient -> ingredient.location().toString())
                .map(StringTag::valueOf)
                .forEach(serialized::add);
        nbt.put(INGREDIENTS_KEY, serialized);
        stack.setCompound(nbt);
        return stack;
    }

    public static ListTag getIngredients(IFluidStack stack) {
        CompoundTag nbt = stack.getCompound();
        if (!(nbt.contains(INGREDIENTS_KEY, ListTag.TAG_LIST)))
            return (ListTag) nbt.put(INGREDIENTS_KEY, new ListTag());
        return nbt.getList(INGREDIENTS_KEY, ListTag.TAG_LIST);
    }

    private static CompoundTag createSoupData(Fluid base) {
        CompoundTag nbt = new CompoundTag();
        ResourceLocation fluidName = Registry.FLUID.getKey(base);
        nbt.put(INGREDIENTS_KEY, new ListTag());
        nbt.putString(BASE_FLUID_KEY, fluidName.toString());
        return nbt;
    }
}
