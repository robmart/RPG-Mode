package robmart.rpgmode.common.capability.attribute;

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
public class AttributeCapability {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IAttribute.class)
    public static final Capability<IAttribute> ATTRIBUTE_CAPABILITY = null;

    private IAttribute attributeCap = null;

    public AttributeCapability() {
        this.attributeCap = new AttributeImplementation();
    }

    public AttributeCapability(EntityLivingBase enitity) {
        this.attributeCap = new AttributeImplementation(enitity);
    }

    public AttributeCapability(IAttribute attribute) {
        this.attributeCap = attribute;
    }

    public static IAttribute get(EntityLivingBase entity) {
        return CapabilityHelper.getCapability(entity, ATTRIBUTE_CAPABILITY, null);
    }

    public ICapabilityProvider createProvider() {
        return new CapabilityProvider<>(ATTRIBUTE_CAPABILITY, null, attributeCap);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IAttribute.class, new Capability.IStorage<IAttribute>() {
            @Override
            public NBTBase writeNBT(Capability<IAttribute> capability, IAttribute instance, EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(Capability<IAttribute> capability, IAttribute instance, EnumFacing side, NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new AttributeImplementation(null));
    }
}
