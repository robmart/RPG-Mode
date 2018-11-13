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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import robmart.rpgmode.common.potion.PotionBase;
import robmart.rpgmode.common.potion.PotionTypeBase;

import javax.annotation.Nullable;

/**
 * @author Robmart
 */
public class PotionHelper {

    public static ItemStack getItemStackOfPotion(Item it, PotionType pt) {
        ItemStack res = new ItemStack(it);
        res.setTagCompound(new NBTTagCompound());
        PotionUtils.addPotionToItemStack(res, pt);
        return res;
    }

    public static PotionTypeBase createPotionType(final PotionEffect effect) {
        return createPotionType(effect, null);
    }

    public static PotionTypeBase createPotionType(final PotionEffect effect, @Nullable final String namePrefix) {
        final ResourceLocation potionName = effect.getPotion().getRegistryName();
        final ResourceLocation potionTypeName;

        if (namePrefix != null) {
            assert potionName != null;
            potionTypeName = new ResourceLocation(
                    potionName.getResourceDomain(), namePrefix + potionName.getResourcePath());
        }
        else {
            assert potionName != null;
            potionTypeName = potionName;
        }

        return (PotionTypeBase) new PotionTypeBase(potionName.toString(), effect).setRegistryName(potionTypeName);
    }

    public static boolean hasEffect(ItemStack stack) {
        boolean useGlint = false;
        boolean modPotions = true;

        for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
            if (!(effect.getPotion() instanceof PotionBase)) {
                modPotions = false;
                break;
            }
            else {
                if (((PotionBase) effect.getPotion()).getUseEnchantedEffect()) useGlint = true;
            }
        }

        if (modPotions)
            return useGlint;

        return !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }

    private PotionHelper() {
    }
}
