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

package robmart.rpgmode.common.recipe.brewing;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipe;
import robmart.rpgmode.common.helper.PotionHelper;

/**
 * @author Robmart
 */

public class BrewRecipe extends BrewingRecipe {

    public BrewRecipe(PotionType input, ItemStack ingredient, PotionType output, Item typeIn, Item typeOut) {
        super(
                PotionHelper.getItemStackOfPotion(typeIn, input), ingredient,
                PotionHelper.getItemStackOfPotion(typeOut, output));
    }

    public BrewRecipe(PotionType input, ItemStack ingredient, PotionType output) {
        super(
                PotionHelper.getItemStackOfPotion(Items.POTIONITEM, input), ingredient,
                PotionHelper.getItemStackOfPotion(Items.POTIONITEM, output));
    }

    public BrewRecipe(PotionType input, ItemStack ingredient, ItemStack output) {
        super(PotionHelper.getItemStackOfPotion(Items.POTIONITEM, input), ingredient, output);
    }

    public BrewRecipe(ItemStack input, ItemStack ingredient, ItemStack output) {
        super(input, ingredient, output);
    }

    @Override
    public boolean isInput(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().equals(this.getInput().getItem()) &&
               PotionUtils.getPotionFromItem(stack).equals(PotionUtils.getPotionFromItem(getInput()));
    }
}
