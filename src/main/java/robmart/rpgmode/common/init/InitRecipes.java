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
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import robmart.rpgmode.api.block.RPGBlocks;
import robmart.rpgmode.api.item.RPGItems;
import robmart.rpgmode.api.potion.RPGPotionTypes;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.recipe.brewing.BrewRecipe;
import vazkii.arl.recipe.RecipeHandler;

/**
 * @author Robmart
 */
public class InitRecipes {
    private static int brewingCounter = 0;

    public static void generateBrewing() {
        RPGMode.logger.info("Adding brewing recipes");
        //Strength
        addBrewingRecipe(
                new BrewRecipe(RPGPotionTypes.STRENGTH, new ItemStack(Items.REDSTONE), RPGPotionTypes.LONG_STRENGTH));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.STRENGTH, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_STRENGTH));

        //Weakness
        addBrewingRecipe(
                new BrewRecipe(RPGPotionTypes.WEAKNESS, new ItemStack(Items.REDSTONE), RPGPotionTypes.LONG_WEAKNESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.WEAKNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_WEAKNESS));

        //Dexterousness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(RPGItems.BAT_WING), RPGPotionTypes.DEXTEROUSNESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.DEXTEROUSNESS, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_DEXTEROUSNESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.DEXTEROUSNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_DEXTEROUSNESS));

        //Clumsiness
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.ROTTEN_FLESH), RPGPotionTypes.CLUMSINESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.CLUMSINESS, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_CLUMSINESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.CLUMSINESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_CLUMSINESS));

        //Fortitude
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(RPGItems.OBSIDIAN_DUST), RPGPotionTypes.FORTITUDE));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.FORTITUDE, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_FORTITUDE));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.FORTITUDE, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_FORTITUDE));

        //Lethargy
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.DYE, 1, 15), RPGPotionTypes.LETHARGY));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.LETHARGY, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_LETHARGY));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.LETHARGY, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_LETHARGY));

        //Intelligence
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, new ItemStack(RPGItems.PARROT_FEATHER), RPGPotionTypes.INTELLIGENCE));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.INTELLIGENCE, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_INTELLIGENCE));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.INTELLIGENCE, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_INTELLIGENCE));
        //Stupidity
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, PotionHelper.getItemStackOfPotion(Items.POTIONITEM, RPGPotionTypes.LAVA),
                        RPGPotionTypes.STUPIDITY));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.STUPIDITY, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_STUPIDITY));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.STUPIDITY, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_STUPIDITY));

        //Wisdom
        addBrewingRecipe(
                new BrewRecipe(PotionTypes.AWKWARD, new ItemStack(Items.APPLE), RPGPotionTypes.WISDOM));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.WISDOM, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_WISDOM));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.WISDOM, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_WISDOM));

        //Foolishness
        addBrewingRecipe(
                new BrewRecipe(
                        PotionTypes.AWKWARD, new ItemStack(RPGBlocks.HELL_FLOWER), RPGPotionTypes.FOOLISHNESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.FOOLISHNESS, new ItemStack(Items.REDSTONE),
                                        RPGPotionTypes.LONG_FOOLISHNESS));
        addBrewingRecipe(new BrewRecipe(RPGPotionTypes.FOOLISHNESS, new ItemStack(Items.GLOWSTONE_DUST),
                                        RPGPotionTypes.STRONG_FOOLISHNESS));

        //TODO: automatically add long and strong recipes (reflection)
        //Splash and Lingering
        net.minecraft.potion.PotionHelper
                .addContainerRecipe((ItemPotion) InitItems.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
        net.minecraft.potion.PotionHelper
                .addContainerRecipe((ItemPotion) InitItems.SPLASH_POTION, Items.DRAGON_BREATH, Items
                .LINGERING_POTION);

        RPGMode.logger.info(String.format("%s brewing recipes added", brewingCounter));
    }

    private static void addBrewingRecipe(IBrewingRecipe recipe) {
        BrewingRecipeRegistry.addRecipe(recipe);
        brewingCounter++;
    }

    public static void generateRecipes() {
        RecipeHandler.addShapelessRecipe(new ItemStack(RPGItems.OBSIDIAN_DUST, 4), new ItemStack(Blocks.OBSIDIAN));
        RecipeHandler.addShapedRecipe(new ItemStack(Blocks.OBSIDIAN), "##", "##", '#', "dustObsidian");
    }
}
