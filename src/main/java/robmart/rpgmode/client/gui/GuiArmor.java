package robmart.rpgmode.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Robmart.
 *         <p>
 *         This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *         Copyright (C) 2017 Robmart
 *         <p>
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU Lesser General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU Lesser General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU Lesser General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class GuiArmor extends Gui {
    private Minecraft mc;

    public GuiArmor(Minecraft mc) {
        this.mc = mc;
    }

    /**
     * Stops the normal air from rendering
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderArmor(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)
            event.setCanceled(true);
    }

    /**
     * Render air bubbles in the right position
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    //@SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;

        if (mc.playerController.getCurrentGameType() == GameType.CREATIVE || mc.playerController.getCurrentGameType() == GameType.SPECTATOR)
            return;

        this.mc.getTextureManager().bindTexture(ICONS);
        EntityPlayer entityPlayer = (EntityPlayer) this.mc.getRenderViewEntity();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);

        int j1 = scaledRes.getScaledHeight() - 49;
        int i3 = entityPlayer.getTotalArmorValue();
        int l = scaledRes.getScaledWidth() / 2 - 91;

        this.mc.mcProfiler.startSection("armor");

        for (int k3 = 0; k3 < 10; ++k3) {
            if (i3 > 0) {
                int l3 = l + k3 * 8;

                if (k3 * 2 + 1 < i3)
                    this.drawTexturedModalRect(l3, j1, 34, 9, 9, 9);

                if (k3 * 2 + 1 == i3)
                    this.drawTexturedModalRect(l3, j1, 25, 9, 9, 9);

                if (k3 * 2 + 1 > i3)
                    this.drawTexturedModalRect(l3, j1, 16, 9, 9, 9);
            }
        }
    }
}
