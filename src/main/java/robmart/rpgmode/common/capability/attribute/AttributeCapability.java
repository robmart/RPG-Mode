/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 *
 * Edited for attributes
 */

package robmart.rpgmode.common.capability.attribute;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.RPGModeAPI;
import robmart.rpgmode.api.capability.attribute.IAttribute;
import robmart.rpgmode.common.capability.CapabilityProviderSerializable;
import robmart.rpgmode.common.helper.MathHelper;
import robmart.rpgmode.common.reference.Reference;
import robmart.rpgmode.common.util.CapabilityUtils;

import javax.annotation.Nullable;

/**
 * @author Choonster
 */
public final class AttributeCapability {

    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IAttribute.class)
    public static final Capability<IAttribute> ATTRIBUTE_CAPABILITY = null;

    /**
     * The default {@link EnumFacing} to use for this capability.
     */
    public static final EnumFacing DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "Attribute");

    public static void register() {
        CapabilityManager.INSTANCE.register(IAttribute.class, new Capability.IStorage<IAttribute>() {
            @Override
            public NBTBase writeNBT(
                    final Capability<IAttribute> capability, final IAttribute instance, final EnumFacing side) {
                return instance.saveNBTData();
            }

            @Override
            public void readNBT(
                    final Capability<IAttribute> capability, final IAttribute instance, final EnumFacing side,
                    final NBTBase nbt) {
                instance.loadNBTData((NBTTagCompound) nbt);
            }
        }, () -> new AttributeImplementation(null));
    }

    /**
     * Get the {@link IAttribute} from the specified entity.
     *
     * @param entity The entity
     *
     * @return The IAttribute
     */
    @Nullable
    public static IAttribute getAttributes(final EntityLivingBase entity) {
        return CapabilityUtils.getCapability(entity, ATTRIBUTE_CAPABILITY, DEFAULT_FACING);
    }

    /**
     * Create a provider for the specified {@link IAttribute} instance.
     *
     * @param attribute The IAttribute
     *
     * @return The provider
     */
    public static ICapabilityProvider createProvider(final IAttribute attribute) {
        return new CapabilityProviderSerializable<>(ATTRIBUTE_CAPABILITY, DEFAULT_FACING, attribute);
    }

    /**
     * Checks if Entity should have the attribute
     */
    public static boolean shouldHaveAttribute(Entity entity) {
        return entity instanceof EntityLivingBase && (entity instanceof EntityPlayer ||
                                                      entity
                                                              .isCreatureType(
                                                                      EnumCreatureType.MONSTER,
                                                                      false) || RPGModeAPI
                                                              .shouldMobHaveAttributes(
                                                                      entity.getClass()));
    }

    /**
     * Event handler for the {@link IAttribute} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IAttribute} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (shouldHaveAttribute(event.getObject())) {
                final AttributeImplementation attribute = new AttributeImplementation(
                        (EntityLivingBase) event.getObject());
                event.addCapability(ID, createProvider(attribute));
            }
        }

        /**
         * Copy the player's attributes when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(final PlayerEvent.Clone event) {
            final IAttribute oldAttribute = getAttributes(event.getOriginal());
            final IAttribute newAttribute = getAttributes(event.getEntityPlayer());

            if (newAttribute != null && oldAttribute != null) {
                newAttribute.loadNBTData(oldAttribute.saveNBTData());
            }
        }

        /**
         * Synchronise a player's attributes to watching clients when they change dimensions.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerChangeDimension(
                final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
            final IAttribute attribute = getAttributes(event.player);

            if (attribute != null) {
                attribute.synchronise();
            }
        }

        /**
         * Synchronise a player's attributes to watching clients when they join world.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof EntityPlayerMP) {
                final IAttribute attribute = getAttributes((EntityLivingBase) event.getEntity());
                attribute.synchronise();
            }
        }

        @SubscribeEvent
        public static void moveEntity(LivingEvent.LivingUpdateEvent event) {
            if (shouldHaveAttribute(event.getEntity())) {
                EntityLivingBase Entity = (EntityLivingBase) event.getEntity();
                if (Entity.world.isRemote) {
                    if ((Entity.onGround || (Entity instanceof EntityPlayer && ((EntityPlayer) Entity).capabilities
                            .isFlying)) && Entity.moveForward > 0F &&
                        !Entity.isInsideOfMaterial(Material.WATER)) {
                        Entity.moveRelative(
                                0F, 0F, 1F, MathHelper.diminishingReturns(
                                        getAttributes(Entity).getConstitution() +
                                        getAttributes(Entity).getDexterity(), 0.00002f) - 0.01f);
                    }
                }
            }
        }
    }

    private AttributeCapability() {
    }
}
