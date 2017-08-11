package robmart.rpgmode.common.capability.character;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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
public class CharacterImplementation implements ICharacter {
    /**
     * Whether the player has joined before or not
     */
    private boolean hasJoined = false;

    /**
     * The player that the capability is attached to
     */
    private final EntityPlayer player;

    @Override
    public String getOwnerType(){
        return "Default Implementation";
    }

    CharacterImplementation(){
        this.player = null;
    }

    CharacterImplementation(EntityPlayer player){
        this.player = player;
    }

    /**
     * Checks if the player has joined before
     *
     * @return Whether the player has joined before or not
     */
    @Override
    public boolean getHasJoined(){
        return this.hasJoined;
    }

    /**
     * Sets if the plaer has joined before
     *
     * @param hasJoined Whether the player has joined before
     */
    public void setHasJoined(boolean hasJoined){
        this.hasJoined = hasJoined;
    }

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    @Override
    public NBTTagCompound saveNBTData() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("hasJoined", this.getHasJoined());
        return nbt;
    }

    /**
     * Saves the NBT data into specified NBT compound
     *
     * @param nbt The compound
     * @return The saved nbt data
     */
    @Override
    public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
        nbt.setBoolean("hasJoined", this.getHasJoined());
        return nbt;
    }

    /**
     * Loads the NBT data from NBT compound
     *
     * @param nbt The compound
     */
    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        this.setHasJoined(nbt.getBoolean("hasJoined"));
    }
}
