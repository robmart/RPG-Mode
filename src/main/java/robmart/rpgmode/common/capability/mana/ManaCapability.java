package robmart.rpgmode.common.capability.mana;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
public class ManaCapability {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMana.class)
    public static final Capability<IMana> MANA_CAPABILITY = null;

    private IMana manaCap = null;

    public ManaCapability() {
        this.manaCap = new ManaImplementation();
    }

    public ManaCapability(EntityLivingBase enitity) {
        this.manaCap = new ManaImplementation(enitity);
    }

    public ManaCapability(IMana mana) {
        this.manaCap = mana;
    }

    public static IMana get(EntityPlayer player) {
        return CapabilityHelper.getCapability(player, MANA_CAPABILITY, null);
    }

    public ICapabilityProvider createProvider() {
        return new CapabilityProvider<>(MANA_CAPABILITY, null, manaCap);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IMana.class, new Capability.IStorage<IMana>() {
            @Override
            public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new ManaImplementation(null));
    }
}
