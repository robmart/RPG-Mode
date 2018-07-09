/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

package robmart.rpgmode.common.capability.health;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Choonster
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
