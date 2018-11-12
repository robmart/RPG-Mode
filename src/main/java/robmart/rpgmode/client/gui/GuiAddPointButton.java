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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import robmart.rpgmode.api.capability.attribute.IAttribute;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.RPGMode;

import java.lang.reflect.Method;

/**
 * @author Robmart
 * Created on 9/15/2018
 */
public class GuiAddPointButton extends GuiButton {
    public static final ResourceLocation icon         =
            new ResourceLocation(Reference.MOD_ID, "textures/gui/add_attribute.png");
    public static final ResourceLocation iconSelected =
            new ResourceLocation(Reference.MOD_ID, "textures/gui/add_attribute_selected.png");

    public final int posX;
    public final int posY;

    private RPGGuiScreen parentGui;
    private IAttribute   attributes;
    private Method       setMethod;
    private Method       getMethod;

    public GuiAddPointButton(
            RPGGuiScreen parentGui, IAttribute attributes, Method setMethod, Method getMethod,
            int buttonId, int x, int y) {
        super(buttonId, x, y, 16, 16, "");

        this.parentGui = parentGui;
        this.attributes = attributes;
        this.setMethod = setMethod;
        this.getMethod = getMethod;
        this.posX = x;
        this.posY = y;

        visible = true;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        visible = attributes.getAttributePoint() > 0;
        if (this.visible) {
            this.hovered = mouseX >= x && mouseY >= this.y && mouseX < x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            if (k != 1)
                mc.getTextureManager().bindTexture(iconSelected);
            else
                mc.getTextureManager().bindTexture(icon);

            this.x = (posX + parentGui.getGuiLeft());
            this.y = posY + parentGui.getGuiTop();
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.scale(1f, 1f, 1f);
            parentGui.draw(x, y, 16, 16);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed) {
            try {
                setMethod.invoke(attributes, ((int) getMethod.invoke(attributes, false)) + 1, true);
                attributes.setAttributePoint(attributes.getAttributePoint() - 1, true);
            } catch (Exception e) {
                RPGMode.logger.error(e);
            }
        }
        return pressed;
    }
}
