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

package robmart.rpgmode.client;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.client.gui.GuiContainerCreativeOverride;
import robmart.rpgmode.client.init.InitGui;
import robmart.rpgmode.common.CommonProxy;
import robmart.rpgmode.common.creativetab.CreativeTabBrewing;

/**
 * @author Robmart
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);

        InitGui.init();
        GuiContainerCreativeOverride.addCreativeTabReplacement(CreativeTabs.BREWING, CreativeTabBrewing.instance);
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
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return (ctx.side.isClient() ? this.mc.player : super.getPlayerEntity(ctx));
    }

    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? this.mc : super.getThreadFromContext(ctx));
    }
}
