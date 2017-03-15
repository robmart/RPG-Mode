package robmart.rpgmode.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.capability.mana.IMana;
import robmart.rpgmode.common.capability.mana.ManaProvider;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.reference.Reference;

/**
 * Created by Robmart.
 *
 *   This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *   Copyright (C) 2017 Robmart
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class GuiManaBar extends Gui {
    private Minecraft mc;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/Bars.png");

    public GuiManaBar(Minecraft mc){
        super();
        this.mc = mc;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ARMOR)
            return;

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        IMana mana = ManaProvider.get(this.mc.thePlayer);

        if (mana == null || mana.getMaxMana() == 0){
            RPGMode.logger.error("[RPGMode] ...Well the mana bar's fucked");
            return;
        }

        Entity entity = this.mc.thePlayer.getRidingEntity();

        if (entity != null)
            return;

        this.mc.getTextureManager().bindTexture(texture);
        int xPos = (scaledResolution.getScaledWidth() / 2 + 10) * 2;
        int yPos = (scaledResolution.getScaledHeight() - 39) * 2;
        int barLength = 77;
        int manabarwidth = (int)(((float) mana.getMana() / mana.getMaxMana()) * barLength);
        FontRenderer fontRenderer = this.mc.fontRendererObj;

        GlStateManager.pushAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        if (!ConfigurationHandler.moveManaBar)
            yPos -= 10 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 0, 81 * 2 , 9 * 2);

        xPos += 2 * 2;
        yPos += 2 * 2;

        drawTexturedModalRect(xPos, yPos, 0, 14 * 2, manabarwidth * 2, 6 * 2 - 2);

        String message = "Mana " + mana.getMana() + "/" + mana.getMaxMana();

        xPos += (barLength) - (fontRenderer.getStringWidth(message) / 2);
        yPos += 1;

        if (ConfigurationHandler.displayManaValue) {
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
