/*
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2018 Robmart
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package robmart.rpgmode.common.capability.chunklevel;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.chunklevel.IChunkLevel;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.CapabilityProviderSerializable;
import robmart.rpgmode.common.util.CapabilityUtils;

import javax.annotation.Nullable;

/**
 * @author Robmart
 * Created on 11/14/2018
 */
public class ChunkLevelCapability {

    /**
     * The default {@link EnumFacing} to use for this capability.
     */
    public static final EnumFacing DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "ChunkLevel");

    private ChunkLevelCapability() {
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IChunkLevel.class, new Capability.IStorage<IChunkLevel>() {
            @Override
            public NBTBase writeNBT(
                    final Capability<IChunkLevel> capability, final IChunkLevel instance, final EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(
                    final Capability<IChunkLevel> capability, final IChunkLevel instance, final EnumFacing side,
                    final NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new ChunkLevelImplementation(null));
    }

    /**
     * Get the {@link IChunkLevel} from the specified chunk.
     *
     * @param chunk The chunk
     *
     * @return The IChunkLevel
     */
    @Nullable
    public static IChunkLevel getChunkLevel(final Chunk chunk) {
        return CapabilityUtils.getCapability(chunk, IChunkLevel.CHUNK_LEVEL_CAPABILITY, DEFAULT_FACING);
    }

    /**
     * Create a provider for the specified {@link IChunkLevel} instance.
     *
     * @param chunkLevel The IChunkLevel
     *
     * @return The provider
     */
    public static ICapabilityProvider createProvider(final IChunkLevel chunkLevel) {
        return new CapabilityProviderSerializable<>(IChunkLevel.CHUNK_LEVEL_CAPABILITY, DEFAULT_FACING, chunkLevel);
    }

    /**
     * Event handler for the {@link IChunkLevel} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IChunkLevel} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Chunk> event) {
            IChunkLevel chunkLevel;
            chunkLevel = new ChunkLevelImplementation(event.getObject());
            event.addCapability(ID, createProvider(chunkLevel));
        }

        @SubscribeEvent
        public static void onChunkWatch(final ChunkWatchEvent.Watch event) {
            IChunkLevel chunkLevel = getChunkLevel(event.getChunkInstance());
            if (chunkLevel != null && chunkLevel.getLevel() < 1)
                chunkLevel.calculateLevel();
        }
    }
}
