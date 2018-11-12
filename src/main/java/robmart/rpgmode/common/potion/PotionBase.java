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

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.api.potion.RPGPotionTypes;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.helper.PotionHelper;
import vazkii.arl.util.ProxyRegistry;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author Robmart
 */
public class PotionBase extends Potion {

    /**
     * Wheather the potion will "glint" or not
     */
    public boolean useEnchantedEffect;

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
        if (!isBadEffect)
            this.setBeneficial();

        setPotionName(name);
        ResourceLocation resourceLocation = new ResourceLocation(name);
        this.iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/" +
                                                                  resourceLocation.getResourcePath() + ".png");
        this.TAG_NAME = String.format("%s - %s", Reference.MOD_ID, resourceLocation.getResourcePath());
        this.useEnchantedEffect = useGlint;
        this.instance = this;
        ProxyRegistry.register(this);
        setPotionTypes();
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

    public void setPotionTypes() {
        String LONG_PREFIX = "long_";
        String STRONG_PREFIX = "strong_";

        int HELPFUL_DURATION_STANDARD = 3600;
        int HELPFUL_DURATION_LONG = 9600;
        int HELPFUL_DURATION_STRONG = 1800;

        int HARMFUL_DURATION_STANDARD = 1800;
        int HARMFUL_DURATION_LONG = 4800;
        int HARMFUL_DURATION_STRONG = 900;

        for (Field field : RPGPotionTypes.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) &&
                field.getType() == PotionType.class) {
                try {
                    if (field.getName().toLowerCase().equals(this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(new PotionEffect(this, isBadEffect() ?
                                                                                             HARMFUL_DURATION_STANDARD :
                                                                                             HELPFUL_DURATION_STANDARD)));

                    else if (field.getName().toLowerCase().equals(LONG_PREFIX + this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(
                                new PotionEffect(this, isBadEffect() ?
                                                       HARMFUL_DURATION_LONG :
                                                       HELPFUL_DURATION_LONG),
                                LONG_PREFIX));

                    else if (field.getName().toLowerCase().equals(STRONG_PREFIX + this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(new PotionEffect(this, isBadEffect() ?
                                                                                             HARMFUL_DURATION_STRONG :
                                                                                             HELPFUL_DURATION_STRONG,
                                                                                       1), STRONG_PREFIX));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    /**
     * Set the registry NAME of {@code potion} to {@code potionName} and the unlocalised NAME to the full registry NAME.
     *
     * @param potionName The potion's NAME
     */
    public Potion setPotionName(final String potionName) {
        this.setRegistryName(potionName);
        final ResourceLocation registryName = Objects.requireNonNull(this.getRegistryName());
        super.setPotionName("effect." + registryName.toString());
        return this;
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
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        renderPotionIcon(x + 6, y + 7, mc);
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
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
        renderPotionIcon(x + 3, y + 3, mc);
    }

    @SideOnly(Side.CLIENT)
    private void renderPotionIcon(int x, int y, net.minecraft.client.Minecraft mc) {
        mc.getTextureManager().bindTexture(iconTexture);
        int width = 18;
        int height = 18;
        float widthRatio = 1.0f / 18.0f;
        float heightRatio = 1.0f / 18.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos((double) (x + 0), (double) (y + height), 0).tex(0, (double) ((float) height * heightRatio))
                    .endVertex();
        vertexBuffer.pos((double) (x + width), (double) (y + height), 0)
                    .tex((double) ((float) width * widthRatio), (double) ((float) height * heightRatio)).endVertex();
        vertexBuffer.pos((double) (x + width), (double) (y + 0), 0).tex((double) ((float) width * widthRatio), 0)
                    .endVertex();
        vertexBuffer.pos((double) (x + 0), (double) (y + 0), 0).tex(0, 0).endVertex();
        tessellator.draw();
    }
}