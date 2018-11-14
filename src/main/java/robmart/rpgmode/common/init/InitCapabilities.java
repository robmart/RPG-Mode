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

import net.minecraftforge.common.MinecraftForge;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.capability.character.CharacterCapability;
import robmart.rpgmode.common.capability.chunklevel.ChunkLevelCapability;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.handlers.CapabilityEventHandler;

/**
 * @author Robmart
 */
public class InitCapabilities {

    public static void init() {
        ManaCapability.register();
        MaxHealthCapability.register();
        AttributeCapability.register();
        CharacterCapability.register();
        ChunkLevelCapability.register();
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
    }

    private InitCapabilities() {
    }
}
