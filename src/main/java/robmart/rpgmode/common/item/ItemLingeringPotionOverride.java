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
import net.minecraft.item.Item;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.common.init.InitItems;
import robmart.rpgmode.common.potion.PotionBase;
import vazkii.arl.util.ProxyRegistry;

/**
 * @author Robmart
 */
public class ItemLingeringPotionOverride extends ItemLingeringPotion {

    public ItemLingeringPotionOverride() {
        this.setUnlocalizedName("lingering_potion");
        this.setRegistryName("minecraft:lingering_potion");
        ProxyRegistry.register(this);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (PotionType potiontype : PotionType.REGISTRY) {
                boolean shouldHaveSpecial = true;
                for (PotionEffect potionEffect : potiontype.getEffects())
                    if (potionEffect.getPotion() instanceof PotionBase &&
                        !((PotionBase) potionEffect.getPotion()).getShouldHaveSpecialPotions()) {
                        shouldHaveSpecial = false;
                        break;
                    }

                if (potiontype != PotionTypes.EMPTY && shouldHaveSpecial) {
                    items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potiontype));
                }
            }
        }
    }

    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     * <p>
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return InitItems.POTION.hasEffect(stack);
    }
}
