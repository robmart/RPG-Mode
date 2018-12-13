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

package robmart.rpgmode.client.helper;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Robmart
 * Created on 2018-12-13
 */
public class GuiHelper {

    public static void drawStringWithBackdrop(
            FontRenderer fontRenderer, Object message, int xPos, int yPos,
            int colorText, int colorBackdrop) {
        fontRenderer.drawString(message.toString(), xPos + 1, yPos + 1, colorBackdrop);
        fontRenderer.drawString(message.toString(), xPos, yPos, colorText);
    }

    public static void drawStringWithBorder(
            FontRenderer fontRenderer, Object message, int xPos, int yPos,
            int colorText, int colorBackdrop) {
        fontRenderer.drawString(message.toString(), xPos + 1, yPos, colorBackdrop);
        fontRenderer.drawString(message.toString(), xPos - 1, yPos, colorBackdrop);
        fontRenderer.drawString(message.toString(), xPos, yPos + 1, colorBackdrop);
        fontRenderer.drawString(message.toString(), xPos, yPos - 1, colorBackdrop);
        fontRenderer.drawString(message.toString(), xPos, yPos, colorText);
    }
}
