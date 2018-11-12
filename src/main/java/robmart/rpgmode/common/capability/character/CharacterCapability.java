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

package robmart.rpgmode.common.capability.character;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.character.ICharacter;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.CapabilityProviderSerializable;
import robmart.rpgmode.common.util.CapabilityUtils;

import javax.annotation.Nullable;

/**
 * @author Robmart
 * Created on 9/26/2018
 */
public class CharacterCapability {

    /**
     * The default {@link EnumFacing} to use for this capability.
     */
    public static final EnumFacing DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "Character");

    private CharacterCapability() {
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(ICharacter.class, new Capability.IStorage<ICharacter>() {
            @Override
            public NBTBase writeNBT(
                    final Capability<ICharacter> capability, final ICharacter instance, final EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(
                    final Capability<ICharacter> capability, final ICharacter instance, final EnumFacing side,
                    final NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new MobCharacterImplementation(null));
    }

    /**
     * Get the {@link ICharacter} from the specified entity.
     *
     * @param entity The entity
     *
     * @return The ICharacter
     */
    @Nullable
    public static ICharacter getCharacter(final EntityLivingBase entity) {
        return CapabilityUtils.getCapability(entity, ICharacter.CHARACTER_CAPABILITY, DEFAULT_FACING);
    }

    /**
     * Create a provider for the specified {@link ICharacter} instance.
     *
     * @param character The ICharacter
     *
     * @return The provider
     */
    public static ICapabilityProvider createProvider(final ICharacter character) {
        return new CapabilityProviderSerializable<>(ICharacter.CHARACTER_CAPABILITY, DEFAULT_FACING, character);
    }

    /**
     * Event handler for the {@link ICharacter} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link ICharacter} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof EntityLivingBase) {
                if (CapabilityUtils.shouldHaveAttribute(event.getObject())) {
                    ICharacter character;
                    if (event.getObject() instanceof EntityPlayer) {
                        character = new PlayerCharacterImplementation((EntityPlayer) event.getObject());
                    }
                    else
                        character = new MobCharacterImplementation((EntityLivingBase) event.getObject());
                    event.addCapability(ID, createProvider(character));
                }
            }
        }

        /**
         * Copy the player's character when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(final PlayerEvent.Clone event) {
            final ICharacter oldCharacter = getCharacter(event.getOriginal());
            final ICharacter newCharacter = getCharacter(event.getEntityPlayer());

            if (newCharacter != null && oldCharacter != null) {
                newCharacter.loadNBTData(oldCharacter.saveNBTData());
            }
        }

        /**
         * Synchronise a player's character to watching clients when they change dimensions.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerChangeDimension(
                final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
            final ICharacter character = getCharacter(event.player);

            if (character != null) {
                character.synchronise();
            }
        }

        /**
         * Synchronise a player's character to watching clients when they join world.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof EntityPlayerMP) {
                final ICharacter character = getCharacter((EntityPlayerMP) event.getEntity());

                if (character != null) {
                    character.synchronise();
                }
            }
        }
    }
}
