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

package robmart.rpgmode.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import robmart.rpgmode.common.handlers.ConfigurationHandler;
import robmart.rpgmode.common.reference.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robmart
 */
public class RPGGuiConfig extends DefaultGuiFactory {

    public RPGGuiConfig(){
        super(Reference.MOD_ID, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }


    @Override
    public GuiScreen createConfigGui(GuiScreen parent){
        return new GuiConfig(parent, getConfigElements(), this.modid, false, false, this.title);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        list.addAll(new ConfigElement(ConfigurationHandler.config
                .getCategory(Configuration.CATEGORY_GENERAL))
                .getChildElements());
        list.addAll(new ConfigElement(ConfigurationHandler.config
                .getCategory(Configuration.CATEGORY_CLIENT))
                .getChildElements());

        return list;
    }
}
