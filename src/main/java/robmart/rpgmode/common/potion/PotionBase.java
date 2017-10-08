package robmart.rpgmode.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.common.reference.Reference;

import java.awt.*;

/**
 * Created by Robmart.
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2017 Robmart
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
public class PotionBase extends Potion {
    private final ResourceLocation iconTexture;

    public PotionBase(final boolean isBadEffect, final int liquidColor, final String name) {
        super(isBadEffect, liquidColor);
        setPotionName(this, name);
        iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/potions/" + name + ".png");
    }

    public PotionBase(final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name) {
        this(isBadEffect, new Color(liquidR, liquidG, liquidB).getRGB(), name);
    }

    public static void setPotionName(final Potion potion, final String potionName) {
        potion.setRegistryName(Reference.MOD_ID, potionName);
        potion.setPotionName("effect." + potion.getRegistryName().toString());
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(iconTexture);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc, final float alpha) {
        mc.getTextureManager().bindTexture(iconTexture);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}
