package robmart.rpgmode.common.capability.health;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author Robmart.
 *         <p>
 *         This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *         Copyright (C) 2017 Robmart
 *         <p>
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU Lesser General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU Lesser General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU Lesser General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class MaxHealthProvider implements ICapabilitySerializable, ICapabilityProvider {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMaxHealth.class)
    public static final Capability<IMaxHealth> MAX_HEALTH_CAPABILITY = null;

    private IMaxHealth maxHealthCap = null;

    public MaxHealthProvider() {
        this.maxHealthCap = new MaxHealthImplementation();
    }

    public MaxHealthProvider(EntityLivingBase enitity) {
        this.maxHealthCap = new MaxHealthImplementation(enitity);
    }

    public MaxHealthProvider(IMaxHealth maxHealth) {
        this.maxHealthCap = maxHealth;
    }

    public static IMaxHealth get(EntityPlayer player) {
        if (player.hasCapability(MAX_HEALTH_CAPABILITY, null))
            return player.getCapability(MAX_HEALTH_CAPABILITY, null);

        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == MAX_HEALTH_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == MAX_HEALTH_CAPABILITY)
            return MAX_HEALTH_CAPABILITY.cast(maxHealthCap);

        return null;
    }

    @Override
    public NBTBase serializeNBT() {
        return maxHealthCap.saveNBTData();
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        maxHealthCap.loadNBTData((NBTTagCompound) nbt);
    }
}
