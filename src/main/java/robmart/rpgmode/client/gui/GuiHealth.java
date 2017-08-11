package robmart.rpgmode.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.reference.Reference;

import java.text.DecimalFormat;

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
public class GuiHealth extends Gui {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/bars.png");
    private Minecraft mc;


    public GuiHealth(Minecraft mc) {
        this.mc = mc;
    }

    /**
     * Stops the normal health from rendering
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderFoodBar(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
            event.setCanceled(true);
    }

    /**
     * Render new health bar
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        EntityPlayer player = this.mc.player;

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;

        if (mc.playerController.getCurrentGameType() == GameType.CREATIVE || mc.playerController.getCurrentGameType() == GameType.SPECTATOR)
            return;

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        Entity entity = this.mc.player.getRidingEntity();

        if (entity != null)
            return;

        this.mc.getTextureManager().bindTexture(texture);
        int xPos = (scaledResolution.getScaledWidth() / 2 - 91) * 2;
        int yPos = (scaledResolution.getScaledHeight() - 39) * 2;
        int barLength = 77;
        int healthBarWidth = (int) ((player.getHealth() / player.getMaxHealth()) * barLength);
        FontRenderer fontRenderer = this.mc.fontRenderer;
        DecimalFormat decimalFormat = new DecimalFormat("#");

        this.mc.mcProfiler.startSection("health");

        GlStateManager.pushAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        drawTexturedModalRect(xPos, yPos, 0, 0, 81 * 2, 9 * 2);

        xPos += 2 * 2;
        yPos += 2 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 9 * 2, healthBarWidth * 2, 6 * 2 - 2);

        String health = String.valueOf(player.getHealth());
        health = !health.contains(".") ? health : health.replaceAll("0*$", "").replaceAll("\\.$", "");
        if (((health.lastIndexOf(".") + 3) >= 0) && (health.lastIndexOf(".") + 3) < health.length())
            health = !health.contains(".") ? health : health.replace(health.substring(health.lastIndexOf(".") + 3), "");
        String maxHealth = String.valueOf(player.getMaxHealth());
        maxHealth = !maxHealth.contains(".") ? maxHealth : maxHealth.replaceAll("0*$", "").replaceAll("\\.$", "");
        if (((maxHealth.lastIndexOf(".") + 3) >= 0) && (maxHealth.lastIndexOf(".") + 3) < maxHealth.length())
            maxHealth = !maxHealth.contains(".") ? maxHealth : maxHealth.replace(maxHealth.substring(maxHealth.lastIndexOf(".") + 3), "");
        String message = "Health " + health + "/" + maxHealth;

        xPos += (barLength) - (fontRenderer.getStringWidth(message) / 2);
        yPos += 1;

        if (ConfigurationHandler.displayHealthValue) {
            fontRenderer.drawString(message, xPos + 1, yPos, 0);
            fontRenderer.drawString(message, xPos - 1, yPos, 0);
            fontRenderer.drawString(message, xPos, yPos + 1, 0);
            fontRenderer.drawString(message, xPos, yPos - 1, 0);
            fontRenderer.drawString(message, xPos, yPos, 8453920);
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.scale(2, 2, 2);
        GlStateManager.popAttrib();
    }
}
