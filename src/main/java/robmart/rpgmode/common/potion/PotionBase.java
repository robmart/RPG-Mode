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
import robmart.rpgmode.common.RPGMode;
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
     * Whether the potion will "glint" or not
     */
    private boolean useEnchantedEffect = true;

    /**
     * Whether the potion will have special alternatives (splash and linger)
     */
    private boolean shouldHaveSpecialPotions = true;

    /**
     * The instance of the potion
     */
    public final PotionBase instance;

    /**
     * The tag used to store the effect on the entity
     */
    public final String tagName;

    /**
     * The icon texture to use in the HUD and inventory GUI.
     */
    private final ResourceLocation iconTexture;

    public PotionBase(
            final boolean isBadEffect, final int liquidColor, final String name, IAttribute attribute,
            String UUID, double amount, int operation) {
        this(isBadEffect, liquidColor, name);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    public PotionBase(final boolean isBadEffect, final int liquidColor, final String name) {
        super(isBadEffect, liquidColor);
        if (!isBadEffect)
            this.setBeneficial();

        setPotionName(name);
        ResourceLocation resourceLocation = new ResourceLocation(name);
        this.iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/" +
                                                                  resourceLocation.getResourcePath() + ".png");
        this.tagName = String.format("%s - %s", Reference.MOD_ID, resourceLocation.getResourcePath());
        this.instance = this;
        ProxyRegistry.register(this);
        setPotionTypes();
    }

    private void setPotionTypes() {
        String longPrefix = "long_";
        String strongPrefix = "strong_";

        int helpfulDurationStandard = 3600;
        int helpfulDurationLong = 9600;
        int helpfulDurationStrong = 1800;

        int harmfulDurationStandard = 1800;
        int harmfulDurationLong = 4800;
        int harmfulDurationStrong = 900;

        for (Field field : RPGPotionTypes.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) &&
                field.getType() == PotionType.class) {
                try {
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

                    if (field.getName().equalsIgnoreCase(this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(new PotionEffect(this, isBadEffect() ?
                                                                                             harmfulDurationStandard :
                                                                                             helpfulDurationStandard)));

                    else if (field.getName().equalsIgnoreCase(longPrefix + this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(
                                new PotionEffect(this, isBadEffect() ?
                                                       harmfulDurationLong :
                                                       helpfulDurationLong), longPrefix));

                    else if (field.getName().equalsIgnoreCase(strongPrefix + this.getName().replaceAll(".*:", "")))
                        field.set(null, PotionHelper.createPotionType(new PotionEffect(this, isBadEffect() ?
                                                                                             harmfulDurationStrong :
                                                                                             helpfulDurationStrong,
                                                                                       1), strongPrefix));

                } catch (IllegalAccessException | NoSuchFieldException e) {
                    RPGMode.logger.error(e);
                }
            }
        }
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name,
            IAttribute attribute, String UUID, double amount, int operation) {
        this(isBadEffect, liquidR, liquidG, liquidB, name);
        this.registerPotionAttributeModifier(attribute, UUID, amount, operation);
    }

    /**
     * Set the registry NAME of {@code potion} to {@code potionName} and the unlocalised NAME to the full registry NAME.
     *
     * @param potionName The potion's NAME
     */
    @Override
    public Potion setPotionName(final String potionName) {
        this.setRegistryName(potionName);
        final ResourceLocation registryName = Objects.requireNonNull(this.getRegistryName());
        super.setPotionName("effect." + registryName.toString());
        return this;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public PotionBase(
            final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name) {
        this(isBadEffect, new Color(liquidR, liquidG, liquidB).getRGB(), name);
    }

    public boolean getUseEnchantedEffect() {
        return this.useEnchantedEffect;
    }

    public PotionBase setUseEnchantedEffect(boolean useEnchantedEffect) {
        this.useEnchantedEffect = useEnchantedEffect;
        return this;
    }

    public boolean getShouldHaveSpecialPotions() {
        return this.shouldHaveSpecialPotions;
    }

    public PotionBase setShouldHaveSpecialPotions(boolean shouldHaveSpecialPotions) {
        this.shouldHaveSpecialPotions = shouldHaveSpecialPotions;
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
    @Override
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
    @Override
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