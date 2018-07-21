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

package robmart.rpgmode.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.helper.RecipeHelper;
import robmart.rpgmode.common.recipe.brewing.BrewRecipe;
import robmart.rpgmode.common.recipe.brewing.BrewingRecipeWrapper;
import robmart.rpgmode.common.recipe.crafting.CraftingHandler;

import java.util.List;

/**
 * @author Robmart
 */
public class InitRecipes {
    private static int brewingCounter = 0;

    public static void generateBrewing() {
        RPGMode.logger.info("Adding brewing recipes");
        //Strength
        addBrewingRecipe(
                new BrewRecipe(InitPotionTypes.STRENGTH, new ItemStack(Items.REDSTONE), InitPotionTypes.LONG_STRENGTH));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.STRENGTH, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_STRENGTH));

        //Weakness
        addBrewingRecipe(
                new BrewRecipe(InitPotionTypes.WEAKNESS, new ItemStack(Items.REDSTONE), InitPotionTypes.LONG_WEAKNESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.WEAKNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_WEAKNESS));

        //Dexterousness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(InitItems.BAT_WING), InitPotionTypes.DEXTEROUSNESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.DEXTEROUSNESS, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_DEXTEROUSNESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.DEXTEROUSNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_DEXTEROUSNESS));

        //Clumsiness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.ROTTEN_FLESH), InitPotionTypes.CLUMSINESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.CLUMSINESS, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_CLUMSINESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.CLUMSINESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_CLUMSINESS));

        //Fortitude
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(InitItems.OBSIDIAN_DUST), InitPotionTypes.FORTITUDE));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.FORTITUDE, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_FORTITUDE));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.FORTITUDE, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_FORTITUDE));

        //Lethargy
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.DYE, 1, 15), InitPotionTypes.LETHARGY));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.LETHARGY, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_LETHARGY));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.LETHARGY, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_LETHARGY));

        //Intelligence
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, new ItemStack(InitItems.PARROT_FEATHER), InitPotionTypes.INTELLIGENCE));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.INTELLIGENCE, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_INTELLIGENCE));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.INTELLIGENCE, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_INTELLIGENCE));
        //Stupidity
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.LAVA),
                        InitPotionTypes.STUPIDITY));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.STUPIDITY, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_STUPIDITY));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.STUPIDITY, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_STUPIDITY));

        //Wisdom
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.APPLE), InitPotionTypes.WISDOM));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.WISDOM, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_WISDOM));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.WISDOM, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_WISDOM));

        //Foolishness
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, new ItemStack(InitBlocks.HELL_FLOWER), InitPotionTypes.FOOLISHNESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.FOOLISHNESS, new ItemStack(Items.REDSTONE),
                                        InitPotionTypes.LONG_FOOLISHNESS));
        addBrewingRecipe(new BrewRecipe(InitPotionTypes.FOOLISHNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        InitPotionTypes.STRONG_FOOLISHNESS));

        RPGMode.logger.info(String.format("%s brewing recipes added", brewingCounter));
    }

    private static void addBrewingRecipe(IBrewingRecipe recipe) {
        BrewingRecipeRegistry.addRecipe(recipe);
        brewingCounter++;
    }

    public static void generateRecipes() {
        CraftingHandler.addShapelessRecipe(new ItemStack(InitItems.OBSIDIAN_DUST, 4), Blocks.OBSIDIAN);
        CraftingHandler.addShapedRecipe(new ItemStack(Blocks.OBSIDIAN), "##", "##", '#', "dustObsidian");
        CraftingHandler.generateConstants();
    }
}
