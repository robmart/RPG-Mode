package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.client.network.SyncPlayerMana;
import robmart.rpgmode.common.network.PacketDispatcher;

/**
 * @author Robmart.
 *
 *   This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *   Copyright (C) 2017 Robmart
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ManaImplementation implements IMana {
    /**
     * The max amount of mana
     */
    private float maxMana = 100,
    /**
     * The current amount of mana
     */
            mana = maxMana,
    /**
     * The speed at which mana regenerates
     */
            regenSpeed = 100,
    /**
     * How long until next regeneration
     */
            regenTimer = regenSpeed,
    /**
     * How much is regenerated
     */
            regenAmount = 1;

    /**
     * The entity the capability is attached to
     */
    private final EntityLivingBase entity;

    ManaImplementation(){
        this.entity = null;
    }

    ManaImplementation(EntityLivingBase entity) {
        this.entity = entity;
    }

    @Override
    public String getOwnerType(){
        return "Default Implementation";
    }

    /**
     * Get the max mana
     *
     * @return Current max amount of mana
     */
    @Override
    public float getMaxMana() {
        return this.maxMana;
    }

    /**
     * Set max mana
     *
     * @param amount The new max amount of mana
     */
    @Override
    public void setMaxMana(float amount) {
        this.maxMana = amount > 0 ? amount : 1;
        setMana(getMaxMana() < getMana() ? getMaxMana() : getMana());
        if (this.entity instanceof EntityPlayerMP)
            PacketDispatcher.sendTo(new SyncPlayerMana((EntityPlayer) this.entity), (EntityPlayerMP) this.entity);
    }

    /**
     * Get mana
     *
     * @return The players amount of mana
     */
    @Override
    public float getMana() {
        return this.mana;
    }

    /**
     * Sets the current mana value
     *
     * @param amount The new mana value
     */
    @Override
    public void setMana(float amount) {
        this.mana = amount > 0 ? (amount < getMaxMana() ? amount : getMaxMana()) : 0;
        if (this.entity instanceof EntityPlayerMP)
            PacketDispatcher.sendTo(new SyncPlayerMana((EntityPlayer) this.entity), (EntityPlayerMP) this.entity);
    }

    /**
     * Get regen speed
     *
     * @return The time (ms) it takes for mana to regen
     */
    @Override
    public float getRegenSpeed() {
        return this.regenSpeed;
    }

    /**
     * Sets the regeneration speed
     *
     * @param speed The speed at which the mana regenerates (in ms)
     */
    @Override
    public void setRegenSpeed(float speed) {
        this.regenSpeed = speed;
    }

    /**
     * Get regen amount
     *
     * @return The amount of mana the entity regens every time
     */
    @Override
    public float getRegenAmount() {
        return this.regenAmount;
    }

    /**
     * Changes the amount regenerated
     *
     * @param amount The new generation amount
     */
    @Override
    public void setRegenAmount(float amount) {
        this.regenAmount = amount;
    }


    /**
     * Updates anything that needs to be updated each tick
     * Call this from LivingUpdateEvent or a Tickhandler
     */
    @Override
    public void onUpdate(EntityPlayer player) {
        if (!player.world.isRemote && updateManaTimer())
            regenMana(getRegenAmount());
    }

    /**
     * Checks if the entity should regen mana
     *
     * @return Weather the entity can regen mana or not
     */
    public boolean updateManaTimer() {
        if (this.regenTimer > 0) {
            --this.regenTimer;
        }
        if (this.regenTimer == 0) {
            this.regenTimer = getMana() < getMaxMana() ? getRegenSpeed() : 0;
            return true;
        }

        return false;
    }

    /**
     * Regenerates the players mana
     *
     * @param amount The amount that will be regenerated
     */
    public final void regenMana(float amount) {
        setMana(getMana() + amount);
    }


    /**
     * Sets the current mana value to the max amount of mana
     */
    @Override
    public void restoreMana(){
        setMana(getMaxMana());
    }

    /**
     * Consumes mana mana
     *
     * @param amount The amount that will be removed
     * @return If the mana can be consumed
     */
    @Override
    public boolean consumeMana(float amount) {

        if (amount <= getMana() && getMana() != 0) {
            setMana(getMana() - amount);
            return true;
        }

        return false;

    }

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    @Override
    public NBTTagCompound saveNBTData() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("maxMana", getMaxMana());
        nbt.setFloat("currentMana", getMana());
        nbt.setFloat("regenSpeed", getRegenSpeed());
        nbt.setFloat("regenAmount", getRegenAmount());
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
        nbt.setFloat("maxMana", getMaxMana());
        nbt.setFloat("currentMana", getMana());
        nbt.setFloat("regenSpeed", getRegenSpeed());
        nbt.setFloat("regenAmount", getRegenAmount());
        return nbt;
    }

    /**
     * Loads the NBT data from NBT compound
     *
     * @param nbt The compound
     */
    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        this.maxMana = nbt.getFloat("maxMana");
        this.mana = nbt.getFloat("currentMana");
        this.regenSpeed = nbt.getFloat("regenSpeed");
        this.regenAmount = nbt.getFloat("regenAmount");
    }
}
