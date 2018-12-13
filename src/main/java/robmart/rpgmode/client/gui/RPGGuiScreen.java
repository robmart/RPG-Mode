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

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

/**
 * @author Robmart
 * Created on 9/14/2018
 */
public abstract class RPGGuiScreen extends GuiScreen {

    protected void drawBackgroundLayer() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getBackground());
        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());
    }

    public abstract ResourceLocation getBackground();

    public int getGuiLeft() {
        return (this.width - getXSize()) / 2;
    }

    public int getGuiTop() {
        return (this.height - getYSize()) / 2;
    }

    public abstract int getXSize();

    public abstract int getYSize();

    public void draw(int x, int y, int width, int height) {
        float widthRatio = 1.0f / width;
        float heightRatio = 1.0f / height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x + 0), (double) (y + height), 0).tex(0, (double) ((float) height * heightRatio))
                     .endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0)
                     .tex((double) ((float) width * widthRatio), (double) ((float) height * heightRatio)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + 0), 0).tex((double) ((float) width * widthRatio), 0)
                     .endVertex();
        bufferbuilder.pos((double) (x + 0), (double) (y + 0), 0).tex(0, 0).endVertex();
        tessellator.draw();
    }
}
