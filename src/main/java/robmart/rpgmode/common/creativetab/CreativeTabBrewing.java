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

package robmart.rpgmode.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.common.item.ItemLingeringPotionOverride;
import robmart.rpgmode.common.item.ItemPotionOverride;
import robmart.rpgmode.common.item.ItemSplashPotionOverride;
import robmart.rpgmode.common.reference.Reference;

import java.util.Objects;

/**
 * @author Robmart
 */
public class CreativeTabBrewing extends CreativeTabs {

    public static CreativeTabBrewing instance = new CreativeTabBrewing();

    private CreativeTabBrewing() {
        super(Reference.MOD_ID+":brewing");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.POTIONITEM);
    }

    /**
     * Only shows items which have tabToDisplayOn == this
     * Filters out the potions replaced by this mod.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> itemList) {
        //TODO: Clean
        String[] blocked = new String[]{"strength", "weakness"};
        NonNullList<ItemStack> list = NonNullList.create();
        NonNullList<ItemStack> listBanned = NonNullList.create();
        for (Item item : Item.REGISTRY) {
            item.getSubItems(BREWING, list);
            item.getSubItems(this, list);
        }

        for (ItemStack itemStack : list) {
            for (String aBlocked : blocked)
                if (itemStack.getItem() instanceof ItemPotion)
                    if (Objects.requireNonNull(itemStack.getItem().getNBTShareTag(itemStack)).getString("Potion").contains("minecraft:") &&
                            Objects.requireNonNull(itemStack.getItem().getNBTShareTag(itemStack)).getString("Potion").contains(aBlocked))
                        listBanned.add(itemStack);

            if (itemStack.getItem() instanceof ItemPotion && !((itemStack.getItem() instanceof ItemPotionOverride) || itemStack.getItem() instanceof ItemSplashPotionOverride || itemStack.getItem() instanceof ItemLingeringPotionOverride))
                listBanned.add(itemStack);
            if (itemStack.getItem() instanceof ItemSplashPotion && !((itemStack.getItem() instanceof ItemSplashPotionOverride) || itemStack.getItem() instanceof ItemPotionOverride || itemStack.getItem() instanceof ItemLingeringPotionOverride))
                listBanned.add(itemStack);
            if (itemStack.getItem() instanceof ItemLingeringPotion && !((itemStack.getItem() instanceof ItemLingeringPotionOverride) || itemStack.getItem() instanceof ItemSplashPotionOverride || itemStack.getItem() instanceof ItemPotionOverride))
                listBanned.add(itemStack);

            if (itemStack.getItem() instanceof ItemSplashPotion || itemStack.getItem() instanceof ItemLingeringPotion)
                if (Objects.requireNonNull(itemStack.getItem().getNBTShareTag(itemStack)).getString("Potion").contains("rpgmode:") &&
                        Objects.requireNonNull(itemStack.getItem().getNBTShareTag(itemStack)).getString("Potion").contains("lava"))
                    listBanned.add(itemStack);
        }

        for (ItemStack stack : listBanned)
            list.remove(stack);

        itemList.addAll(list);
    }
}
