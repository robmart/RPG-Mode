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

package robmart.rpgmode.api.capability.chunklevel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import static robmart.rpgmode.api.util.InjectionUtil.Null;

/**
 * @author Robmart
 * Created on 11/14/2018
 */
public interface IChunkLevel {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IChunkLevel.class)
    Capability<IChunkLevel> CHUNK_LEVEL_CAPABILITY = Null();

    /**
     * Calculates the chunks level based on the surrounding chunks and a bit of randomness
     */
    void calculateLevel();

    /**
     * Gets the chunks level
     *
     * @return the chunks level
     */
    int getLevel();

    /**
     * Sets the chunks level
     *
     * @param level the new level
     */
    void setLevel(int level);

    void synchronise();

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    NBTTagCompound saveNBTData();

    /**
     * Saves the NBT data into specified NBT compound
     *
     * @param compound The compound
     *
     * @return The saved nbt data
     */
    NBTTagCompound saveNBTData(NBTTagCompound compound);

    /**
     * Loads the NBT data from NBT compound
     *
     * @param compound The compound
     */
    void loadNBTData(NBTTagCompound compound);
}
