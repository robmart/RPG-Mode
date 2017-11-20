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
    /**
     * The icon texture to use in the HUD and inventory GUI.
     */
    final ResourceLocation iconTexture;

    public PotionBase(boolean isBadEffect, int liquidColor, String name) {
        super(isBadEffect, liquidColor);
        setPotionName(this, name);
        iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/potions/" + name + ".png");
    }

    public PotionBase(boolean isBadEffect, int liquidR, int liquidG, int liquidB, String name) {
        this(isBadEffect, new Color(liquidR, liquidG, liquidB).getRGB(), name);
    }

    /**
     * Set the registry name of {@code potion} to {@code potionName} and the unlocalised name to the full registry name.
     *
     * @param potion     The potion
     * @param potionName The potion's name
     */
    public static void setPotionName(Potion potion, String potionName) {
        potion.setRegistryName(Reference.MOD_ID, potionName);
        potion.setPotionName("effect." + potion.getRegistryName().toString());
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    /**
     * Called to draw the this Potion onto the player's inventory when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param effect the active PotionEffect
     * @param mc     the Minecraft instance, for convenience
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(iconTexture);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    /**
     * Called to draw the this Potion onto the player's ingame HUD when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param effect the active PotionEffect
     * @param mc     the Minecraft instance, for convenience
     * @param alpha  the alpha value, blinks when the potion is about to run out
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        System.out.println("WHY IS THIS NOT WORKING");
        mc.getTextureManager().bindTexture(iconTexture);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}