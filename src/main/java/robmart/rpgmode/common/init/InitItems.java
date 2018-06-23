package robmart.rpgmode.common.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.item.ItemBase;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart.
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
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class InitItems {
    private static int oreDictCounter = 0;

    public static final ItemBase BAT_WING = new ItemBase("bat_wing", CreativeTabs.BREWING);

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        RPGMode.logger.info("Adding Items");
        event.getRegistry().registerAll(
                BAT_WING
        );
        RPGMode.logger.info(String.format("%s items added", ItemBase.itemCounter));
    }

    public static void registerOreDictionary() {
        RPGMode.logger.info("Adding ore dictionary entries");

        //Items
        registerOre("wingBat", new ItemStack(BAT_WING));

        //Potions
        registerOre("potionStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.STRENGTH));
        registerOre("potionLongStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.LONG_STRENGTH));
        registerOre("potionStrongStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.STRONG_STRENGTH));

        registerOre("potionStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.STRENGTH));
        registerOre("potionLongStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.LONG_STRENGTH));
        registerOre("potionStrongStrength", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.STRONG_STRENGTH));

        registerOre("potionWeakness", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.WEAKNESS));
        registerOre("potionLongWeakness", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.LONG_WEAKNESS));

        registerOre("potionWeakness", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.WEAKNESS));
        registerOre("potionLongWeakness", PotionHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.LONG_WEAKNESS));

        RPGMode.logger.info(String.format("%s ore dictionary entries added", oreDictCounter));
    }

    private static void registerOre(String name, ItemStack ore) {
        OreDictionary.registerOre(name, ore);
        oreDictCounter++;
    }
}
