package robmart.rpgmode.common.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart.
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
public class EventHandler {

    @SubscribeEvent
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        final EntityPlayer player = event.player;

        final NBTTagCompound entityData = player.getEntityData();
        final NBTTagCompound persistedData = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);

        final String key = Reference.MOD_ID + ":LoggedIn";
        final String message;

        if (persistedData.getBoolean(key)) {
            message = "Player has joined before.";
        } else {
            persistedData.setBoolean(key, true);
            AttributeCapability.get(player).setAttributes(5, 5, 5, 5, 5);

            message = "Player hasn't joined before.";
        }

        final ITextComponent chatComponent = new TextComponentTranslation(message);
        chatComponent.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
        player.sendMessage(chatComponent);
    }
}
