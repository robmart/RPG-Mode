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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart
 */
@SuppressWarnings("unused")
public class CapabilityEventHandler {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer) &&
            AttributeCapability.shouldHaveAttribute(event.getEntity())) {
            final EntityLivingBase entity = (EntityLivingBase) event.getEntity();

            final NBTTagCompound entityData = entity.getEntityData();
            final NBTTagCompound persistedData = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);

            final String key = Reference.MOD_ID + ":JoinedWorld";

            if (!persistedData.getBoolean(key)) {
                persistedData.setBoolean(key, true);
                AttributeCapability.getAttributes(entity).setAttributes(5, 5, 5, 5, 5);
                entity.setHealth(entity.getMaxHealth());
            }
        }
    }
}
