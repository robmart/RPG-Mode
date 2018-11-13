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

package robmart.rpgmode.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import robmart.rpgmode.common.potion.PotionBase;
import vazkii.arl.util.ProxyRegistry;

/**
 * @author Robmart
 * Created on 11/13/2018
 */
public class ItemTippedArrowOverride extends ItemTippedArrow {

    public ItemTippedArrowOverride() {
        this.setUnlocalizedName("tipped_arrow");
        this.setRegistryName("minecraft:tipped_arrow");
        ProxyRegistry.register(this);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (PotionType potiontype : PotionType.REGISTRY) {
                boolean shouldHaveArrow = true;
                for (PotionEffect potionEffect : potiontype.getEffects())
                    if (potionEffect.getPotion() instanceof PotionBase &&
                        !((PotionBase) potionEffect.getPotion()).getShouldHaveArrow()) {
                        shouldHaveArrow = false;
                        break;
                    }

                if (potiontype != PotionTypes.EMPTY && shouldHaveArrow) {
                    items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potiontype));
                }
            }
        }
    }
}
