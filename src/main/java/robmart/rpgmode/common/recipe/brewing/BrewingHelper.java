/*
 * Copyright 2015-2017, mezz
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/mezz/JustEnoughItems/blob/1.12/LICENSE.txt
 */

package robmart.rpgmode.common.recipe.brewing;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.AbstractBrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.ItemHelper;
import robmart.rpgmode.common.init.InitItems;

import java.util.*;

/**
 * @author mezz
 */
public class BrewingHelper {

    private static final Set<BrewingRecipeWrapper> disabledRecipes = new HashSet<>();

    public static List<BrewingRecipeWrapper> getAllBrewingRecipes() {
        Set<BrewingRecipeWrapper> recipes = new HashSet<>();
        addAllVanillaBrewingRecipes(recipes);
        addAllModdedBrewingRecipes(recipes);

        return new ArrayList<>(recipes);
    }

    private static void addAllVanillaBrewingRecipes(Collection<BrewingRecipeWrapper> recipes) {
        List<ItemStack> potionIngredients = getAllPotionIngredients();
        List<ItemStack> knownPotions = new ArrayList<>();

        knownPotions.add(getItemStackOfPotion(
                Items.POTIONITEM,
                PotionTypes.WATER));

        boolean foundNewPotions;
        do {
            List<ItemStack> newPotions = getNewPotions(knownPotions, potionIngredients, recipes);
            foundNewPotions = !newPotions.isEmpty();
            knownPotions.addAll(newPotions);
        } while (foundNewPotions);
    }

    public static ItemStack getItemStackOfPotion(Item it, PotionType pt) {
        ItemStack res = new ItemStack(it);
        res.setTagCompound(new NBTTagCompound());
        PotionUtils.addPotionToItemStack(res, pt);
        return res;
    }

    /**
     * Collects a list of all vanilla potion ingredients
     *
     * @return all the vanilla potion ingredients
     *
     * @author Robmart
     */
    public static List<ItemStack> getAllPotionIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        List<ItemStack> everything = new ArrayList<>();

        for (Item item : ForgeRegistries.ITEMS) {
            if (item != null || item != Items.AIR) {
                NonNullList<ItemStack> items = ItemHelper.getSubtypes(item, 1);
                everything.addAll(items);
            }
        }

        for (Block block : ForgeRegistries.BLOCKS) {
            if (block != null && block != Blocks.AIR) {

                Item item = Item.getItemFromBlock(block);

                for (CreativeTabs itemTab : item.getCreativeTabs()) {
                    NonNullList<ItemStack> subBlocks = NonNullList.create();
                    try {
                        block.getSubBlocks(itemTab, subBlocks);
                    } catch (RuntimeException | LinkageError e) {
                        RPGMode.logger.error("Failed to getSubBlocks {}", e);
                    }

                    for (ItemStack subBlock : subBlocks) {
                        if (subBlock != null && !subBlock.isEmpty()) {
                            everything.add(subBlock);
                        }
                    }
                }
            }
        }

        for (ItemStack stack : everything) {
            if (PotionHelper.isReagent(stack))
                ingredients.add(stack);
        }

        return ingredients;
    }

    private static List<ItemStack> getNewPotions(
            List<ItemStack> knownPotions, List<ItemStack> potionIngredients, Collection<BrewingRecipeWrapper> recipes) {
        List<ItemStack> newPotions = new ArrayList<>();
        for (ItemStack potionInput : knownPotions) {
            for (ItemStack potionIngredient : potionIngredients) {
                ItemStack potionOutput = PotionHelper.doReaction(potionIngredient, potionInput.copy());
                if (potionOutput.equals(potionInput)) {
                    continue;
                }

                if (potionInput.getItem() == potionOutput.getItem()) {
                    PotionType potionOutputType = PotionUtils.getPotionFromItem(potionOutput);
                    if (potionOutputType == PotionTypes.WATER) {
                        continue;
                    }

                    PotionType potionInputType = PotionUtils.getPotionFromItem(potionInput);
                    ResourceLocation inputId = ForgeRegistries.POTION_TYPES.getKey(potionInputType);
                    ResourceLocation outputId = ForgeRegistries.POTION_TYPES.getKey(potionOutputType);
                    if (Objects.equals(inputId, outputId)) {
                        continue;
                    }
                }

                BrewingRecipeWrapper recipe = new BrewingRecipeWrapper(
                        potionIngredient, potionInput.copy(), potionOutput);
                if (!recipes.contains(recipe) && !disabledRecipes.contains(recipe)) {
                    if (BrewingRecipeRegistry.hasOutput(potionInput, potionIngredient)) {
                        recipes.add(recipe);
                    }
                    else {
                        disabledRecipes.add(recipe);
                    }
                    newPotions.add(potionOutput);
                }
            }
        }
        return newPotions;
    }

    public static void addAllModdedBrewingRecipes(Collection<BrewingRecipeWrapper> recipes) {
        for (IBrewingRecipe iBrewingRecipe : BrewingRecipeRegistry.getRecipes()) {
            if (iBrewingRecipe instanceof AbstractBrewingRecipe) {
                AbstractBrewingRecipe brewingRecipe = (AbstractBrewingRecipe) iBrewingRecipe;
                ItemStack ingredient = ((ItemStack) ((AbstractBrewingRecipe) iBrewingRecipe).getIngredient());

                if (!ingredient.isEmpty()) {
                    ItemStack input = brewingRecipe.getInput();
                    // AbstractBrewingRecipe.isInput treats any uncraftable potion here as a water bottle in the brewing stand
                    if (ItemStack.areItemStacksEqual(input, new ItemStack(Items.POTIONITEM))) {
                        input = getItemStackOfPotion(
                                Items.POTIONITEM,
                                PotionTypes.WATER);
                    }
                    ItemStack output = brewingRecipe.getOutput();
                    BrewingRecipeWrapper recipe = new BrewingRecipeWrapper(ingredient, input, output);
                    recipes.add(recipe);
                }
            }
        }
    }

    public static boolean isItemPotion(Item item) {
        return (item == Items.POTIONITEM || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION);
    }

    public static ItemStack convertPotionStack(ItemStack itemStack) {
        return getItemStackOfPotion(
                BrewingHelper.getOverridePotion(itemStack.getItem()), PotionUtils.getPotionFromItem(itemStack));
    }

    public static Item getOverridePotion(Item item) {
        if (!(item instanceof ItemPotion))
            throw new IllegalArgumentException();

        Item potionItem = item;

        if (item == Items.POTIONITEM)
            potionItem = InitItems.POTION;
        if (item == Items.SPLASH_POTION)
            potionItem = InitItems.SPLASH_POTION;
        if (item == Items.LINGERING_POTION)
            potionItem = InitItems.LINGERING_POTION;

        return potionItem;
    }
}
