/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

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
 * @author Choonster
 */
public class MaxHealthCapability {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMaxHealth.class)
    private static final Capability<IMaxHealth> MAX_HEALTH_CAPABILITY = null;

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
