package robmart.rpgmode.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import robmart.rpgmode.common.handlers.ConfigurationHandler;

import java.util.Random;

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
public class GuiFood extends Gui {
    private final Random rand = new Random();
    private Minecraft mc;
    private int updateCounter;


    public GuiFood(Minecraft mc) {
        this.mc = mc;
    }

    /**
     * Stops normal hunger from rendering
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderFoodBar(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
            event.setCanceled(true);
    }

    /**
     * Renders hunger at new position
     *
     * @param event
     */
    @SubscribeEvent(priority =  EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;

        if (mc.playerController.getCurrentGameType() == GameType.CREATIVE || mc.playerController.getCurrentGameType() == GameType.SPECTATOR)
            return;

        this.mc.getTextureManager().bindTexture(ICONS);

        EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);

        this.rand.setSeed((long)(this.updateCounter * 312871));
        FoodStats foodstats = entityplayer.getFoodStats();
        int k = foodstats.getFoodLevel();
        int i1 = scaledRes.getScaledWidth() / 2 + 91;
        int j1 = scaledRes.getScaledHeight() - 49;
        if (!ConfigurationHandler.moveManaBar)
            j1 = scaledRes.getScaledHeight() - 39;

        int j6 = j1;

        EntityPlayer player = this.mc.player;
        Entity entity = player.getRidingEntity();

        if (entity == null) {
            GlStateManager.enableBlend();

            this.mc.mcProfiler.endStartSection("food");

            for (int l5 = 0; l5 < 10; ++l5) {
                int l6 = 16;
                int j7 = 0;

                if (player.isPotionActive(MobEffects.HUNGER)) {
                    l6 += 36;
                    j7 = 13;
                }

                if (player.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0) {
                    j6 = j1 + (this.rand.nextInt(3) - 1);
                }

                int l7 = i1 - l5 * 8 - 9;
                this.drawTexturedModalRect(l7, j6, 16 + j7 * 9, 27, 9, 9);

                if (l5 * 2 + 1 < k)
                    this.drawTexturedModalRect(l7, j6, l6 + 36, 27, 9, 9);

                if (l5 * 2 + 1 == k)
                    this.drawTexturedModalRect(l7, j6, l6 + 45, 27, 9, 9);
            }
        }

        GlStateManager.disableBlend();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onClientTick(TickEvent.ClientTickEvent event){
        ++this.updateCounter;
    }
}
