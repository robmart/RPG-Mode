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

package robmart.rpgmode.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.mana.IMana;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.client.helper.GuiHelper;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.helper.MathHelper;

/**
 * @author Robmart
 * Created on 2018-12-12
 */
public class RPGGuiIngame extends Gui {
    private static final ResourceLocation barTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/bars.png");
    private              Minecraft        mc;
    private              ScaledResolution scaledResolution;
    private              EntityPlayer     player;
    private              FontRenderer     fontRenderer;

    public RPGGuiIngame(Minecraft mc) {
        this.mc = mc;
        this.scaledResolution = new ScaledResolution(this.mc);
        this.player = this.mc.player;
        this.fontRenderer = mc.fontRenderer;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderFoodBar(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
            event.getType() == RenderGameOverlayEvent.ElementType.FOOD ||
            event.getType() == RenderGameOverlayEvent.ElementType.AIR ||
            event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)
            event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        this.scaledResolution = new ScaledResolution(this.mc);

        if (this.player == null)
            this.player = this.mc.player;

        if (mc.playerController.getCurrentGameType() != GameType.CREATIVE &&
            mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
            if (this.player.getRidingEntity() == null) {
                renderHealth();
                renderMana();
            }

            renderArmor();
            renderFood();
            renderAir();
        }
    }

    private void renderHealth() {
        final float scale = 0.5f;
        int xPos = (scaledResolution.getScaledWidth() / 2 - 91) * (int) MathHelper.inverse(scale);
        int yPos = (scaledResolution.getScaledHeight() - 39) * (int) MathHelper.inverse(scale);
        int length = 77;

        this.mc.mcProfiler.startSection("health");
        this.mc.getTextureManager().bindTexture(barTexture);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.color(1, 1, 1, 1);

        drawTexturedModalRect(xPos, yPos, 0, 0, 81 * (int) MathHelper.inverse(scale),
                              9 * (int) MathHelper.inverse(scale));

        drawTexturedModalRect(xPos + (2 * MathHelper.inverse(scale)),
                              yPos + (2 * MathHelper.inverse(scale)),
                              0, 9 * (int) MathHelper.inverse(scale),
                              (int) (this.player.getHealth() / this.player.getMaxHealth() * (float) length *
                                     (int) MathHelper.inverse(scale)),
                              6 * (int) MathHelper.inverse(scale) - (int) MathHelper.inverse(scale));

        String message = String.format("%s / %s", MaxHealthCapability.formatMaxHealth(player.getHealth()),
                                       MaxHealthCapability.formatMaxHealth(player.getMaxHealth()));

        if (ConfigurationHandler.shouldDisplayHealthValue())
            GuiHelper.drawStringWithBorder(fontRenderer, message,
                                           xPos + (length) - (this.fontRenderer.getStringWidth(message) / 2),
                                           yPos + (int) (2.5f * MathHelper.inverse(scale)), 8453920, 0);

        GlStateManager.scale(MathHelper.inverse(scale), MathHelper.inverse(scale), MathHelper.inverse(scale));
        this.mc.mcProfiler.endSection();
    }

    private void renderMana() {
        final float scale = 0.5f;
        int xPos = (this.scaledResolution.getScaledWidth() / 2 + 10) * (int) MathHelper.inverse(scale);
        int yPos = (scaledResolution.getScaledHeight() - 39) * (int) MathHelper.inverse(scale);
        int length = 77;
        IMana mana = ManaCapability.getMana(this.player);

        if (mana == null) return;

        this.mc.getTextureManager().bindTexture(barTexture);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.color(1, 1, 1, 1);

        if (!ConfigurationHandler.shouldMoveManaBar())
            yPos -= 10 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 0, 81 * (int) MathHelper.inverse(scale),
                              9 * (int) MathHelper.inverse(scale));

        drawTexturedModalRect(xPos + (2 * MathHelper.inverse(scale)),
                              yPos + (2 * MathHelper.inverse(scale)),
                              0, 14 * (int) MathHelper.inverse(scale),
                              (int) (mana.getMana() / mana.getMaxMana() * (float) length *
                                     (int) MathHelper.inverse(scale)),
                              6 * (int) MathHelper.inverse(scale) - (int) MathHelper.inverse(scale));

        String message = String.format("%s / %s", MaxHealthCapability.formatMaxHealth(mana.getMana()),
                                       MaxHealthCapability.formatMaxHealth(mana.getMaxMana()));

        if (ConfigurationHandler.shouldDisplayManaValue())
            GuiHelper.drawStringWithBorder(fontRenderer, message,
                                           xPos + (length) - (this.fontRenderer.getStringWidth(message) / 2),
                                           yPos + (int) (2.5f * MathHelper.inverse(scale)), 8453920, 0);

        GlStateManager.scale(MathHelper.inverse(scale), MathHelper.inverse(scale), MathHelper.inverse(scale));
    }

    private void renderArmor() {
    }

    private void renderFood() {
    }

    private void renderAir() {
    }
}
