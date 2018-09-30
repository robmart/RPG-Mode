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

package robmart.rpgmode.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import robmart.rpgmode.api.capability.character.ICharacter;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.character.CharacterCapability;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 * Created on 9/30/2018
 */
public class CommandSetCharacter extends CommandBase {
    public String name            = "setcharacter";
    public int    permissionLevel = 2;
    String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".setcharacter.usage";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return commandUsage;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        ICharacter character;
        String[] info;

        if (args.length > 3 || args.length == 1 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 3)
            player = getPlayer(server, sender, args[2]);
        else
            player = getCommandSenderAsPlayer(sender);

        character = CharacterCapability.getCharacter(player);

        setCharacter(args[0], Integer.valueOf(args[1]), character);
        info = getInfoFromString(args[0], character);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(player, this, "commands.rpgmode.setcharacter.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(
                    sender, this, "commands.rpgmode.setcharacter.success2", player.getName(), info[0], info[1]);
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    private String[] getInfoFromString(String string, ICharacter character) {
        switch (string.toLowerCase()) {
            case "level":
                return new String[] {"level", floatToString(character.getLevel())};
            case "lvl":
                return getInfoFromString("level", character);
            case "experience":
                return new String[] {"experience", floatToString(character.getEXP())};
            case "exp":
                return getInfoFromString("experience", character);
        }
        return null;
    }

    private void setCharacter(String string, int amount, ICharacter character) {
        switch (string.toLowerCase()) {
            case "level":
                character.setLevel(amount);
                break;
            case "lvl":
                setCharacter("level", amount, character);
                break;
            case "experience":
                character.setEXP(amount);
                break;
            case "exp":
                setCharacter("experience", amount, character);
                break;
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ?
               getListOfStringsMatchingLastWord(args, "level", "experience") :
               (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
