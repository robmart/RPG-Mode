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

package robmart.rpgmode.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.common.reference.Reference;

import java.awt.*;
import java.util.Objects;

/**
 * @author Robmart
 */
public class PotionBase extends Potion {
    public static final IAttribute STRENGTH      = (new RangedAttribute(
            null, Reference.MOD_ID + ".strength", 0, -10, 10)).setDescription("Strength").setShouldWatch(true);
    public static final IAttribute DEXTEROUSNESS = (new RangedAttribute(
            null, Reference.MOD_ID + ".dexterousness", 0, -10, 10)).setDescription("Dexterity").setShouldWatch(true);
    public static final IAttribute FORTITUDE     = (new RangedAttribute(
            null, Reference.MOD_ID + ".fortitude", 0, -10, 10)).setDescription("Constitution").setShouldWatch(true);
    public static final IAttribute INTELLIGENCE  = (new RangedAttribute(
            null, Reference.MOD_ID + ".intelligence", 0, -10, 10)).setDescription("Intelligence").setShouldWatch(true);
    public static final IAttribute WISDOM        = (new RangedAttribute(null, Reference.MOD_ID + ".wisdom", 0, -10, 10))
            .setDescription("Wisdom").setShouldWatch(true);
    /**
     * Wheather the potion will "glint" or not
     */
    public              boolean    useEnchantedEffect;

    /**
     * The instance of the potion
     */
    public PotionBase instance = null;

    /**
     * The tag used to store the effect on the entity
     */
    public String TAG_NAME;

    /**
     * Counts how many potions have been added so far
     */
    public static int potionCounter = 0;

    /**
     * The icon texture to use in the HUD and inventory GUI.
     */
    private final ResourceLocation iconTexture;

    public PotionBase(final boolean isBadEffect, final int liquidColor, final String name, boolean useGlint) {
        super(isBadEffect, liquidColor);
        setPotionName(this, name);
        this.iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/potions/" + name + ".png");
        this.TAG_NAME = String.format("%s - %s", Reference.MOD_ID, name);
        this.useEnchantedEffect = useGlint;
        this.instance = this;
        potionCounter++;
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidColor, final String name, boolean useGlint, IAttribute attribute,
            String UUID, double amount, int operation) {
        this(isBadEffect, liquidColor, name, useGlint);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    public PotionBase(final boolean isBadEffect, final int liquidColor, final String name) {
        this(isBadEffect, liquidColor, name, true);
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidColor, final String name, IAttribute attribute, String UUID,
            double amount, int operation) {
        this(isBadEffect, liquidColor, name);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name,
            boolean useGlint, IAttribute attribute, String UUID, double amount, int operation) {
        this(isBadEffect, liquidR, liquidG, liquidB, name, useGlint);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name,
            boolean useGlint) {
        this(isBadEffect, new Color(liquidR, liquidG, liquidB).getRGB(), name, useGlint);
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name,
            IAttribute attribute, String UUID, double amount, int operation) {
        this(isBadEffect, liquidR, liquidG, liquidB, name);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name) {
        this(isBadEffect, liquidR, liquidG, liquidB, name, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    /**
     * Set the registry name of {@code potion} to {@code potionName} and the unlocalised name to the full registry name.
     *
     * @param potion     The potion
     * @param potionName The potion's name
     */
    public static void setPotionName(final Potion potion, final String potionName) {
        potion.setRegistryName(Reference.MOD_ID, potionName);
        final ResourceLocation registryName = Objects.requireNonNull(potion.getRegistryName());
        potion.setPotionName("effect." + registryName.toString());
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @Override
    public boolean shouldRender(PotionEffect effect) {
        return effect.getDuration() > 0;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect) {
        return effect.getDuration() > 0;
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
    public void renderInventoryEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(iconTexture);
            GlStateManager.scale(0.25, 0.25, 0.25);
            Gui.drawModalRectWithCustomSizedTexture((x + 7) * 4, (y + 8) * 4, 0, 0, 64, 64, 64, 64);
            GlStateManager.scale(4, 4, 4);
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
    public void renderHUDEffect(
            final int x, final int y, final PotionEffect effect, final Minecraft mc, final float alpha) {
        mc.getTextureManager().bindTexture(iconTexture);
        GlStateManager.scale(0.25, 0.25, 0.25);
        Gui.drawModalRectWithCustomSizedTexture((x + 4) * 4, (y + 4) * 4, 0, 0, 64, 64, 64, 64);
        GlStateManager.scale(4, 4, 4);
    }
}