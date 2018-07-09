/*
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2018 Robmart
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package robmart.rpgmode.common.helper;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import robmart.rpgmode.common.RPGMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Robmart
 */
public class RecipeHelper {

    /**
     * Remove a crafting recipe
     * @param resultItem - the recipe's output Item
     * @param stackSize - the recipe's output stack size
     * @param meta - the recipe's output metadata
     */
    public static void RemoveRecipe(Item resultItem, int stackSize, int meta) {
        RemoveRecipe(new ItemStack(resultItem, stackSize, meta));
    }

    /**
     * Remove a crafting recipe
     * @param resultStack - the output result stack, including metadata and size
     */
    public static void RemoveRecipe(ItemStack resultStack) {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());
        ItemStack recipeResult;

        for (IRecipe recipe : recipes) {
            ItemStack output = recipe.getRecipeOutput();
            recipeResult = recipe.getRecipeOutput();
            if (ItemStack.areItemsEqual(output, resultStack)) {
                RPGMode.logger.info("Removed Recipe: " + recipe + " -> " + recipeResult);
                recipeRegistry.remove(recipe.getRegistryName());
            }
        }
    }

    /**
     * @author Draco18s
     * Remove a smelting recipe
     * @param resultItem - the smelting output Item
     * @param stacksize - the smelting output stack size
     * @param meta - the smelting output metadata
     */
    public static void RemoveSmelting(Item resultItem, int stacksize, int meta) {
        ItemStack resultStack = new ItemStack(resultItem, stacksize, meta);
        RemoveSmelting(resultStack);
    }

    /**
     * @author Draco18s
     * Remove a smelting recipe
     * @param resultStack - the output result stack, including metadata and size
     */
    public static void RemoveSmelting(ItemStack resultStack) {
        ItemStack recipeResult;
        Map<ItemStack,ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
        Iterator<ItemStack> iterator = recipes.keySet().iterator();
        while(iterator.hasNext()) {
            ItemStack tmpRecipe = iterator.next();
            recipeResult = recipes.get(tmpRecipe);
            if (ItemStack.areItemStacksEqual(resultStack, recipeResult)) {
                RPGMode.logger.info(" Removed Recipe: " + tmpRecipe + " -> " + recipeResult);
                iterator.remove();
            }
        }
    }
}
