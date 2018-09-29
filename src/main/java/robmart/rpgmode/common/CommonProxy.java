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

package robmart.rpgmode.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.init.InitCapabilities;
import robmart.rpgmode.common.init.InitCommands;
import robmart.rpgmode.common.init.InitItems;
import robmart.rpgmode.common.init.InitRecipes;
import robmart.rpgmode.common.network.GuiHandler;
import robmart.rpgmode.common.network.PacketDispatcher;
import robmart.rpgmode.common.world.biome.BiomeHellDecoratorWrapper;

import java.io.File;

/**
 * @author Robmart
 */
public abstract class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        RPGMode.logger.info("Pre initialization starting");

        try {
            RPGMode.logger.info("Loading configuration");
            ConfigurationHandler.initialize(new File(Loader.instance().getConfigDir(), Reference.MOD_ID + "/config" +
                                                                                       ".cfg"));
        } catch (Exception e) {
            RPGMode.logger.error("Problem loading configuration");
        } finally {
            if (ConfigurationHandler.config != null) ConfigurationHandler.save();
        }

        InitCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
            InitRecipes.generateRecipes();

        PacketDispatcher.registerPackets();

    }

    public void init(FMLInitializationEvent event) {
        RPGMode.logger.info("Initialization starting");

        NetworkRegistry.INSTANCE.registerGuiHandler(RPGMode.instance, new GuiHandler());

        InitRecipes.generateBrewing();
    }

    public void postInit(FMLPostInitializationEvent event) {
        RPGMode.logger.info("Post initialization starting");
        InitItems.registerOreDictionary();

        for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER))
            biome.decorator = new BiomeHellDecoratorWrapper(biome.decorator);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        InitCommands.init(event);
    }

    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return ctx.getServerHandler().player.getServerWorld();
    }
}
