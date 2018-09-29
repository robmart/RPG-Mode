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
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.mana.IMana;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.handlers.ConfigurationHandler;

/**
 * @author Robmart
 */
public class GuiMana extends Gui {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/bars.png");
    private              Minecraft        mc;

    public GuiMana(Minecraft mc) {
        super();
        this.mc = mc;
    }

    /**
     * Renders a mana bar
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        if (mc.playerController.getCurrentGameType() == GameType.CREATIVE ||
            mc.playerController.getCurrentGameType() == GameType.SPECTATOR) return;

        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        IMana mana = ManaCapability.getMana(this.mc.player);

        Entity entity = this.mc.player.getRidingEntity();

        if (entity != null) return;

        this.mc.getTextureManager().bindTexture(texture);
        int xPos = (scaledResolution.getScaledWidth() / 2 + 10) * 2;
        int yPos = (scaledResolution.getScaledHeight() - 39) * 2;
        int barLength = 77;
        int manaBarWidth = (int) ((mana.getMana() / mana.getMaxMana()) * barLength);
        FontRenderer fontRenderer = this.mc.fontRenderer;

        GlStateManager.pushAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        if (!ConfigurationHandler.shouldMoveManaBar())
            yPos -= 10 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 0, 81 * 2, 9 * 2);

        xPos += 2 * 2;
        yPos += 2 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 14 * 2, manaBarWidth * 2, 6 * 2 - 2);

        String message = String.format("%s / %s", MaxHealthCapability.formatMaxHealth(mana.getMana()),
                                       MaxHealthCapability.formatMaxHealth(mana.getMaxMana()));

        xPos += (barLength) - (fontRenderer.getStringWidth(message) / 2);
        yPos += 1;

        if (ConfigurationHandler.shouldDisplayManaValue()) {
            fontRenderer.drawString(message, xPos + 1, yPos, 0);
            fontRenderer.drawString(message, xPos - 1, yPos, 0);
            fontRenderer.drawString(message, xPos, yPos + 1, 0);
            fontRenderer.drawString(message, xPos, yPos - 1, 0);
            fontRenderer.drawString(message, xPos, yPos, 8453920);
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.scale(2, 2, 2);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popAttrib();
    }
}
