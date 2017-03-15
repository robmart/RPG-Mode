package robmart.rpgmode.common.capability.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

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
public class ManaStorage implements Capability.IStorage<IMana> {

    @Override
    public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing facing){
        return instance.saveNBTData();
    }

    @Override
    public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing facing, NBTBase nbt){
        instance.loadNBTData((NBTTagCompound) nbt);
    }
}
