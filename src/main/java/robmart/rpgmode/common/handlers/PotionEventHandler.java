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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.api.potion.RPGPotions;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.potion.PotionBase;

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
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.STRENGTH);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.DEXTEROUSNESS);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.FORTITUDE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.INTELLIGENCE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.WISDOM);
        }
    }

    @SubscribeEvent
    public static void inLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        //TODO: make this less cluttered
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound persisted = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        if (entity.ticksExisted % 9 == 0 && ((PotionBase) RPGPotions.STRENGTH).instance != null && !entity.isDead) {
            //Strength
            if (!persisted.getBoolean(((PotionBase) RPGPotions.STRENGTH).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() !=
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.STRENGTH).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() ==
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.STRENGTH).TAG_NAME, false);
                }
            }

            //Weakness
            if (!persisted.getBoolean(((PotionBase) RPGPotions.WEAKNESS).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() !=
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.WEAKNESS).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() ==
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.WEAKNESS).TAG_NAME, false);
                }
            }


            //Dexterousness
            if (!persisted.getBoolean(((PotionBase) RPGPotions.DEXTEROUSNESS).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() !=
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.DEXTEROUSNESS).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() ==
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.DEXTEROUSNESS).TAG_NAME, false);
                }
            }

            //Clumsiness
            if (!persisted.getBoolean(((PotionBase) RPGPotions.CLUMSINESS).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() !=
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.CLUMSINESS).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() ==
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.CLUMSINESS).TAG_NAME, false);
                }
            }

            //Fortitude
            if (!persisted.getBoolean(((PotionBase) RPGPotions.FORTITUDE).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() !=
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.FORTITUDE).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() ==
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.FORTITUDE).TAG_NAME, false);
                }
            }

            //Lethargy
            if (!persisted.getBoolean(((PotionBase) RPGPotions.LETHARGY).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() !=
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.LETHARGY).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() ==
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.LETHARGY).TAG_NAME, false);
                }
            }

            //Intelligence
            if (!persisted.getBoolean(((PotionBase) RPGPotions.INTELLIGENCE).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() !=
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.INTELLIGENCE).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() ==
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.INTELLIGENCE).TAG_NAME, false);
                }
            }

            //Stupidity
            if (!persisted.getBoolean(((PotionBase) RPGPotions.STUPIDITY).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() !=
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.STUPIDITY).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() ==
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.STUPIDITY).TAG_NAME, false);
                }
            }

            //Wisdom
            if (!persisted.getBoolean(((PotionBase) RPGPotions.WISDOM).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() !=
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.WISDOM).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() ==
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.WISDOM).TAG_NAME, false);
                }
            }

            //Foolishness
            if (!persisted.getBoolean(((PotionBase) RPGPotions.FOOLISHNESS).TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() !=
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.FOOLISHNESS).TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() ==
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(((PotionBase) RPGPotions.FOOLISHNESS).TAG_NAME, false);
                }
            }
        }


        if (!entity.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
        }
    }
}
