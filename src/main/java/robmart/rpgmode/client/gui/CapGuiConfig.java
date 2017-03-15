package robmart.rpgmode.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import robmart.rpgmode.common.handlers.ConfigurationHandler;

import java.util.ArrayList;
import java.util.List;

import static robmart.rpgmode.common.reference.Reference.MOD_ID;

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
public class CapGuiConfig extends GuiConfig {

    public CapGuiConfig(GuiScreen parent){
        super(parent, getConfigElements(), MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }


    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        list.add(categoryElement(
                Configuration.CATEGORY_GENERAL,
                "category." + MOD_ID.toLowerCase() + ".general",
                "tooltip." + MOD_ID.toLowerCase() + ".general"));
        list.add(categoryElement(
                Configuration.CATEGORY_CLIENT,
                "category." + MOD_ID.toLowerCase() + ".client",
                "tooltip." + MOD_ID.toLowerCase() + ".client"));


        return list;

    }

    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(ConfigurationHandler.config.getCategory(category)).getChildElements());
    }
}
