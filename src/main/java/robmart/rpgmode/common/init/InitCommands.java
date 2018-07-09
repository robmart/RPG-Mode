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

package robmart.rpgmode.common.init;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.command.*;

/**
 * @author Robmart
 */
public class InitCommands {
    private static int commandCounter = 0;

    public static void init(FMLServerStartingEvent event){
        RPGMode.logger.info("Adding commands");
        registerCommand(event, new CommandManaInfo());
        registerCommand(event, new CommandSetMana());
        registerCommand(event, new CommandRestore());
        registerCommand(event, new CommandSetHealth());
        registerCommand(event, new CommandHealthInfo());
        registerCommand(event, new CommandAttributeInfo());
        registerCommand(event, new CommandSetAttribute());
        RPGMode.logger.info(String.format("%s commands added", commandCounter));
    }

    private static void registerCommand(FMLServerStartingEvent event, ICommand command) {
        event.registerServerCommand(command);
        commandCounter++;
    }
}
