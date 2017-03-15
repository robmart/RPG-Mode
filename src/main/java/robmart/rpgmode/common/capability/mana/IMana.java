package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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
public interface IMana {
    String getOwnerType();

    int getMaxMana();
    int getMana();
    int getRegenSpeed();
    int getRegenAmount();
    void setMaxMana(int amount);
    void setMana(int amount);
    void setRegenSpeed(int speed);
    void setRegenAmount(int amount);

    void onUpdate(EntityPlayer player);
    boolean updateManaTimer();
    void regenMana(int amount);

    void restoreMana();
    boolean consumeMana(int amount);

    NBTTagCompound saveNBTData();
    NBTTagCompound saveNBTData(NBTTagCompound compound);
    void loadNBTData(NBTTagCompound compound);

}
