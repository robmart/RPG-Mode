package robmart.rpgmode.common.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.helper.PotionHelper;
import robmart.rpgmode.common.potion.PotionStrength;

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
public class PotionHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            ((EntityLivingBase) event.getEntity()).getAttributeMap().registerAttribute(PotionHelper.STRENGTH);
        }
    }

    @SubscribeEvent
    public void inLivingUppdate(LivingEvent.LivingUpdateEvent event) {
        if ((event.getEntityLiving() instanceof EntityPlayer)) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

            //Strength
            if (event.getEntityLiving() instanceof EntityPlayer) {
                if (player.ticksExisted % 9 == 0 && PotionStrength.instance != null && !player.isDead) {

                    if (!persisted.getBoolean(PotionStrength.TAG_NAME)) {
                        if (player.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() != PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionStrength.TAG_NAME, true);
                        }
                    } else {
                        AttributeCapability.get(player).setStrMod((int) player.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue());

                        if (player.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue() == PotionHelper.STRENGTH.getDefaultValue()) {
                            persisted.setBoolean(PotionStrength.TAG_NAME, false);
                        }
                    }
                }
            }

            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
            }
        }
    }
}
