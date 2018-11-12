/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 *
 * Edited for mana
 */

package robmart.rpgmode.common.capability.mana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.capability.mana.IMana;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.CapabilityProviderSerializable;
import robmart.rpgmode.common.util.CapabilityUtils;

import javax.annotation.Nullable;

/**
 * @author Choonster
 */
public final class ManaCapability {

    /**
     * The default {@link EnumFacing} to use for this capability.
     */
    public static final EnumFacing DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "Mana");

    public static void register() {
        CapabilityManager.INSTANCE.register(IMana.class, new Capability.IStorage<IMana>() {
            @Override
            public NBTBase writeNBT(final Capability<IMana> capability, final IMana instance, final EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(
                    final Capability<IMana> capability, final IMana instance, final EnumFacing side,
                    final NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new ManaImplementation(null));
    }

    /**
     * Get the {@link IMana} from the specified entity.
     *
     * @param entity The entity
     *
     * @return The IMana
     */
    @Nullable
    public static IMana getMana(final EntityLivingBase entity) {
        return CapabilityUtils.getCapability(entity, IMana.MANA_CAPABILITY, DEFAULT_FACING);
    }

    /**
     * Create a provider for the specified {@link IMana} instance.
     *
     * @param mana The IMana
     *
     * @return The provider
     */
    public static ICapabilityProvider createProvider(final IMana mana) {
        return new CapabilityProviderSerializable<>(IMana.MANA_CAPABILITY, DEFAULT_FACING, mana);
    }

    private ManaCapability() {
    }

    /**
     * Event handler for the {@link IMana} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IMana} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof EntityLivingBase) {
                final ManaImplementation mana = new ManaImplementation((EntityLivingBase) event.getObject());
                event.addCapability(ID, createProvider(mana));
            }
        }

        /**
         * Copy the player's mana when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(final PlayerEvent.Clone event) {
            final IMana oldMana = getMana(event.getOriginal());
            final IMana newMana = getMana(event.getEntityPlayer());

            if (newMana != null && oldMana != null) {
                newMana.loadNBTData(oldMana.saveNBTData());
            }
        }

        /**
         * Synchronise a player's mana to watching clients when they change dimensions.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerChangeDimension(
                final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
            final IMana mana = getMana(event.player);

            if (mana != null) {
                mana.synchronise();
            }
        }

        /**
         * @param event The event
         */
        @SubscribeEvent
        public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
            final IMana mana = ManaCapability.getMana(event.getEntityLiving());

            if (event.getEntity() instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) event.getEntity();

                //Regenerates mana
                mana.onUpdate(entity);

                //Restores mana after sleeping
                if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isPlayerFullyAsleep()) {
                    new GuiIngame(Minecraft.getMinecraft()).addChatMessage(ChatType.SYSTEM, new TextComponentString(
                            TextFormatting.AQUA + "After a full nights rest, you feel completely restored!"));
                    mana.restoreMana();
                }
            }
        }

        /**
         * Synchronise a player's mana to watching clients when they join world.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof EntityPlayerMP) {
                final IMana mana = getMana((EntityLivingBase) event.getEntity());
                mana.synchronise();
            }
        }
    }
}
