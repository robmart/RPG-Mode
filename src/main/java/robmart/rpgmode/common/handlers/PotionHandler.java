package robmart.rpgmode.common.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.potion.*;
import robmart.rpgmode.common.reference.Reference;

/**
 * Created by Robmart.
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2017 Robmart
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
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class PotionHandler {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityLivingBase) {
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionHelper.STRENGTH);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionHelper.DEXTEROUSNESS);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionHelper.FORTITUDE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionHelper.INTELLIGENCE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionHelper.WISDOM);
        }
    }

    @SubscribeEvent
    public static void inLivingUppdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound persisted = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

            //Strength
        if (entity.ticksExisted % 9 == 0 && PotionStrength.instance != null && !entity.isDead) {

                    if (!persisted.getBoolean(PotionStrength.TAG_NAME)) {
                        if (entity.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() != PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionStrength.TAG_NAME, true);
                        }
                    } else {
                        if (entity.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() == PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionStrength.TAG_NAME, false);
                        }
                    }

        }

        //Weakness
        if (entity.ticksExisted % 9 == 0 && PotionWeakness.instance != null && !entity.isDead) {

                    if (!persisted.getBoolean(PotionWeakness.TAG_NAME)) {
                        if (entity.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() != PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionWeakness.TAG_NAME, true);
                        }
                    } else {
                        if (entity.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() == PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionWeakness.TAG_NAME, false);
                        }
                    }
                }

        //Dexterousness
        if (entity.ticksExisted % 9 == 0 && PotionDexterousness.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionDexterousness.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.DEXTEROUSNESS).getAttributeValue() != PotionHelper.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(PotionDexterousness.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.DEXTEROUSNESS).getAttributeValue() == PotionHelper.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(PotionDexterousness.TAG_NAME, false);
                }
            }
        }

        //Clumsiness
        if (entity.ticksExisted % 9 == 0 && PotionClumsiness.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionClumsiness.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.DEXTEROUSNESS).getAttributeValue() != PotionHelper.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(PotionClumsiness.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.DEXTEROUSNESS).getAttributeValue() == PotionHelper.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(PotionClumsiness.TAG_NAME, false);
                }
            }
        }

        //Fortitude
        if (entity.ticksExisted % 9 == 0 && PotionFortitude.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionFortitude.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.FORTITUDE).getAttributeValue() != PotionHelper.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(PotionFortitude.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.FORTITUDE).getAttributeValue() == PotionHelper.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(PotionFortitude.TAG_NAME, false);
                }
            }
        }

        //Lethargy
        if (entity.ticksExisted % 9 == 0 && PotionLethargy.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionLethargy.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.FORTITUDE).getAttributeValue() != PotionHelper.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(PotionLethargy.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.FORTITUDE).getAttributeValue() == PotionHelper.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(PotionLethargy.TAG_NAME, false);
                }
            }
        }

        //Intelligence
        if (entity.ticksExisted % 9 == 0 && PotionIntelligence.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionIntelligence.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.INTELLIGENCE).getAttributeValue() != PotionHelper.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(PotionIntelligence.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.INTELLIGENCE).getAttributeValue() == PotionHelper.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(PotionIntelligence.TAG_NAME, false);
                }
            }
        }

        //Stupidity
        if (entity.ticksExisted % 9 == 0 && PotionStupidity.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionStupidity.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.INTELLIGENCE).getAttributeValue() != PotionHelper.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(PotionStupidity.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.INTELLIGENCE).getAttributeValue() == PotionHelper.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(PotionStupidity.TAG_NAME, false);
                }
            }
        }

        //Wisdom
        if (entity.ticksExisted % 9 == 0 && PotionWisdom.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionWisdom.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.WISDOM).getAttributeValue() != PotionHelper.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(PotionWisdom.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.WISDOM).getAttributeValue() == PotionHelper.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(PotionWisdom.TAG_NAME, false);
                }
            }
        }

        //Foolishness
        if (entity.ticksExisted % 9 == 0 && PotionFoolishness.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(PotionFoolishness.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionHelper.WISDOM).getAttributeValue() != PotionHelper.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(PotionFoolishness.TAG_NAME, true);
                }
            } else {
                if (entity.getEntityAttribute(PotionHelper.WISDOM).getAttributeValue() == PotionHelper.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(PotionFoolishness.TAG_NAME, false);
                }
            }
        }


        if (!entity.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
        }
    }
}
