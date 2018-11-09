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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import robmart.rpgmode.api.item.RPGItems;
import robmart.rpgmode.api.reference.RefItemNames;
import robmart.rpgmode.api.reference.RefOreDict;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.item.ItemBase;
import robmart.rpgmode.common.item.ItemLingeringPotionOverride;
import robmart.rpgmode.common.item.ItemPotionOverride;
import robmart.rpgmode.common.item.ItemSplashPotionOverride;

/**
 * @author Robmart
 */
public class InitItems {
    private static int oreDictCounter = 0;

    public static final Item POTION           = new ItemPotionOverride();
    public static final Item SPLASH_POTION    = new ItemSplashPotionOverride();
    public static final Item LINGERING_POTION = new ItemLingeringPotionOverride();

    public static void init() {
        RPGItems.BAT_WING = new ItemBase(RefItemNames.BAT_WING, CreativeTabs.BREWING);
        RPGItems.PARROT_FEATHER = new ItemBase(RefItemNames.PARROT_FEATHER, CreativeTabs.BREWING);
        RPGItems.OBSIDIAN_DUST = new ItemBase(RefItemNames.OBSIDIAN_DUST, CreativeTabs.BREWING);
    }

    public static void registerOreDictionary() {
        RPGMode.logger.info("Adding ore dictionary entries");

        //Items
        registerOre(RefOreDict.WING_BAT, new ItemStack(RPGItems.BAT_WING));

        registerOre(RefOreDict.OBSIDIAN_DUST, new ItemStack(RPGItems.OBSIDIAN_DUST));

        RPGMode.logger.info(String.format("%s ore dictionary entries added", oreDictCounter));
    }

    private static void registerOre(String name, ItemStack ore) {
        OreDictionary.registerOre(name, ore);
        oreDictCounter++;
    }
}
