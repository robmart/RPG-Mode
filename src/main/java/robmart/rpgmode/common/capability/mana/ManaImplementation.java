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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.api.capability.mana.IMana;
import robmart.rpgmode.client.network.SyncPlayerMana;
import robmart.rpgmode.common.network.PacketDispatcher;

/**
 * @author Robmart
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

    ManaImplementation() {
        this.entity = null;
    }

    ManaImplementation(EntityLivingBase entity) {
        this.entity = entity;
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
    public void setMaxMana(float amount, boolean shouldSync) {
        this.maxMana = amount > 0 ? amount : 1;
        setMana(getMaxMana() < getMana() ? getMaxMana() : getMana(), shouldSync);

        if (shouldSync)
            this.synchronise();
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
    public void setMana(float amount, boolean shouldSync) {
        if (amount < 0)
            this.mana = 0;
        else
            this.mana = amount < getMaxMana() ? amount : getMaxMana();

        if (shouldSync)
            this.synchronise();
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
    public void setRegenSpeed(float speed, boolean shouldSync) {
        this.regenSpeed = speed;

        if (shouldSync)
            this.synchronise();
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
    public void setRegenAmount(float amount, boolean shouldSync) {
        this.regenAmount = amount;

        if (shouldSync)
            this.synchronise();
    }


    /**
     * Updates anything that needs to be updated each tick
     * Call this from LivingUpdateEvent or a Tickhandler
     */
    @Override
    public void onUpdate(EntityLivingBase entity) {
        if (!entity.world.isRemote && updateManaTimer())
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
     * Sets the current mana value to the max amount of mana
     */
    @Override
    public void restoreMana() {
        setMana(getMaxMana(), true);
    }

    /**
     * Consumes mana mana
     *
     * @param amount The amount that will be removed
     *
     * @return If the mana can be consumed
     */
    @Override
    public boolean consumeMana(float amount) {

        if (amount <= getMana() && getMana() != 0) {
            setMana(getMana() - amount, true);
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
        setMana(getMana() + amount, true);
    }

    @Override
    public void synchronise() {
        if (this.entity instanceof EntityPlayerMP)
            PacketDispatcher.sendTo(new SyncPlayerMana((EntityPlayer) this.entity), (EntityPlayerMP) this.entity);
    }

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    @Override
    public NBTTagCompound saveNBTData() {
        return saveNBTData(new NBTTagCompound());
    }

    /**
     * Saves the NBT data into specified NBT compound
     *
     * @param nbt The compound
     *
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
