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

package robmart.rpgmode.client.handlers;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.init.InitItems;

/**
 * @author Robmart
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public class ModelHandler {

    /**
     * Register the {@link IItemColor} handlers
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void registerItemColorHandlers(final ColorHandlerEvent.Item event) {
        final ItemColors itemColors = event.getItemColors();

        final IItemColor potionColorHandler = (stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack);

        itemColors.registerItemColorHandler(potionColorHandler, InitItems.POTION, InitItems.SPLASH_POTION,
                                            InitItems.LINGERING_POTION);
    }

    private ModelHandler() {
    }
}
