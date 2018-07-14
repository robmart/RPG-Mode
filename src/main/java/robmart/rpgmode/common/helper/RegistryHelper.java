/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

package robmart.rpgmode.common.helper;

import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;
import robmart.rpgmode.common.reference.Reference;

/**
 * Utility methods for Forge registries.
 *
 * @author Choonster
 */
public class RegistryHelper {
    /**
     * Get an entry from the provided registry, using <code>testmod3</code> as the mod ID.
     *
     * @param registry The registry
     * @param name     The name of the entry
     * @param <T>      The registry type
     *
     * @return The registry entry
     *
     * @throws NullPointerException When the entry doesn't exist
     */
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(
            final IForgeRegistry<T> registry, final String name) {
        return getRegistryEntry(registry, Reference.MOD_ID, name);
    }

    /**
     * Get an entry from the provided registry.
     *
     * @param registry The registry
     * @param modid    The mod ID of the entry
     * @param name     The name of the entry
     * @param <T>      The registry type
     *
     * @return The registry entry
     *
     * @throws NullPointerException When the entry doesn't exist
     */
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(
            final IForgeRegistry<T> registry, final String modid, final String name) {
        final ResourceLocation key = new ResourceLocation(modid, name);
        final T registryEntry = registry.getValue(key);
        return Preconditions.checkNotNull(registryEntry, "%s doesn't exist in registry %s", key,
                                          RegistryManager.ACTIVE.getName(registry));
    }
}
