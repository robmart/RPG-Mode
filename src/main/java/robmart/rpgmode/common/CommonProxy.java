package robmart.rpgmode.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import robmart.rpgmode.common.capability.mana.IMana;
import robmart.rpgmode.common.capability.mana.ManaFactory;
import robmart.rpgmode.common.capability.mana.ManaStorage;
import robmart.rpgmode.common.command.CommandManaInfo;
import robmart.rpgmode.common.command.CommandRestore;
import robmart.rpgmode.common.command.CommandSetMana;
import robmart.rpgmode.common.handlers.CapabilityHandler;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.network.PacketDispatcher;

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
public abstract class CommonProxy implements IGuiHandler{

    public void preInit(FMLPreInitializationEvent event){
        RPGMode.logger.info("[RPGMode] Pre initialization starting");

        try {
            ConfigurationHandler.initialize(event.getSuggestedConfigurationFile());
            RPGMode.logger.error("[RPGMode] Problem loading configuration");
        } finally {
            if (ConfigurationHandler.config!=null) ConfigurationHandler.save();
        }

        CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), new ManaFactory());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        PacketDispatcher.registerPackets();
    }

    public void init(FMLInitializationEvent event){
        RPGMode.logger.info("[RPGMode] Initialization starting");
    }

    public void postInit(FMLPostInitializationEvent event){
        RPGMode.logger.info("[RPGMode] Post initialization starting");
    }

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandManaInfo());
        event.registerServerCommand(new CommandSetMana());
        event.registerServerCommand(new CommandRestore());
    }

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity.getServerWorld();
    }
}
