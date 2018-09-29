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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.api.capability.character.ICharacter;

/**
 * @author Robmart
 * Created on 9/26/2018
 */
public class MobCharacterImplementation implements ICharacter {
    /**
     * The entity the capability is attached to
     */
    private EntityLivingBase entity;

    /**
     * The players level
     */
    private int level;

    MobCharacterImplementation() {
        this.entity = null;
    }

    MobCharacterImplementation(EntityLivingBase entity) {
        this.entity = entity;
    }

    @Override
    public int getEXP() {
        return 0;
    }

    @Override
    public void setEXP(int exp) {
    }

    @Override
    public void addEXP(int exp) {
    }

    @Override
    public int getEXPRequired() {
        return 1;
    }

    @Override
    public int getEXPForLevel(int level) {
        return 1;
    }

    @Override
    public int getTotalEXP() {
        return 1;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void levelUp() {
    }

    @Override
    public void synchronise() {

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
