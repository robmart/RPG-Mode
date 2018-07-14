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

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.client.render.IModelRegister;

import java.util.Objects;

/**
 * @author Robmart
 */
public class ItemBase extends Item implements IModelRegister {
    public static int itemCounter = 0;

    public ItemBase(String name, CreativeTabs creativeTab) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTab);

        incrementCounter();
    }

    public ItemBase(String name, CreativeTabs creativeTab, int stackSize) {
        this(name, creativeTab);

        this.setMaxStackSize(stackSize);
    }

    protected static void incrementCounter() {
        itemCounter++;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                                                   new ModelResourceLocation(
                                                           Objects.requireNonNull(getRegistryName()),
                                                           "inventory"));
    }
}
