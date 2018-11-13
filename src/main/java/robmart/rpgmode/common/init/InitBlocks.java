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

package robmart.rpgmode.common.init;

import net.minecraftforge.common.EnumPlantType;
import robmart.rpgmode.api.block.RPGBlocks;
import robmart.rpgmode.api.reference.RefBlockNames;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.block.BlockRPGBush;
import robmart.rpgmode.common.util.ReflectionUtil;

/**
 * @author Robmart
 */
public class InitBlocks {

    private InitBlocks() {
    }

    public static void init() {
        try {
            ReflectionUtil.setField(RPGBlocks.class, "HELL_FLOWER",
                                    new BlockRPGBush(RefBlockNames.HELL_FLOWER, EnumPlantType.Nether));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            RPGMode.logger.error(e);
        }
    }
}
