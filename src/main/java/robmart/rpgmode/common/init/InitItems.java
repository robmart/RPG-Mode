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
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import robmart.rpgmode.api.item.RPGItems;
import robmart.rpgmode.api.reference.RefItemNames;
import robmart.rpgmode.api.reference.RefOreDict;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.item.*;
import robmart.rpgmode.common.util.ReflectionUtil;

/**
 * @author Robmart
 */
public class InitItems {
    private static int oreDictCounter = 0;

    private InitItems() {
    }

    public static void init() {
        new ItemPotionOverride();
        new ItemSplashPotionOverride();
        new ItemLingeringPotionOverride();
        new ItemTippedArrowOverride();

        try {
            ReflectionUtil
                    .setField(RPGItems.class, "BAT_WING", new ItemBase(RefItemNames.BAT_WING, CreativeTabs.BREWING));
            ReflectionUtil.setField(RPGItems.class, "PARROT_FEATHER",
                                    new ItemBase(RefItemNames.PARROT_FEATHER, CreativeTabs.BREWING));
            ReflectionUtil.setField(RPGItems.class, "OBSIDIAN_DUST",
                                    new ItemBase(RefItemNames.OBSIDIAN_DUST, CreativeTabs.BREWING));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            RPGMode.logger.error(e);
        }
    }

    private static void registerOre(String name, ItemStack ore) {
        OreDictionary.registerOre(name, ore);
        oreDictCounter++;
    }

    public static void registerOreDictionary() {
        RPGMode.logger.info("Adding ore dictionary entries");

        //Items
        registerOre(RefOreDict.WING_BAT, new ItemStack(RPGItems.BAT_WING));

        registerOre(RefOreDict.OBSIDIAN_DUST, new ItemStack(RPGItems.OBSIDIAN_DUST));

        RPGMode.logger.info(String.format("%s ore dictionary entries added", oreDictCounter));
    }
}
