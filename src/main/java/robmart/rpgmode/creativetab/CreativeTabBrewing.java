package robmart.rpgmode.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    public void Init(){
        Items.GHAST_TEAR.setCreativeTab(this);
        Items.GLASS_BOTTLE.setCreativeTab(this);
        Items.FERMENTED_SPIDER_EYE.setCreativeTab(this);
        Items.BLAZE_POWDER.setCreativeTab(this);
        Items.MAGMA_CREAM.setCreativeTab(this);
        Items.BREWING_STAND.setCreativeTab(this);
        Items.CAULDRON.setCreativeTab(this);
        Items.SPECKLED_MELON.setCreativeTab(this);
        Items.GOLDEN_CARROT.setCreativeTab(this);
        Items.RABBIT_FOOT.setCreativeTab(this);
        Items.DRAGON_BREATH.setCreativeTab(this);
        Items.POTIONITEM.setCreativeTab(this);
        Items.SPLASH_POTION.setCreativeTab(this);
        Items.LINGERING_POTION.setCreativeTab(this);
    }

    /**
     * Only shows items which have tabToDisplayOn == this
     * Filters out the potions replaced by this mod.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> itemList) {
        String[] blocked = new String[]{"strength", "weakness"};
        NonNullList<ItemStack> list = NonNullList.create();
        NonNullList<ItemStack> list2 = NonNullList.create();
        for (Item item : Item.REGISTRY)
            item.getSubItems(this, list);

        for (String aBlocked : blocked)
            for (ItemStack itemStack : list)
                if (itemStack.getItem() instanceof ItemPotion)
                    if (itemStack.getItem().getNBTShareTag(itemStack).getString("Potion").contains("minecraft:") &&
                            itemStack.getItem().getNBTShareTag(itemStack).getString("Potion").contains(aBlocked))
                        list2.add(itemStack);

        for (ItemStack itemStack : list2)
            list.remove(itemStack);

        itemList.addAll(list);
    }
}
