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

package robmart.rpgmode.api.capability.character;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Robmart
 * Created on 9/26/2018
 */
public interface ICharacter {

    /**
     * Gets the amount of EXP the character currently has
     *
     * @return the EXP
     */
    int getEXP();

    /**
     * Sets the amount of EXP character has
     *
     * @param exp the new EXP value
     */
    void setEXP(int exp);

    /**
     * Add EXP to character
     *
     * @param exp the EXP to be added
     */
    void addEXP(int exp);

    /**
     * Gets the EXP required to level up
     *
     * @return the required exp
     */
    int getEXPRequired();

    /**
     * Get exp required for level
     *
     * @param level the level
     *
     * @return the exp required for that level
     */
    int getEXPForLevel(int level);

    /**
     * Gets the total exp the character has acquired
     *
     * @return the total EXP acquired
     */
    int getTotalEXP();

    /**
     * Gets the characters level
     *
     * @return the level
     */
    int getLevel();

    /**
     * Sets the characters level
     *
     * @param level the characters new level
     */
    void setLevel(int level);

    /**
     * Levels the character up by one
     */
    void levelUp();

    /**
     * Synchronise the entity's attributes to watching clients.
     */
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
