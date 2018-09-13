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

import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;

/**
 * @author Robmart
 */
public class BrewingHelper {

    public static ItemStack getItemStackOfPotion(Item it, PotionType pt, boolean wasd) {
        ItemStack res = new ItemStack(it);
        res.setTagCompound(new NBTTagCompound());
        addPotionToItemStack(res, pt);
        return res;
    }

    public static ItemStack addPotionToItemStack(ItemStack itemIn, PotionType potionIn) {
        ResourceLocation resourcelocation = potionIn.getRegistryName();
        System.out.println(resourcelocation);

        if (potionIn == PotionTypes.EMPTY || resourcelocation == null) {
            if (itemIn.hasTagCompound()) {
                NBTTagCompound nbttagcompound = itemIn.getTagCompound();
                nbttagcompound.removeTag("Potion");

                if (nbttagcompound.hasNoTags()) {
                    itemIn.setTagCompound(null);
                }
            }
        }
        else {
            NBTTagCompound nbttagcompound1 = itemIn.hasTagCompound() ? itemIn.getTagCompound() : new NBTTagCompound();
            nbttagcompound1.setString("Potion", resourcelocation.toString());
            itemIn.setTagCompound(nbttagcompound1);
        }

        return itemIn;
    }
    public static ItemStack getItemStackOfPotion(Item it, PotionType pt) {
        ItemStack res = new ItemStack(it);
        res.setTagCompound(new NBTTagCompound());
        PotionUtils.addPotionToItemStack(res, pt);
        return res;
    }
}
