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
public class CommandCharacterInfo extends CommandBase {
    protected static final String NAME             = "characterinfo";
    private static final   int    PERMISSION_LEVEL = 1;
    private static final   String COMMAND_USAGE    = "commands." + Reference.MOD_ID.toLowerCase() +
                                                     ".characterinfo.usage";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return COMMAND_USAGE;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        ICharacter character;
        String[] info;

        if (args.length > 2 || args.length == 0)
            throw new WrongUsageException(COMMAND_USAGE);
        else if (args.length == 2)
            player = getPlayer(server, sender, args[1]);
        else
            player = getCommandSenderAsPlayer(sender);

        character = CharacterCapability.getCharacter(player);
        info = getInfoFromString(args[0], character);

        if (info == null)
            throw new WrongUsageException(COMMAND_USAGE);

        notifyCommandListener(
                sender, this, "commands.rpgmode.characterinfo.success", player.getName(), info[0], info[1]);
    }

    public int getPermissionLevel() {
        return PERMISSION_LEVEL;
    }

    private String[] getInfoFromString(String string, ICharacter character) {
        switch (string.toLowerCase()) {
            case "lvl":
                return getInfoFromString("level", character);
            case "experience":
                return new String[] {"experience", floatToString(character.getEXP())};
            case "exp":
                return getInfoFromString("experience", character);
            case "exprequired":
                return new String[] {"experience required", floatToString(character.getEXPRequired())};
            case "required":
                return getInfoFromString("exprequired", character);
            case "totalexp":
                return new String[] {"total experience", floatToString(character.getTotalEXP())};
            case "total":
                return getInfoFromString("totalexp", character);
            default:
                return new String[] {"level", floatToString(character.getLevel())};
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    @Override
    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "level", "experience", "exprequired", "totalexp");
        return (args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
