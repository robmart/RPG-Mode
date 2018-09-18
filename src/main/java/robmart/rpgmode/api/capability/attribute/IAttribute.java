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

package robmart.rpgmode.api.capability.attribute;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Robmart
 */
public interface IAttribute {
    /**
     * Get the strength value
     *
     * @return Current strength value
     */
    int getStrength(boolean withMod);

    /**
     * Set strength
     *
     * @param value The new strength value
     */
    void setStrength(int value, boolean shouldSync);

    /**
     * Get the strength modifier
     *
     * @return Current strength modifier
     */
    int getStrMod();

    /**
     * Get the dexterity value
     *
     * @return Current dexterity value
     */
    int getDexterity(boolean withMod);

    /**
     * Set dexterity
     *
     * @param value The new dexterity value
     */
    void setDexterity(int value, boolean shouldSync);

    /**
     * Get the dexterity modifier
     *
     * @return Current dexterity modifier
     */
    int getDexMod();

    /**
     * Get the intelligence value
     *
     * @return Current intelligence value
     */
    int getIntelligence(boolean withMod);

    /**
     * Set intelligence
     *
     * @param value The new intelligence value
     */
    void setIntelligence(int value, boolean shouldSync);

    /**
     * Get the intelligence modifier
     *
     * @return Current intelligence modifier
     */
    int getIntMod();

    /**
     * Get the constitution
     *
     * @return Current constitution value
     */
    int getConstitution(boolean withMod);

    /**
     * Set constitution
     *
     * @param value The new constitution value
     */
    void setConstitution(int value, boolean shouldSync);

    /**
     * Get the constitution modifier
     *
     * @return Current constitution modifier
     */
    int getConMod();

    /**
     * Get the wisdom
     *
     * @return Current wisdom value
     */
    int getWisdom(boolean withMod);

    /**
     * Set wisdom
     *
     * @param value The new wisdom value
     */
    void setWisdom(int value, boolean shouldSync);

    /**
     * Get the wisdom modifier
     *
     * @return Current wisdom modifier
     */
    int getWisMod();

    /**
     * Sets all attributes
     *
     * @param strength     New strength value
     * @param dexterity    New dexterity value
     * @param intelligence New intelligence value
     * @param constitution New constitution value
     * @param wisdom       new wisdom value
     */
    void setAttributes(int strength, int dexterity, int intelligence, int constitution, int wisdom);

    /**
     * Gets the attribute points
     *
     * @return Current attribute point value
     */
    int getAttributePoint();

    /**
     * Sets attribute points
     *
     * @param value The new attribute point value
     */
    void setAttributePoint(int value, boolean shouldSync);

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
