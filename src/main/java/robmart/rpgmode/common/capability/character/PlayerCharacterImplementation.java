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

package robmart.rpgmode.common.capability.character;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.api.capability.attribute.IAttribute;
import robmart.rpgmode.api.capability.character.ICharacter;
import robmart.rpgmode.client.network.SyncPlayerCharacter;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.network.PacketDispatcher;

/**
 * @author Robmart
 * Created on 9/26/2018
 */
public class PlayerCharacterImplementation implements ICharacter {
    /**
     * The player the capability is attached to
     */
    private EntityPlayer player;

    /**
     * The players level
     */
    private int level;

    /**
     * The players EXP
     */
    private int exp;

    PlayerCharacterImplementation() {
        this.player = null;
    }

    PlayerCharacterImplementation(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public int getEXP() {
        return this.exp;
    }

    @Override
    public void setEXP(int exp) {
        this.exp = exp;

        if (getEXP() >= getEXPRequired()) {
            this.exp -= getEXPRequired();
            levelUp();
        }

        if (this.exp < 0)
            this.exp = 0;
    }

    @Override
    public void addEXP(int exp) {
        setEXP(getEXP() + exp);
    }

    @Override
    public int getEXPRequired() {
        return getEXPForLevel(getLevel() + 1);
    }

    @Override
    public int getEXPForLevel(int level) {
        return 500 + (int) Math.round(level * Math.pow(level, 1.5));
    }

    @Override
    public int getTotalEXP() {
        int leveli = getLevel();
        int totalEXP = 0;

        while (leveli > 0)
            totalEXP += getEXPForLevel(leveli--);

        return totalEXP + getEXP();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        if (level > getLevel())
            while (level > getLevel())
                levelUp();
        else
            this.level = level;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        IAttribute attribute = AttributeCapability.getAttributes(player);
        attribute.setAttributePoint(attribute.getAttributePoint() + 4, true);
    }

    @Override
    public void synchronise() {
        if (player instanceof EntityPlayerMP)
            PacketDispatcher.sendTo(new SyncPlayerCharacter(this.player), (EntityPlayerMP) this.player);
    }

    @Override
    public NBTTagCompound saveNBTData() {
        return saveNBTData(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound saveNBTData(NBTTagCompound compound) {
        compound.setInteger("level", getLevel());
        compound.setInteger("exp", getEXP());
        return compound;
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.level = compound.getInteger("level");
        setEXP(compound.getInteger("exp"));
    }
}
