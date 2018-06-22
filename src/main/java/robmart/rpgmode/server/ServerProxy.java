package robmart.rpgmode.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import robmart.rpgmode.common.CommonProxy;

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
public class ServerProxy extends CommonProxy {

    private MinecraftServer mc;

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        mc = FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event){
        super.serverStarting(event);
    }

    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isServer() ? mc : super.getThreadFromContext(ctx));
    }
}
