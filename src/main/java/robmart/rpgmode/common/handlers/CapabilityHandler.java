package robmart.rpgmode.common.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.client.network.SyncPlayerMana;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.IMana;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.network.PacketDispatcher;
import robmart.rpgmode.common.reference.Reference;

/**
 * Created by Robmart.
 *
 *   This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *   Copyright (C) 2017 Robmart
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
@SuppressWarnings("unused")
public class CapabilityHandler {

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent event){
        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":ManaCapability"), new ManaCapability((EntityLivingBase) event.getObject()).createProvider());

        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":MaxHealthCapability"), new MaxHealthCapability((EntityLivingBase) event.getObject()).createProvider());

        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":AttributeCapability"), new AttributeCapability((EntityLivingBase) event.getObject()).createProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        ManaCapability.get(newPlayer).loadNBTData(ManaCapability.get(oldPlayer).saveNBTData());
        MaxHealthCapability.get(newPlayer).loadNBTData(MaxHealthCapability.get(oldPlayer).saveNBTData());
        AttributeCapability.get(newPlayer).loadNBTData(AttributeCapability.get(oldPlayer).saveNBTData());

    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntity();
            IMana mana = ManaCapability.get(player);

            mana.onUpdate(player);

            if (player.isPlayerFullyAsleep()){
                new GuiIngame(Minecraft.getMinecraft()).addChatMessage(ChatType.SYSTEM, new TextComponentString(TextFormatting.AQUA + "After a full nights rest, you feel completely restored!"));
                mana.restoreMana();
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (event.getEntity() instanceof  EntityPlayerMP){
            PacketDispatcher.sendTo(new SyncPlayerMana((EntityPlayer) event.getEntity()), (EntityPlayerMP) event.getEntity());
        }
    }
}
