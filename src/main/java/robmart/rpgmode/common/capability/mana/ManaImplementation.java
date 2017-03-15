package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.client.network.SyncPlayerMana;
import robmart.rpgmode.common.network.PacketDispatcher;

/**
 * Created by Robmart.
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
    private int maxMana = 100, mana = maxMana, regenSpeed = 100, regenTimer = regenSpeed, regenAmount = 1;
    private EntityPlayer player;

    ManaImplementation(){}

    ManaImplementation (EntityPlayer player){
        this.player = player;
    }

    @Override
    public String getOwnerType(){
        return "Default Implementation";
    }

    /**
     * @return Current amount of mana
     */
    @Override
    public int getMaxMana(){
        return this.maxMana;
    }

    /**
     * @return The players max amount of mana
     */
    @Override
    public int getMana(){
        return this.mana;
    }

    /**
     * @return The time (ms) it takes for mana to regen
     */
    @Override
    public int getRegenSpeed(){
        return this.regenSpeed;
    }

    /**
     * @return The amount of mana the player regens every time
     */
    @Override
    public int getRegenAmount(){
        return this.regenAmount;
    }

    /**
     * Sets the max mana to specified amount
     * @param amount The new max mana
     */
    @Override
    public void setMaxMana(int amount) {
        this.maxMana = amount > 0 ? amount : 1;
        setMana(getMaxMana() < getMana() ? getMaxMana() : getMana());
        PacketDispatcher.sendTo(new SyncPlayerMana(this.player), (EntityPlayerMP) this.player);
    }

    /**
     * Sets the current mana value
     * @param amount The new mana value
     */
    @Override
    public void setMana(int amount){
        this.mana = amount > 0 ? (amount < getMaxMana() ? amount : getMaxMana()) : 0;
        PacketDispatcher.sendTo(new SyncPlayerMana(this.player), (EntityPlayerMP) this.player);
        //System.out.println(this.mana);
    }

    /**
     * Sets the regeneration speed
     * @param speed The speed at which the mana regenerates (in milliseconds)
     */
    @Override
    public void setRegenSpeed(int speed){
        this.regenSpeed = speed;
    }

    @Override
    public void setRegenAmount(int amount){
        this.regenAmount = amount;
    }


    /**
     * Updates anything that needs to be updated each tick
     * Call this from LivingUpdateEvent or a Tickhandler
     */
    @Override
    public void onUpdate(EntityPlayer player) {
        if (!player.worldObj.isRemote && updateManaTimer())
            regenMana(getRegenAmount());
    }

    /**
     * Checks if the player should regen mana
     * @return Weather the player can regen mana or not
     */
    @Override
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
     * @param amount The amount that will be regenerated
     */
    @Override
    public final void regenMana(int amount) {
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
     * Removes mana
     * @param amount The amount that will be removed
     * @return If the mana can be consumed
     */
    @Override
    public boolean consumeMana(int amount) {

        if (amount <= getMana() && getMana() != 0) {
            setMana(getMana() - amount);
            return true;
        }

        return false;

    }

    @Override
    public NBTTagCompound saveNBTData() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("maxMana", getMaxMana());
        nbt.setInteger("currentMana", getMana());
        nbt.setInteger("regenSpeed", getRegenSpeed());
        nbt.setInteger("regenAmount", getRegenAmount());
        return nbt;
    }

    @Override
    public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("maxMana", getMaxMana());
        nbt.setInteger("currentMana", getMana());
        nbt.setInteger("regenSpeed", getRegenSpeed());
        nbt.setInteger("regenAmount", getRegenAmount());
        return nbt;
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        this.maxMana = nbt.getInteger("maxMana");
        this.mana = nbt.getInteger("currentMana");
        this.regenSpeed = nbt.getInteger("regenSpeed");
        this.regenAmount = nbt.getInteger("regenAmount");
    }
}
