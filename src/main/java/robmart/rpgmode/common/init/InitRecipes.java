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
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import robmart.rpgmode.api.block.RPGBlocks;
import robmart.rpgmode.api.item.RPGItems;
import robmart.rpgmode.api.potion.RPGPotionTypes;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.recipe.brewing.BrewRecipe;
import vazkii.arl.recipe.RecipeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Robmart
 */
public class InitRecipes {
    private static int brewingCounter = 0;

    private InitRecipes() {
    }

    private static void addBrewingRecipe(IBrewingRecipe recipe) {
        BrewingRecipeRegistry.addRecipe(recipe);
        brewingCounter++;
    }

    public static void generateBrewing() {
        String longPrefix = "long_";
        String strongPrefix = "strong_";

        RPGMode.logger.info("Adding brewing recipes");

        //Dexterousness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(RPGItems.batWing), RPGPotionTypes.DEXTEROUSNESS));

        //Clumsiness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.ROTTEN_FLESH), RPGPotionTypes.CLUMSINESS));

        //Fortitude
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(RPGItems.obsidianDust), RPGPotionTypes.FORTITUDE));

        //Lethargy
        addBrewingRecipe(new BrewRecipe(PotionTypes.AWKWARD,
                                        new ItemStack(Items.DYE, 1, 15), RPGPotionTypes.LETHARGY));

        //Intelligence
        addBrewingRecipe(new BrewRecipe(PotionTypes.AWKWARD,
                                        new ItemStack(RPGItems.parrotFeather), RPGPotionTypes.INTELLIGENCE));
        //Stupidity
        addBrewingRecipe(new BrewRecipe(
                PotionTypes.AWKWARD,
                PotionHelper.getItemStackOfPotion(Items.POTIONITEM, RPGPotionTypes.LAVA),
                RPGPotionTypes.STUPIDITY));

        //Wisdom
        addBrewingRecipe(new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.APPLE), RPGPotionTypes.WISDOM));

        //Foolishness
        addBrewingRecipe(new BrewRecipe(PotionTypes.AWKWARD,
                                        new ItemStack(RPGBlocks.hellFlower), RPGPotionTypes.FOOLISHNESS));

        //Automatically makes recipes for long and strong potions
        for (Field field : RPGPotionTypes.class.getDeclaredFields()) {
            //Finds PotionTypes in RPGPotionTypes that are public, static and long/strong
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) &&
                field.getType() == PotionType.class && (field.getName().toLowerCase().contains(longPrefix) ||
                                                        field.getName().toLowerCase().contains(strongPrefix))) {
                try {
                    for (Field field2 : RPGPotionTypes.class.getDeclaredFields()) {
                        //Finds the base PotionType
                        if (field2.getName().equalsIgnoreCase(field.getName().replaceAll(".*_", ""))) {
                            //Adds recipe for long potions
                            if (field.getName().toLowerCase().contains(longPrefix))
                                addBrewingRecipe(new BrewRecipe(
                                        (PotionType) field2.get(null),
                                        new ItemStack(Items.REDSTONE),
                                        (PotionType) field.get(null)));

                            //Adds recipe for strong potions
                            if (field.getName().toLowerCase().contains(strongPrefix))
                                addBrewingRecipe(new BrewRecipe(
                                        (PotionType) field2.get(null),
                                        new ItemStack(Items.GLOWSTONE_DUST),
                                        (PotionType) field.get(null)));
                        }
                    }
                } catch (IllegalAccessException e) {
                    RPGMode.logger.error(e);
                }
            }
        }

        //Splash and Lingering
        net.minecraft.potion.PotionHelper
                .addContainerRecipe((ItemPotion) InitItems.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
        net.minecraft.potion.PotionHelper
                .addContainerRecipe((ItemPotion) InitItems.SPLASH_POTION, Items.DRAGON_BREATH, Items
                .LINGERING_POTION);

        RPGMode.logger.info(String.format("%s brewing recipes added", brewingCounter));
    }

    public static void generateRecipes() {
        RecipeHandler.addShapelessRecipe(new ItemStack(RPGItems.obsidianDust, 4), new ItemStack(Blocks.OBSIDIAN));
        RecipeHandler.addShapedRecipe(new ItemStack(Blocks.OBSIDIAN), "##", "##", '#', "dustObsidian");
    }
}
