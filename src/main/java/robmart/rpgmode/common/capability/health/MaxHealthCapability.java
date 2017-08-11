package robmart.rpgmode.common.capability.health;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import robmart.rpgmode.common.capability.CapabilityProvider;
import robmart.rpgmode.common.helper.CapabilityHelper;

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
public class MaxHealthCapability {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMaxHealth.class)
    public static final Capability<IMaxHealth> MAX_HEALTH_CAPABILITY = null;

    private IMaxHealth maxHealthCap = null;

    public MaxHealthCapability() {
        this.maxHealthCap = new MaxHealthImplementation();
    }

    public MaxHealthCapability(EntityLivingBase entity) {
        this.maxHealthCap = new MaxHealthImplementation(entity);
    }

    public MaxHealthCapability(IMaxHealth maxHealth) {
        this.maxHealthCap = maxHealth;
    }

    public static IMaxHealth get(EntityLivingBase entity) {
        return CapabilityHelper.getCapability(entity, MAX_HEALTH_CAPABILITY, null);
    }

    public ICapabilityProvider createProvider() {
        return new CapabilityProvider<>(MAX_HEALTH_CAPABILITY, null, maxHealthCap);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IMaxHealth.class, new Capability.IStorage<IMaxHealth>() {
            @Override
            public NBTBase writeNBT(Capability<IMaxHealth> capability, IMaxHealth instance, EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(Capability<IMaxHealth> capability, IMaxHealth instance, EnumFacing side, NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new MaxHealthImplementation(null));
    }
}
