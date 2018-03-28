package robmart.rpgmode.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.handlers.PotionHandler;
import robmart.rpgmode.common.helper.RecipeHelper;
import robmart.rpgmode.common.init.InitCapabilities;
import robmart.rpgmode.common.init.InitCommands;
import robmart.rpgmode.common.network.PacketDispatcher;
import robmart.rpgmode.common.reference.Reference;
import robmart.rpgmode.creativetab.CreativeTabBrewing;

import java.util.ArrayList;
import java.util.List;

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
        RPGMode.logger.info("Pre initialization starting");

        try {
            ConfigurationHandler.initialize(event.getSuggestedConfigurationFile());
            RPGMode.logger.error("Problem loading configuration");
        } finally {
            if (ConfigurationHandler.config!=null) ConfigurationHandler.save();
        }

        InitCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        MinecraftForge.EVENT_BUS.register(new PotionHandler());

        PacketDispatcher.registerPackets();
    }

    public void init(FMLInitializationEvent event){
        RPGMode.logger.info("Initialization starting");
        CreativeTabBrewing.instance.Init();
    }

    public void postInit(FMLPostInitializationEvent event){
        RPGMode.logger.info("Post initialization starting");
    }

    public void serverStarting(FMLServerStartingEvent event){
        InitCommands.init(event);
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
        return ctx.getServerHandler().player;
    }

    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return ctx.getServerHandler().player.getServerWorld();
    }
}
