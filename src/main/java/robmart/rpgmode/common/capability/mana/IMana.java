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

package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Robmart
 */
public interface IMana {
    String getOwnerType();

    /**
     * Get the max mana
     *
     * @return Current max amount of mana
     */
    float getMaxMana();

    /**
     * Set max mana
     *
     * @param amount The new max amount of mana
     */
    void setMaxMana(float amount);

    /**
     * Get mana
     *
     * @return The players amount of mana
     */
    float getMana();

    /**
     * Sets the current mana value
     *
     * @param amount The new mana value
     */
    void setMana(float amount);

    /**
     * Get regen speed
     *
     * @return The time (ms) it takes for mana to regen
     */
    float getRegenSpeed();

    /**
     * Sets the regeneration speed
     *
     * @param speed The speed at which the mana regenerates (in ms)
     */
    void setRegenSpeed(float speed);

    /**
     * Get regen amount
     *
     * @return The amount of mana the player regens every time
     */
    float getRegenAmount();

    /**
     * Changes the amount regenerated
     *
     * @param amount The new generation amount
     */
    void setRegenAmount(float amount);

    /**
     * Updates anything that needs to be updated each tick
     * Call this from LivingUpdateEvent or a Tickhandler
     */
    void onUpdate(EntityPlayer player);

    /**
     * Sets the current mana value to the max amount of mana
     */
    void restoreMana();

    /**
     * Consumes mana mana
     *
     * @param amount The amount that will be removed
     * @return If the mana can be consumed
     */
    boolean consumeMana(float amount);

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
