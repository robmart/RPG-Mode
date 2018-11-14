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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.chunklevel.IChunkLevel;
import robmart.rpgmode.common.capability.chunklevel.ChunkLevelCapability;

/**
 * @author Robmart
 * Created on 11/14/2018
 */
public class GuiChunkLevel extends Gui {
    private Minecraft mc;

    public GuiChunkLevel(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;

        FontRenderer fontRenderer = mc.fontRenderer;
        IChunkLevel chunkLevel =
                ChunkLevelCapability.getChunkLevel(mc.player.world.getChunkFromBlockCoords(mc.player.getPosition()));

        GlStateManager.scale(4, 4, 4);
        fontRenderer.drawString(String.valueOf(chunkLevel.getLevel()), 5, 5, 0);
        GlStateManager.scale(0.25, 0.25, 0.25);

    }
}
