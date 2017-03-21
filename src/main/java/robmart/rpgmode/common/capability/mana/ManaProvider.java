package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

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
public class ManaProvider implements ICapabilitySerializable, ICapabilityProvider{

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMana.class)
    public static final Capability<IMana> MANA_CAPABILITY = null;

    private IMana manaCap = null;

    public ManaProvider(){
        this.manaCap = new ManaImplementation();
    }

    public ManaProvider(EntityPlayer player){
        this.manaCap = new ManaImplementation(player);
    }

    public ManaProvider(IMana mana){
        this.manaCap = mana;
    }

    public static IMana get(EntityPlayer player){
        if (player.hasCapability(MANA_CAPABILITY, null))
            return player.getCapability(MANA_CAPABILITY, null);

        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == MANA_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if (capability == MANA_CAPABILITY)
            return MANA_CAPABILITY.cast(manaCap);

        return null;
    }

    @Override
    public NBTBase serializeNBT(){
        return manaCap.saveNBTData();
    }

    @Override
    public void deserializeNBT(NBTBase nbt){
        manaCap.loadNBTData((NBTTagCompound) nbt);
    }
}
