/*
 * Copyright 2015-2017, mezz
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/mezz/JustEnoughItems/blob/1.12/LICENSE.txt
 */

package robmart.rpgmode.common.recipe.brewing;

import com.google.common.base.Objects;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mezz
 */
public class BrewingRecipeWrapper {
    private final ItemStack ingredient;
    private final ItemStack potionInput;
    private final ItemStack potionOutput;

    public BrewingRecipeWrapper(ItemStack ingredient, ItemStack potionInput, ItemStack potionOutput) {
        this.ingredient = ingredient;
        this.potionInput = potionInput;
        this.potionOutput = potionOutput;
    }

    /**
     * Checks whether the input or ingredient is a vanilla potion and should be converted
     *
     * @return whether the potion needs converting or not
     *
     * @author Robmart
     */
    public boolean needConverting() {
        return BrewingHelper.isItemPotion(getPotionInput().getItem()) ||
               BrewingHelper.isItemPotion(getIngredient().getItem());
    }

    public ItemStack getPotionInput() {
        return potionInput;
    }

    public ItemStack getIngredient() {
        return ingredient;
    }

    /**
     * Converts the recipe to ones with override potion
     *
     * @return the converted potions
     *
     * @author Robmart
     */
    public List<BrewingRecipeWrapper> convertPotionType() {
        List<BrewingRecipeWrapper> newRecipes = new ArrayList<>();
        ItemStack newIngredient;
        ItemStack newInput;

        if (BrewingHelper.isItemPotion(getIngredient().getItem()) &&
            !BrewingHelper.isItemPotion(getPotionInput().getItem())) {
            newIngredient = BrewingHelper.convertPotionStack(getIngredient());
            newRecipes.add(new BrewingRecipeWrapper(newIngredient, getPotionInput(), getPotionOutput()));
        }
        else if (BrewingHelper.isItemPotion(getPotionInput().getItem()) &&
                 !BrewingHelper.isItemPotion(getIngredient().getItem())) {
            newInput = BrewingHelper.convertPotionStack(getPotionInput());
            newRecipes.add(new BrewingRecipeWrapper(getIngredient(), newInput, getPotionOutput()));
        }
        else if (BrewingHelper.isItemPotion(getIngredient().getItem()) &&
                 BrewingHelper.isItemPotion(getPotionInput().getItem())) {
            newIngredient = BrewingHelper.convertPotionStack(getIngredient());
            newRecipes.add(new BrewingRecipeWrapper(newIngredient, getPotionInput(), getPotionOutput()));

            newInput = BrewingHelper.convertPotionStack(getPotionInput());
            newRecipes.add(new BrewingRecipeWrapper(getIngredient(), newInput, getPotionOutput()));

            newRecipes.add(new BrewingRecipeWrapper(newIngredient, newInput, getPotionOutput()));
        }

        return newRecipes;
    }

    public ItemStack getPotionOutput() {
        return potionOutput;
    }

    @Override
    public int hashCode() {
        PotionType typeIn = PotionUtils.getPotionFromItem(getPotionInput());
        PotionType typeOut = PotionUtils.getPotionFromItem(getPotionOutput());
        return Objects.hashCode(getPotionInput().getItem(), ForgeRegistries.POTION_TYPES.getKey(typeIn),
                                getPotionOutput().getItem(), ForgeRegistries.POTION_TYPES.getKey(typeOut),
                                getIngredient().getItem(), getIngredient().getMetadata());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BrewingRecipeWrapper)) {
            return false;
        }
        BrewingRecipeWrapper other = (BrewingRecipeWrapper) obj;

        if (!arePotionsEqual(other.getPotionInput(), getPotionInput())) {
            return false;
        }

        if (!arePotionsEqual(other.getPotionOutput(), getPotionOutput())) {
            return false;
        }

        return ItemStack.areItemStacksEqual(getIngredient(), other.getIngredient());
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean arePotionsEqual(ItemStack potion1, ItemStack potion2) {
        if (potion1.getItem() != potion2.getItem()) {
            return false;
        }
        PotionType type1 = PotionUtils.getPotionFromItem(potion1);
        PotionType type2 = PotionUtils.getPotionFromItem(potion2);
        ResourceLocation key1 = ForgeRegistries.POTION_TYPES.getKey(type1);
        ResourceLocation key2 = ForgeRegistries.POTION_TYPES.getKey(type2);
        return java.util.Objects.equals(key1, key2);
    }

    @Override
    public String toString() {
        PotionType inputType = PotionUtils.getPotionFromItem(getPotionInput());
        PotionType outputType = PotionUtils.getPotionFromItem(getPotionOutput());
        if (!(getIngredient().getItem() instanceof ItemPotion))
            return getIngredient() + " + [" + getPotionInput().getItem() + " " + inputType.getNamePrefixed("") +
                   "] = [" +
                   getPotionOutput() + " " + outputType.getNamePrefixed("") + "]";

        PotionType ingredientType = PotionUtils.getPotionFromItem(getIngredient());
        return "[" + getIngredient().getItem() + " " + ingredientType.getNamePrefixed("") + "]" + " + " +
               "[" + getPotionInput().getItem() + " " + inputType.getNamePrefixed("") + "] = " +
               "[" + getPotionOutput() + " " + outputType.getNamePrefixed("") + "]";
    }
}
