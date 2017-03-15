package robmart.rpgmode.client.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
public class GuiAir extends Gui{

    private Minecraft mc;

    public GuiAir(Minecraft mc){
        this.mc = mc;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderAir(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
            event.setCanceled(true);
    }

    @SubscribeEvent(priority =  EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH)
            return;

        EntityPlayer  entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
        ScaledResolution scaledRes = new ScaledResolution(mc);

        int i1 = scaledRes.getScaledWidth() / 2 + 91;


        if (entityPlayer.isInsideOfMaterial(Material.WATER)) {
            int i6 = this.mc.thePlayer.getAir();
            int k6 = MathHelper.ceiling_double_int((double) (i6 - 2) * 10.0D / 300.0D);
            int i7 = MathHelper.ceiling_double_int((double) i6 * 10.0D / 300.0D) - k6;
            int j1 = scaledRes.getScaledHeight() - 59;

            for (int k7 = 0; k7 < k6 + i7; ++k7) {
                if (k7 < k6) {
                    this.drawTexturedModalRect(i1 - k7 * 8 - 9, j1, 16, 18, 9, 9);
                } else {
                    this.drawTexturedModalRect(i1 - k7 * 8 - 9, j1, 25, 18, 9, 9);
                }
            }
        }
    }
}
