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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.reference.RefGuiId;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart
 * Created on 9/14/2018
 */
public class GuiAttributesButton extends GuiButton {
    public static ResourceLocation icon = new ResourceLocation(Reference.MOD_ID, "textures/gui/character.png");
    public static ResourceLocation iconSelected = new ResourceLocation(
            Reference.MOD_ID,
            "textures/gui/character_selected.png");
    private       GuiContainer     parentGui;
    private       int              pos;

    public GuiAttributesButton(int buttonId, GuiContainer parentGui, int x, int y, int width, int height) {
        super(buttonId, x, y, width, height, "");
        this.parentGui = parentGui;
        visible = true;
        pos = x;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.hovered = mouseX >= x && mouseY >= this.y && mouseX < x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            if (k != 1)
                mc.getTextureManager().bindTexture(iconSelected);
            else
                mc.getTextureManager().bindTexture(icon);

            this.x = pos + parentGui.getGuiLeft();
            int width = 20;
            int height = 18;
            float widthRatio = 1.0f / 20f;
            float heightRatio = 1.0f / 18f;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexBuffer = tessellator.getBuffer();
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexBuffer.pos((double) (x + 0), (double) (y + height), 0).tex(0, (double) ((float) height * heightRatio))
                        .endVertex();
            vertexBuffer.pos((double) (x + width), (double) (y + height), 0)
                        .tex((double) ((float) width * widthRatio), (double) ((float) height * heightRatio))
                        .endVertex();
            vertexBuffer.pos((double) (x + width), (double) (y + 0), 0).tex((double) ((float) width * widthRatio), 0)
                        .endVertex();
            vertexBuffer.pos((double) (x + 0), (double) (y + 0), 0).tex(0, 0).endVertex();
            tessellator.draw();
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed) {
            if (parentGui instanceof GuiInventory) {
                mc.player.openGui(RPGMode.instance, RefGuiId.GuiAttributesId, mc.player.world, (int) mc.player.posX,
                                  (int) mc.player.posY, (int) mc.player.posZ);
            }
            else {
                //PacketHandler.INSTANCE.sendToServer(new PacketOpenNormalInventory());
            }
        }
        return pressed;
    }
}
