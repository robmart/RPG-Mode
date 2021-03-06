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

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robmart.rpgmode.api.reference.Reference;

import java.io.File;

/**
 * @author Robmart
 */
public class ConfigurationHandler {

    private static Configuration config;

    private static boolean debug = false;
    private static boolean displayManaValue   = false;
    private static boolean displayHealthValue = false;
    private static boolean moveManaBar        = true;

    public static boolean debugModeActive() {
        return debug;
    }

    public static boolean shouldDisplayManaValue() {
        return displayManaValue;
    }

    public static boolean shouldDisplayHealthValue() {
        return displayHealthValue;
    }

    public static boolean shouldMoveManaBar() {
        return moveManaBar;
    }

    public static void initialize(File file) {
        config = new Configuration(file);
        config.load();

        load();

        MinecraftForge.EVENT_BUS.register(ConfigChangeListener.class);
    }

    private static void load() {
        String desc = "Debug stuff.";
        debug = config.getBoolean("debug.enabled",
                                  Configuration.CATEGORY_GENERAL, debug, desc);

        desc = "Set this to true to display the amount of health you have left.";
        displayHealthValue = config.getBoolean("healthValueRender.enabled",
                                               Configuration.CATEGORY_CLIENT, displayHealthValue, desc);

        desc = "Set this to true to display the amount of mana you have left.";
        displayManaValue = config.getBoolean("manaValueRender.enabled",
                                             Configuration.CATEGORY_CLIENT, displayManaValue, desc);

        desc = "Set this to false to disable the mana bar and food switching places.";
        moveManaBar = config.getBoolean("moveManaBar.enabled",
                                        Configuration.CATEGORY_CLIENT, moveManaBar, desc);

        if (config.hasChanged())
            config.save();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void save() {
        config.save();
    }

    public static class ConfigChangeListener {
        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if (eventArgs.getModID().equals(Reference.MOD_ID))
                load();
        }

        private ConfigChangeListener() {
        }
    }
}
