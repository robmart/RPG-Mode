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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import robmart.rpgmode.common.RPGMode;

import java.util.Objects;

/**
 * @author Robmart
 */
public class ItemHelper {
    public static ItemStack turnBottleIntoItem(
            ItemStack itemStackInput, EntityPlayer player, ItemStack itemStackOutput) {
        if (!player.isCreative())
            itemStackInput.shrink(1);

        player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(itemStackInput.getItem())));

        if (itemStackInput.isEmpty()) {
            return itemStackOutput;
        }
        else {
            if (!player.inventory.addItemStackToInventory(itemStackOutput)) {
                player.dropItem(itemStackOutput, false);
            }

            return itemStackInput;
        }
    }

    /**
     * Gets all subtypes of an item
     *
     * @param item      The item in question
     * @param stackSize How large the stacks should be
     *
     * @return A list of the subtypes
     *
     * @author mezz
     */
    public static NonNullList<ItemStack> getSubtypes(final Item item, final int stackSize) {
        NonNullList<ItemStack> itemStacks = NonNullList.create();

        for (CreativeTabs itemTab : item.getCreativeTabs()) {
            if (itemTab == null) {
                itemStacks.add(new ItemStack(item, stackSize));
            }
            else {
                NonNullList<ItemStack> subItems = NonNullList.create();
                try {
                    item.getSubItems(itemTab, subItems);
                } catch (RuntimeException | LinkageError e) {
                    RPGMode.logger.warn("Caught a crash while getting sub-items of {}", item, e);
                }

                for (ItemStack subItem : subItems) {
                    if (!subItem.isEmpty() && subItem.getMetadata() != OreDictionary.WILDCARD_VALUE) {
                        if (subItem.getCount() != stackSize) {
                            ItemStack subItemCopy = subItem.copy();
                            subItemCopy.setCount(stackSize);
                            itemStacks.add(subItemCopy);
                        }
                        else {
                            itemStacks.add(subItem);
                        }
                    }
                }
            }
        }

        return itemStacks;
    }
}
