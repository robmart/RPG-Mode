/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

package robmart.rpgmode.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

/**
 * A simple implementation of {@link ICapabilityProvider} and {@link INBTSerializable} that supports a single {@link Capability} handler instance.
 * <p>
 * Uses the {@link Capability}'s {@link IStorage} to serialise/deserialise NBT.
 *
 * @author Choonster
 */
public class CapabilityProviderSerializable<T> extends CapabilityProviderSimple<T> implements INBTSerializable<NBTBase> {

    /**
     * Create a provider for the default handler instance.
     *
     * @param capability The Capability instance to provide the handler for
     * @param facing     The EnumFacing to provide the handler for
     */
    public CapabilityProviderSerializable(final Capability<T> capability, @Nullable final EnumFacing facing) {
        this(capability, facing, capability.getDefaultInstance());
    }

    /**
     * Create a provider for the specified handler instance.
     *
     * @param capability The Capability instance to provide the handler for
     * @param facing     The EnumFacing to provide the handler for
     * @param instance   The handler instance to provide
     */
    public CapabilityProviderSerializable(
            final Capability<T> capability, @Nullable final EnumFacing facing, @Nullable final T instance) {
        super(capability, facing, instance);
    }

    @Nullable
    @Override
    public NBTBase serializeNBT() {
        return getCapability().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        getCapability().readNBT(getInstance(), getFacing(), nbt);
    }

}
