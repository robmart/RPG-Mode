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

package robmart.rpgmode.common.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.api.potion.RPGPotions;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.potion.PotionBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Robmart
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class PotionEventHandler {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityLivingBase) {
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(RPGPotions.STRENGTH_ATTRIBUTE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(
                    RPGPotions.DEXTEROUSNESS_ATTRIBUTE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(RPGPotions.FORTITUDE_ATTRIBUTE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(
                    RPGPotions.INTELLIGENCE_ATTRIBUTE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(RPGPotions.WISDOM_ATTRIBUTE);
        }
    }

    private PotionEventHandler() {
    }

    @SubscribeEvent
    public static void inLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound persisted = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        for (Field field : RPGPotions.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) &&
                field.getType() == Potion.class) {
                try {
                    if (entity.ticksExisted % 9 == 0 && ((PotionBase) field.get(null)).instance != null &&
                        !entity.isDead) {
                        for (Field field2 : RPGPotions.class.getDeclaredFields()) {
                            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) &&
                                field.getType() == IAttribute.class) {
                                if (field2.getName().replaceAll("_*.", "").equalsIgnoreCase(field.getName())) {
                                    if (!persisted.getBoolean(((PotionBase) field.get(null)).tagName)) {
                                        if (entity.getEntityAttribute((IAttribute) field2.get(null))
                                                  .getAttributeValue() !=
                                            ((IAttribute) field2.get(null)).getDefaultValue()) {
                                            persisted.setBoolean(((PotionBase) field.get(null)).tagName, true);
                                        }
                                    }
                                    else if (entity.getEntityAttribute((IAttribute) field2.get(null))
                                                   .getAttributeValue() ==
                                             ((IAttribute) field2.get(null)).getDefaultValue()) {
                                        persisted.setBoolean(((PotionBase) field.get(null)).tagName, false);
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    RPGMode.logger.error(e);
                }
            }
        }

        if (!entity.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
        }
    }
}
