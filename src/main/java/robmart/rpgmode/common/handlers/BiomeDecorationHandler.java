package robmart.rpgmode.common.handlers;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.init.InitBlocks;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart.
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
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@SuppressWarnings("unused")
public final class BiomeDecorationHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWorldDecoration(DecorateBiomeEvent.Decorate event) {
        if ((event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT) && event.getType() == DecorateBiomeEvent.Decorate.EventType.SHROOM) {
            if (event.getWorld().provider.isNether()) {
                int dist = Math.min(8, Math.max(1, 4));
                for (int i = 0; i < 1; i++) {
                    if (event.getRand().nextInt(8) == 0) {
                        int x = event.getPos().getX() + event.getRand().nextInt(16) + 8;
                        int z = event.getPos().getZ() + event.getRand().nextInt(16) + 8;
                        int y = event.getWorld().getTopSolidOrLiquidBlock(event.getPos()).getY();

                        for (int j = 0; j < 1 * 8; j++) {
                            int x1 = x + event.getRand().nextInt(dist * 2) - dist;
                            int y1 = y + event.getRand().nextInt(4) - event.getRand().nextInt(4);
                            int z1 = z + event.getRand().nextInt(dist * 2) - dist;
                            BlockPos pos2 = new BlockPos(x1, y1, z1);
                            if (event.getWorld().isAirBlock(pos2) && InitBlocks.HELL_FLOWER.canPlaceBlockAt(event.getWorld(), pos2)) {
                                event.getWorld().setBlockState(pos2, InitBlocks.HELL_FLOWER.getDefaultState());
                                System.out.println("Placed flower " + pos2);
                            }
                        }
                    }
                }
            }
        }
    }
}
