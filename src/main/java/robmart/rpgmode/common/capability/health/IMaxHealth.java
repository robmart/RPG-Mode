package robmart.rpgmode.common.capability.health;

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
public interface IMaxHealth {
    String getOwnerType();

    /**
     * Get the bonus max health.
     *
     * @return The bonus max health
     */
    float getBonusMaxHealth();

    /**
     * Set the bonus max health.
     *
     * @param bonusMaxHealth The bonus max health
     */
    void setBonusMaxHealth(float bonusMaxHealth);

    /**
     * Add an amount to the current bonus max health.
     *
     * @param healthToAdd The amount of health to add
     */
    void addBonusMaxHealth(float healthToAdd);

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